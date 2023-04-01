#!/bin/zsh

JAR_FILE=../../../build/libs/simpleWebStudio-0.0.1-SNAPSHOT.jar
NAME=SimpleWebStudio
TARGET_PATH=.
TYPE=dmg
MAIN_CLASS=com.school21.simplewebstudio.SimpleWebStudioApplication
VERSION=1

rm $NAME-$VERSION.$TYPE

jpackage -t $TYPE \
  --input $TARGET_PATH \
  --name $NAME \
  --main-jar $JAR_FILE \
  --main-class $MAIN_CLASS \
  --mac-package-name "SimpleWebStudio" \
  --app-version $VERSION \
  --vendor "student ttanja"