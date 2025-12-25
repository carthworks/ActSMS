# ActSMS - Quick Start Guide

## 🚀 Getting Started

### Prerequisites
- **Android Studio**: Hedgehog (2023.1.1) or later
- **JDK**: 17 or higher
- **Android SDK**: API 34 (Android 14)
- **Minimum SDK**: API 29 (Android 10)

### Initial Setup

1. **Open Project in Android Studio**
   ```bash
   # Navigate to project directory
   cd c:\Users\tkart\Dev\products\act_sms
   
   # Open in Android Studio
   # File > Open > Select act_sms folder
   ```

2. **Sync Gradle**
   - Android Studio will automatically prompt to sync Gradle
   - Click "Sync Now" when prompted
   - Wait for dependencies to download (~2-5 minutes first time)

3. **Build the Project**
   ```bash
   # Using Gradle wrapper (recommended)
   ./gradlew build
   
   # Or use Android Studio
   # Build > Make Project (Ctrl+F9)
   ```

4. **Run Tests**
   ```bash
   # Run unit tests
   ./gradlew test
   
   # View test results
   # app/build/reports/tests/testDebugUnitTest/index.html
   ```

5. **Install on Device/Emulator**
   ```bash
   # Install debug build
   ./gradlew installDebug
   
   # Or use Android Studio
   # Run > Run 'app' (Shift+F10)
   ```

---

## 📁 Project Structure

```
act_sms/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/actsms/app/
│   │   │   │   ├── data/           # Data layer
│   │   │   │   │   ├── local/      # Room database
│   │   │   │   │   ├── parsing/    # SMS parser
│   │   │   │   │   └── repository/ # Repository implementations
│   │   │   │   ├── di/             # Dependency injection (Hilt)
│   │   │   │   ├── domain/         # Business logic
│   │   │   │   │   ├── model/      # Domain models
│   │   │   │   │   ├── repository/ # Repository interfaces
│   │   │   │   │   └── usecase/    # Use cases
│   │   │   │   └── presentation/   # UI layer
│   │   │   │       ├── navigation/ # Navigation
│   │   │   │       ├── screens/    # Compose screens
│   │   │   │       └── theme/      # Material You theme
│   │   │   ├── res/                # Resources
│   │   │   └── AndroidManifest.xml
│   │   └── test/                   # Unit tests
│   └── build.gradle.kts
├── gradle/
│   └── wrapper/
├── build.gradle.kts                # Root build file
├── settings.gradle.kts
├── README.md
├── IMPLEMENTATION.md               # Implementation tracking
└── PROJECT_SUMMARY.md              # This file

```

---

## 🔧 Development Workflow

### 1. Understanding the Architecture

**ActSMS follows Clean Architecture:**

```
UI (Compose) → ViewModel → Use Case → Repository → Data Source
```

**Example Flow:**
```
DashboardScreen → DashboardViewModel → GetActionsUseCase → ActionRepository → Room Database
```

### 2. Adding a New Feature

**Example: Add a new action type**

1. **Update Domain Model**
   ```kotlin
   // domain/model/ActionCategory.kt
   enum class ActionCategory {
       BILL, EMI, DELIVERY, APPOINTMENT, INFO,
       SUBSCRIPTION // New type
   }
   ```

2. **Add Parsing Pattern**
   ```kotlin
   // data/parsing/SmsParserImpl.kt
   // Add new regex pattern for subscription SMS
   ```

3. **Update UI**
   ```kotlin
   // presentation/screens/dashboard/DashboardScreen.kt
   // Handle new category in UI
   ```

4. **Write Tests**
   ```kotlin
   // test/data/parsing/SmsParserImplTest.kt
   @Test
   fun `parse subscription SMS successfully`() { ... }
   ```

### 3. Running the App

**Debug Build:**
```bash
./gradlew installDebug
adb shell am start -n com.actsms.app.debug/.presentation.MainActivity
```

**Release Build:**
```bash
./gradlew assembleRelease
# APK will be in: app/build/outputs/apk/release/
```

---

## 🧪 Testing

### Unit Tests
```bash
# Run all unit tests
./gradlew test

# Run specific test class
./gradlew test --tests SmsParserImplTest

# Run with coverage
./gradlew testDebugUnitTest jacocoTestReport
```

### Integration Tests
```bash
# Run on connected device/emulator
./gradlew connectedAndroidTest
```

### Manual Testing Checklist
- [ ] SMS permission flow
- [ ] SMS parsing accuracy
- [ ] Action creation
- [ ] Reminder scheduling
- [ ] Notification display
- [ ] Battery optimization handling
- [ ] App restart behavior

---

## 🐛 Debugging

