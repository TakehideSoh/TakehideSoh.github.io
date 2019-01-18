#!/bin/bash

# $1 is 'instance file'
# $2 is 'csp file'
# progJARpath=/Users/soh/app/sucre/sucre.jar
# scalaJARpath=/Users/soh/app/scala-2.11.8/lib/scala-library.jar
# sugarJARpath=/Users/soh/app/sucre/sugar-v2-3-2-min.jar
# claspPath=/Users/soh/app/clasp-3.2.0/build/release/bin/clasp

progJARpath=/Users/soh/02_prog/sucre/sucre.jar
scalaJARpath=/Users/soh/Applications/scala-2.11.6/lib/scala-library.jar
sugarJARpath=/Users/soh/02_prog/sucre/sugar-v2-3-2-min.jar
claspPath=/usr/local/bin/clasp
# sugarOption='-v -v -hybrid -debug 2 -option hy1,pmin -encode'
sugarOption='-v -v -hybrid -option hy1,pmin -encode'

cspFile=$1
opbFile="/tmp/sugar$$.opb"
mapFile="/tmp/sugar$$.map"
opb2File="/tmp/sugar$$.opb2"

function startlog () {
    echo "====== $1 start ======"
    date
    echo "======================"    
}

# java -Xms8g -Xmx8g -cp /Users/soh/02_prog/sucre/sugar-v2-3-2-min.jar jp.kobe_u.sugar.SugarMain -v -v -hybrid -option hy1,pmin -encode test.csp test.opb test.map
# java -Xms8g -Xmx8g -cp /Users/soh/02_prog/sucre/sucre.jar:/Users/soh/Applications/scala-2.11.6/lib/scala-library.jar sucre.solverMain test.opb test.map test.out

function exec () {
    startlog 'encode csp to opb by Sugar'
    echo "java -Xms8g -Xmx8g -cp $sugarJARpath jp.kobe_u.sugar.SugarMain $sugarOption $cspFile $opbFile $mapFile"
    java -Xms8g -Xmx8g -cp $sugarJARpath jp.kobe_u.sugar.SugarMain $sugarOption $cspFile $opbFile $mapFile
    startlog 'sucre solver with clasp'
    echo "java -Xms8g -Xmx8g -cp $progJARpath:$scalaJARpath sucre.solverMain $opbFile $mapFile $opb2File"
    java -Xms8g -Xmx8g -cp $progJARpath:$scalaJARpath sucre.solverMain $opbFile $mapFile $opb2File $claspPath
}

exec

echo "java -Xms8g -Xmx8g -cp $sugarJARpath jp.kobe_u.sugar.SugarMain $sugarOption $cspFile $opbFile $mapFile"
echo "java -Xms8g -Xmx8g -cp $progJARpath:$scalaJARpath sucre.solverMain $opbFile $mapFile $opb2File"
