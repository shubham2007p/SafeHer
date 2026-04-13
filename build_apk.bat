@echo off
echo ============================================
echo  SafeGuard APK Builder
echo ============================================
echo.

:: --- Set JAVA_HOME ---
set "JAVA_HOME=C:\Android\jdk17\jdk-17.0.11+9"
if not exist "%JAVA_HOME%\bin\java.exe" (
    echo [ERROR] JDK not found at: %JAVA_HOME%
    echo.
    echo Searching for Java...
    for /f "delims=" %%i in ('where java 2^>nul') do (
        set "JAVA_EXE=%%i"
        goto :found_java
    )
    echo [ERROR] Java not found. Please make sure JDK 17 is installed.
    pause
    exit /b 1
)
:found_java
set "PATH=%JAVA_HOME%\bin;%PATH%"
echo [OK] Java found: %JAVA_HOME%
java -version
echo.

:: --- Set ANDROID_HOME ---
set "ANDROID_HOME=C:\Android"
set "PATH=%ANDROID_HOME%\cmdline-tools\latest\cmdline-tools\bin;%ANDROID_HOME%\platform-tools;%PATH%"
echo [OK] Android SDK: %ANDROID_HOME%
echo.

:: --- Accept Licenses ---
echo [STEP 1] Accepting Android SDK licenses...
(for /l %%i in (1,1,20) do echo y) > temp_y.txt
call sdkmanager.bat --sdk_root="C:\Android" --licenses < temp_y.txt >nul 2>&1
del temp_y.txt
echo Done.
echo.

:: --- Install required SDK packages ---
echo [STEP 2] Installing Android SDK packages (platform + build-tools)...
echo This may take a few minutes on first run...
call sdkmanager.bat --sdk_root="C:\Android" "platforms;android-34" "build-tools;34.0.0"
echo Done.
echo.

:: --- Build APK ---
echo [STEP 3] Building SafeGuard APK...
call gradlew.bat assembleDebug
echo.

if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo ============================================
    echo  SUCCESS! APK is ready at:
    echo  app\build\outputs\apk\debug\app-debug.apk
    echo ============================================
    echo.
    echo Opening APK location in Explorer...
    explorer "app\build\outputs\apk\debug"
) else (
    echo [ERROR] Build failed. See output above for details.
)

pause
