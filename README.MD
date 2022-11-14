## Suiri

Suiri is a telegram bot to learn the new words. \
Suiri also is my project for college 

Suiri works on webhooks. If you want to register webhook you need to run 
`telegram.webhook.register.sh` or do it by yourself.


# Features:
* User can add words to dictionary, remove them and test himself using commands
* User can test using Updatable message(by default) or Quiz Poll
* User can change preferred settings
* Suiri supports GIF and photo for word picture.
* Suiri has fully multi-language support(Ukrainian and English)

# Setup
If you want to run Suiri locally, you need only 3 steps.
The only thing that you need is installed Docker

```
$ git clone https://github.com/justJavaProgrammer/Suiri.git
$ ./bootstrap.docker.container.sh
$ ./telegram.webhook.register.sh
```
Easy, right?

P.S. You can't register local url as webhook, if you don't want to pay for url you can use [ngrok](https://ngrok.com/)

# Technologies used

```
$ Java 8
$ Spring Framework (Boot, Jpa, MVC)
$ Docker
$ Flyway
$ Postgres
$ Maven
```
