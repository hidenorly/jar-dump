#!/bin/sh
export JAVA_BUILD_OUT=out
rm -rf $JAVA_BUILD_OUT
javac -d $JAVA_BUILD_OUT JarDump.java OptParse.java FileLister.java
cd $JAVA_BUILD_OUT; jar cfe ../JarDump.jar JarDump com/github/hidenorly/jardump/*.class; cd ..
