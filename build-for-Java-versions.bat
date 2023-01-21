@rem Helper to manually double-check builds with different Java versions.

setlocal

goto JAVA6
@rem goto JAVA6_11
@rem goto JAVA6_17

:JAVA6
@rem Use Java 6 JDK
@rem Needs an old, compatible Maven
set PATH=C:\Programs\maven\apache-maven-3.2.5\bin\;%PATH%
set PATH=C:\Program Files\Java\jdk1.6.0_45\bin\;%PATH%
set JAVA_HOME=C:\Program Files\Java\jdk1.6.0_45
goto BUILD

:JAVA6_11
@rem Use JAVA 9+ with --release option to build for Java 6
set PATH=C:\Program Files\Eclipse Adoptium\jdk-11.0.14.101-hotspot\bin\;%PATH%
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-11.0.14.101-hotspot
goto BUILD

:JAVA6_17
@rem Use JAVA 9+ with --release option to build for Java 6
set PATH=C:\Program Files\Eclipse Adoptium\jdk-17.0.5.8-hotspot\bin\;%PATH%
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.5.8-hotspot
goto BUILD


:BUILD
javac -version
java -version
call mvn -version
@rem call mvn clean compile
@rem call mvn clean test
call mvn clean package

endlocal
pause