### Enable Logging
```kotlin
// In build.gradle.kts
android {
    buildTypes {
        debug {
            buildConfigField("boolean", "DEBUG_MODE", "true")
        }
    }
}
```

### View Logs
```bash
# Filter by app tag
adb logcat -s ActSMS

# View all logs
adb logcat
```

### Database Inspection
```bash
# Pull database from device
adb pull /data/data/com.actsms.app.debug/databases/actsms_database

# Use Android Studio Database Inspector
# View > Tool Windows > App Inspection > Database Inspector
```

---

## 📦 Dependencies

### Core Dependencies
- **Jetpack Compose**: UI framework
- **Room**: Database (with SQLCipher encryption)
- **Hilt**: Dependency injection
- **WorkManager**: Background tasks
- **Coroutines**: Async programming

### Testing Dependencies
- **JUnit**: Unit testing
- **Mockk**: Mocking
- **Turbine**: Flow testing

---

## 🔐 Security Notes

### Database Encryption
The app uses SQLCipher for database encryption. The encryption key is currently hardcoded for development:

```kotlin
// di/DatabaseModule.kt
val passphrase = SQLiteDatabase.getBytes("ActSMS_Secure_Key_2024".toCharArray())
```

**⚠️ For Production:**
- Use Android Keystore to generate and store encryption key
- Implement key rotation
- Add biometric authentication option

### Permissions
The app only requests `READ_SMS` permission. This is explained clearly in the onboarding screen.

---

## 🎨 UI Customization

### Changing Colors
```kotlin
// presentation/theme/Color.kt
val md_theme_light_primary = Color(0xFF006C51) // Change this
```

### Changing Typography
```kotlin
// presentation/theme/Type.kt
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = YourCustomFont, // Add custom font
        fontSize = 22.sp
    )
)
```

---

## 📱 Testing on Real Device

### Enable Developer Options
1. Go to Settings > About Phone
2. Tap "Build Number" 7 times
3. Go back to Settings > Developer Options
4. Enable "USB Debugging"

### Install App
```bash
# Connect device via USB
adb devices

# Install debug build
./gradlew installDebug

# View logs
adb logcat -s ActSMS
```

### Test SMS Parsing
Send test SMS to your device:
```
Your credit card bill of Rs.5,000 is due on 31-12-2024. Pay now.
```

---

## 🚨 Common Issues

### Issue: Gradle Sync Failed
**Solution:**
```bash
# Clean and rebuild
./gradlew clean
./gradlew build --refresh-dependencies
```

### Issue: SQLCipher Not Found
**Solution:**
```bash
# Ensure dependency is added in app/build.gradle.kts
implementation("net.zetetic:android-database-sqlcipher:4.5.4")
```

### Issue: Compose Preview Not Working
**Solution:**
- Invalidate Caches: File > Invalidate Caches > Invalidate and Restart

### Issue: Permission Not Granted
**Solution:**
- Uninstall app completely
- Reinstall and grant permission when prompted

---

## 📚 Next Steps

1. **Complete SMS Repository**
   - Implement `SmsRepositoryImpl`
   - Read SMS from ContentProvider
   - Filter transactional messages

2. **Implement Background Processing**
   - Create `SmsReceiver`
   - Set up `WorkManager` for periodic scanning
   - Implement `AlarmManager` for reminders

3. **Complete UI**
   - Implement `DashboardViewModel`
   - Create `ActionCard` composable
   - Add action detail screen

4. **Add Tests**
   - Integration tests for SMS → Action flow
   - UI tests for Compose screens

---

## 🤝 Contributing

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable names
- Add KDoc comments for public APIs
- Write tests for new features

### Commit Messages
```
feat: Add delivery tracking support
fix: Handle empty SMS body
test: Add tests for date parsing
docs: Update README with setup instructions
```

---

## 📞 Support

- **Documentation**: See `README.md`, `IMPLEMENTATION.md`, `PROJECT_SUMMARY.md`
- **Architecture**: Check `domain/` package for business logic
- **UI**: Check `presentation/` package for screens
- **Data**: Check `data/` package for database and parsing

---

## ✅ Pre-Launch Checklist

Before releasing to Play Store:

- [ ] Update encryption key to use Android Keystore
- [ ] Complete all features
- [ ] Write comprehensive tests (>80% coverage)
- [ ] Test on multiple devices (Samsung, Xiaomi, OnePlus, Pixel)
- [ ] Test on Android 10, 11, 12, 13, 14
- [ ] Optimize performance (startup time, battery usage)
- [ ] Add ProGuard rules for release
- [ ] Create app icon and screenshots
- [ ] Write privacy policy
- [ ] Prepare Play Store listing
- [ ] Test release build thoroughly

---

**Happy Coding! 🚀**

For detailed implementation status, see `PROJECT_SUMMARY.md`
