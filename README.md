##Introduction
This is an implement for https://github.com/ioof-holdings/recruitment/wiki/Robot-Challenge

##how to Launch

 - export JAVA_HOME=<with a version of Java that is newer than 16> 
 - gradlew run --console=plain

##Examples of use
```
PLACE 1,2,NORTH
MOVE
LEFT
RIGHT
REPORT
PLACE 1,2,EAST
ROBOT 2
REPORT
MOVE
REPORT
```
>**_NOTE:_** The input is considered case-insensitive, and leading or trailing spaces are ignored. In cases where a command does not require parameters, any trailing text is simply disregarded.

##Technologies
```
lombok
Junit
Mockito
```