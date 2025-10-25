#!/usr/bin/env zsh
#export JAVA_HOME=<point to your jdk folder>
#export M2_HOME=<point to your maven folder>

set -e

export JAVA_HOME="/usr/lib/jvm/java-21-openjdk-amd64"
export M2_HOME="/opt/maven"

echo "Detecting environment variables..."
echo "Java Home: $JAVA_HOME"
echo "Maven Home: $M2_HOME"

echo "Updating path..."

export PATH=$JAVA_HOME/bin:$PATH
export PATH=$M2_HOME/bin:$PATH

echo "Building..."
sam build

# COMMENT BELOW WHEN DEBUG MODE IS ENABLED
echo "Running..."
sam local start-api \
  --port 3000 \
  --host 0.0.0.0 \
  --docker-network sam-local-net \
  --warm-containers EAGER

# UNCOMMENT BELOW FOR DEBUG MODE
#echo "Running in DEBUG MODE..."
#sam local start-api \
#  --port 3000 \
#  --host 0.0.0.0 \
#  --docker-network sam-local-net \
#  --debug-port 5005 \
#  --debug-args "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005" \
#  --warm-containers EAGER