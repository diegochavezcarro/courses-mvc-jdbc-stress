@echo off
setlocal enabledelayedexpansion

set "BASE_DIR=%~dp0"
set "WRAPPER_DIR=%BASE_DIR%\.mvn\wrapper"
set "WRAPPER_JAR=%WRAPPER_DIR%\maven-wrapper.jar"
set "PROPERTIES_FILE=%WRAPPER_DIR%\maven-wrapper.properties"

if not exist "%WRAPPER_JAR%" (
  set "WRAPPER_URL="
  if exist "%PROPERTIES_FILE%" (
    for /f "usebackq tokens=1* delims==" %%a in ("%PROPERTIES_FILE%") do (
      if "%%a"=="wrapperUrl" set "WRAPPER_URL=%%b"
    )
  )
  if "%WRAPPER_URL%"=="" set "WRAPPER_URL=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"
  echo Downloading Maven Wrapper jar...
  if not exist "%WRAPPER_DIR%" mkdir "%WRAPPER_DIR%"
  powershell -Command "& { $ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -Uri '%WRAPPER_URL%' -OutFile '%WRAPPER_JAR%' }"
)

if defined JAVA_HOME (
  set "JAVA_CMD=%JAVA_HOME%\bin\java.exe"
) else (
  set "JAVA_CMD=java"
)

"%JAVA_CMD%" -classpath "%WRAPPER_JAR%" -Dmaven.multiModuleProjectDirectory="%BASE_DIR%" org.apache.maven.wrapper.MavenWrapperMain %*
endlocal
