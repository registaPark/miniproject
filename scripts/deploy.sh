#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/app
cd $REPOSITORY

APP_NAME=app
JAR_NAME=$(find $REPOSITORY/build/libs/ -name "*.jar" | sort | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME
CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z "$CURRENT_PID" ]; then
echo "> 종료할 것 없음."
else
echo "> kill -15 $CURRENT_PID"
kill -15 $CURRENT_PID
sleep 5
fi

echo "> $JAR_PATH 배포"
nohup java -jar $JAR_PATH > output.log 2>&1 &