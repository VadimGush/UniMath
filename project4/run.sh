#!/bin/bash
javac -sourcepath src -d out src/net/djuke/vadim/Main.java
java -cp out net.djuke.vadim.Main
