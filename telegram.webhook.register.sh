#!/bin/bash

: '
*******************************************************
  Bash script to register webhook in telegram API.
  The script requires the bot token in env variables with name BOT_TOKEN and internet connection
  If token is empty or token is not presented in env variables script will wait for input
  Also script requires for Webhook URL. You can set it using command args or using input
*******************************************************
  @author Odeyalo
  @version 1.0.0
'

TELEGRAM_API_URL="https://api.telegram.org/bot${TOKEN}/setWebhook?url=${HOOK}/hook/telegram"
# Support methods to log script execution
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
RED='\033[0;31m'
RESET_COLOR="\e[0m"

function logInfo() {
  echo -e "[${GREEN} INFO ${RESET_COLOR}] $1"
}

function logWarn() {
  echo -e "[${YELLOW} WARN ${RESET_COLOR}] $1 "
}

function logError() {
  echo -e "[${RED} ERROR ${RESET_COLOR}] $1"
}

webhook_url=$1
bot_token=$BOT_TOKEN


if [ -z "$bot_token" ]; then

  read -p "$(logWarn "The bot token was not sent. Enter the bot token: ")" input -r
  bot_token=$input
  logInfo "Webhook url is: $bot_token"
fi;


if [ -z "$webhook_url" ]; then

  read -p "$(logInfo "The webhook was not sent. Enter the webhook url to register: ")" input -r
  webhook_url=$input
  logInfo "Webhook url is: $webhook_url"
fi;

logInfo "Starting registering webhook"

curl -X POST "https://api.telegram.org/bot$bot_token/setWebhook?url=$webhook_url/hook/telegram"

