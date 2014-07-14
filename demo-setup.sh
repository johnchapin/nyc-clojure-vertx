#!/usr/bin/env bash

if [ ! -d vertx-chat ]; then
  echo "Run from the top level directory" >&2 && exit -1
fi

rm -rf mods

pushd vertx-chat-robot

rm -rf target

lein vertx buildmod && \
  mvn install:install-file -Dfile=target/vertx-chat-robot-0.1.0-SNAPSHOT.zip \
  -DgroupId=net.boostrot -DartifactId=vertx-chat-robot -Dversion=0.1.0-SNAPSHOT \
  -Dpackaging=zip

popd

pushd vertx-chat

rm -rf target

lein vertx buildmod && \
  mvn install:install-file -Dfile=target/vertx-chat-0.1.0-SNAPSHOT.zip \
  -DgroupId=net.boostrot -DartifactId=vertx-chat -Dversion=0.1.0-SNAPSHOT \
  -Dpackaging=zip

popd

echo "Copy/paste:"
echo "vertx runmod net.boostrot~vertx-chat-robot~0.1.0-SNAPSHOT -instances 3 -ha"
echo "vertx runmod net.boostrot~vertx-chat~0.1.0-SNAPSHOT -ha"
