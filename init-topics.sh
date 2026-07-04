#!/bin/bash
# Create multiple topics with specific configurations
kafka-topics --create --topic topic-a --partitions 3 --replication-factor 1 --bootstrap-server localhost:9092
kafka-topics --create --topic topic-b --partitions 1 --replication-factor 1 --bootstrap-server localhost:9092
kafka-topics --create --topic topic-c --partitions 6 --replication-factor 1 --bootstrap-server localhost:9092