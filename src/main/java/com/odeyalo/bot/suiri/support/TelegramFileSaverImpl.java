package com.odeyalo.bot.suiri.support;

import com.odeyalo.bot.suiri.configuration.TelegramBotConfig;
import com.odeyalo.bot.suiri.dto.TelegramFileDownloadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.facilities.filedownloader.TelegramFileDownloader;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.net.URI;

@Component
public class TelegramFileSaverImpl implements TelegramFileSaver {

    private final TelegramFileDownloader downloader;
    private final RestTemplate template;
    private final String path;
    private final Logger logger = LoggerFactory.getLogger(TelegramFileSaverImpl.class);
    private final String token;

    @Autowired
    public TelegramFileSaverImpl(TelegramFileDownloader downloader,
                                 TelegramBotConfig config,
                                 RestTemplate template,
                                 @Value("${app.path.user.images}") String path) {
        this.downloader = downloader;
        this.template = template;
        this.path = path;
        this.token = config.getToken();
    }

    @Override
    public String save(String fileId) throws TelegramApiException {
        URI uri = UriBuilder
                .fromUri("https://api.telegram.org")
                .path("/bot" + token +"/getFile")
                .queryParam("file_id", fileId)
                .build();
        ResponseEntity<TelegramFileDownloadResponse> responseEntity = template.exchange(new RequestEntity<>(HttpMethod.GET, uri), TelegramFileDownloadResponse.class);
        TelegramFileDownloadResponse response = responseEntity.getBody();
        String path = response.getBody().getFilePath();
        File file = downloader.downloadFile(path, new File(path + System.currentTimeMillis() + ".jpg"));
        this.logger.info("Saved file with path: {}", file);
        return file.getAbsolutePath();
    }
}
