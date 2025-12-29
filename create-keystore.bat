@echo off
echo Creating ActSMS Release Keystore...
echo.
echo Please enter the following information:
echo.

keytool -genkey -v -keystore actsms-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias actsms

echo.
echo Keystore created successfully!
echo Location: actsms-release-key.jks
echo.
echo IMPORTANT: Keep this file safe and remember your password!
echo You'll need it for future app updates.
pause
