#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/app
cd $REPOSITORY

APP_NAME=app
# 수정 전: JAR_NAME=$(ls $REPOSITORY/build/libs/ / grep 'dog-0.0.1-SNAPSHOT.jar' / tail -n 1)
# 수정 후: JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'dog-0.0.1-SNAPSHOT.jar' | tail -n 1)
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'dog-0.0.1-SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)
if [ -z "$CURRENT_PID" ]
then
  echo "> 종료할 것 없음."
else
  echo "> kill -15 $CURRENT_PID"
  # 수정 전: kill -15 $CURRENT_PID
  # 수정 후: kill -9 $CURRENT_PID
  kill -9 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH 배포"
# 수정 전: nohup java -jar $JAR_PATH > /dev/null 2> /dev/null < /dev/null &
# 수정 후: nohup java -jar $JAR_PATH > /dev/null 2>&1 &
nohup java -jar $JAR_PATH > /dev/null 2>&1 &
