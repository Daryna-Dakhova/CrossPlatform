@echo off
java -cp . -Djava.rmi.server.hostname=localhost -Djava.security.policy=program.policy example.ComputeEngine
