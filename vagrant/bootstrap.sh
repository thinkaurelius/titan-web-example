#!/usr/bin/env bash

export HOST_IP=$1
export CASS_VER=$2
export ES_VER=$3

apt-get update

#--------------------
# Misc 
#--------------------
sudo apt-get install unzip curl openjdk-8-jdk -y

#--------------------
# Titan
#-------------------
# Note that this is really only added to so you can use the gremlin shell.

if [ ! -e titan* ]; then
  echo "Installing Titan"
  wget https://s3.amazonaws.com/s3.thinkaurelius.com/downloads/titan/titan-1.0.0-hadoop2.zip > /dev/null
  unzip titan-*.zip

	# Clear existing conf file
	echo "" > titan-*/conf/titan-cassandra-es.properties 
	# Set conf file
  sudo tee -a titan-*/conf/titan-cassandra-es.properties <<_EOF_
storage.backend=cassandrathrift
storage.hostname=$HOST_IP
cache.db-cache = false

index.search.backend=elasticsearch
index.search.hostname=$HOST_IP
index.search.elasticsearch.client-only=true
_EOF_

fi


#--------------------
# Elasticsearch
#--------------------
if [ ! -d /etc/elasticsearch ]; then
  echo "Installing Elasticsearch"
  wget https://download.elasticsearch.org/elasticsearch/elasticsearch/elasticsearch-$ES_VER.deb > /dev/null
  sudo dpkg -i elasticsearch-*.deb

  # set listen host
  echo "setting elasticsearch host to $HOST_IP"
  sudo rm /etc/elasticsearch/elasticsearch.yml
  echo "network.host: $HOST_IP" | sudo tee /etc/elasticsearch/elasticsearch.yml > /dev/null

  # Install the head plugin
  sudo /usr/share/elasticsearch/bin/plugin -install mobz/elasticsearch-head
fi

sudo service elasticsearch restart

#--------------------
# Cassandra 
#--------------------
if [ ! -d /etc/cassandra ]; then
  echo "Installing Cassandra"
  sudo tee /etc/apt/sources.list.d/cassandra.sources.list <<_EOF_
deb http://www.apache.org/dist/cassandra/debian $CASS_VER main
deb-src http://www.apache.org/dist/cassandra/debian $CASS_VER main
_EOF_
 
  # add apache cassandra keys
  gpg --keyserver keyserver.ubuntu.com --recv-keys 4BD736A82B5C1B00
  gpg --export --armor 4BD736A82B5C1B00 | sudo apt-key add -
  gpg --keyserver keyserver.ubuntu.com --recv-keys F758CE318D77295D
  gpg --export --armor F758CE318D77295D | sudo apt-key add -
  gpg --keyserver pgp.mit.edu --recv-keys 749D6EEC0353B12C
  gpg --export --armor 749D6EEC0353B12C | sudo apt-key add -
  
  # update repository
  sudo apt-get update
 
  # install
  sudo apt-get install -y cassandra
 
  # set listen host
  sudo sed --in-place "s/localhost/$HOST_IP/g" /etc/cassandra/cassandra.yaml
  sudo sed --in-place "s/127.0.0.1/$HOST_IP/g" /etc/cassandra/cassandra.yaml

  # More sane default configurations - the default 50% free memory allocation is too much for me
  sudo tee -a  /etc/default/cassandra <<"_EOF_"
 
# Set the Max Memory used by Cassandra, change to whatever value you like
MAX_HEAP_SIZE="700M"
HEAP_NEWSIZE="200M"
 
# Performance Tuning
# see also : http://wiki.apache.org/cassandra/PerformanceTuning
JVM_OPTS="$JVM_OPTS -XX:+UseThreadPriorities" # To lower compaction priority
JVM_OPTS="$JVM_OPTS -XX:ThreadPriorityPolicy=42" # To lower compaction priority
JVM_OPTS="$JVM_OPTS -Dcassandra.compaction.priority=1" # To lower compaction priority
JVM_OPTS="$JVM_OPTS -XX:+UseCompressedOops" # enables compressed references, reducing memory overhead on 64bit JVMs
 
_EOF_

fi
 
# restart Cassandra
sudo service cassandra restart

