@echo off
echo ========================================
echo   ActSMS - Build Signed Release APK
echo ========================================
echo.

REM Check if keystore exists
if not exist "actsms-release-key.jks" (
    echo ERROR: Keystore not found!
    echo Please run create-keystore.bat first to create a keystore.
    echo.
    pause
    exit /b 1
)

echo Building signed release APK...
echo.

REM Clean previous builds
call gradlew clean

REM Build release APK
call gradlew assembleRelease

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo   BUILD SUCCESSFUL!
    echo ========================================
    echo.
    echo Signed APK location:
    echo app\build\outputs\apk\release\app-release.apk
    echo.
    echo You can now install this APK on any Android device.
    echo Google Play Protect should not block it.
    echo.
    
    REM Open the folder
    explorer app\build\outputs\apk\release
) else (
    echo.
    echo ========================================
    echo   BUILD FAILED!
    echo ========================================
    echo.
    echo Please check the error messages above.
)

echo.
pause
