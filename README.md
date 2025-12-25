# ActSMS - SMS to Action Planner

**ActSMS** is a privacy-first Android application that automatically converts transactional SMS into structured actions (reminders, tasks, and alerts) using on-device AI and rule-based parsing.

## 🎯 Key Features

- **Automatic SMS Parsing**: Intelligently extracts bills, EMIs, deliveries, and appointments from SMS
- **Smart Reminders**: Auto-creates reminders with context-aware timing
- **Privacy-First**: 100% on-device processing, no cloud services, no analytics
- **Material You Design**: Modern, accessible UI that adapts to your device
- **Learning System**: Adapts to your preferences over time
- **Battery Efficient**: Optimized background processing with WorkManager

## 🏗️ Architecture

ActSMS follows **Clean Architecture** principles with **MVVM** pattern:

```
app/
├── data/           # Data sources, repositories, Room DB
├── domain/         # Business logic, use cases, models
├── presentation/   # UI (Jetpack Compose), ViewModels
└── di/             # Dependency injection
```

### Tech Stack

- **Language**: Kotlin 100%
- **UI**: Jetpack Compose + Material 3
- **Architecture**: MVVM + Clean Architecture
- **Database**: Room (encrypted with SQLCipher)
- **Background**: WorkManager + AlarmManager
- **DI**: Hilt
- **Testing**: JUnit, Mockk, Compose UI Testing

## 📋 Requirements

- Android 10+ (API 29+)
- SMS Read Permission (requested with clear explanation)

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- JDK 17+
- Android SDK 34+

### Build & Run

```bash
# Clone the repository
git clone <repository-url>
cd act_sms

# Build the project
./gradlew assembleDebug

# Run tests
./gradlew test
./gradlew connectedAndroidTest

# Install on device
./gradlew installDebug
```

## 🧪 Testing

- **Unit Tests**: `./gradlew test`
- **Integration Tests**: `./gradlew connectedAndroidTest`
- **Coverage Report**: `./gradlew jacocoTestReport`

## 📱 Features in Detail

### SMS Parsing Engine

- **Rule-based regex** for common SMS patterns
- **Sender classification** (banks, couriers, utilities)
- **Entity extraction**: dates, amounts, tracking numbers
- **Confidence scoring** for parsed data

### Action Types

1. **Reminders**: Time-based notifications
2. **Tasks**: Checklist-style items
3. **Alerts**: Temporary, auto-expiring notifications

### Smart Defaults

- Bills → Remind 2 days before due date
- EMI → Remind 3 days before
- Delivery → Remind same day morning
- Appointment → Remind 1 hour before

### Learning System

- Tracks user interactions (accept/snooze/ignore)
- Adapts reminder timing based on behavior
- Allows sender-level rules

## 🔒 Privacy & Security

- **No cloud sync** - All data stays on device
- **No analytics** - Zero tracking
- **No ads** - Clean, focused experience
- **No login required** - Works immediately
- **Encrypted database** - Room DB with SQLCipher
- **Minimal permissions** - Only SMS read permission

## 📄 License

[Add your license here]

## 🤝 Contributing

[Add contribution guidelines]

## 📞 Support

[Add support information]

---

Built with ❤️ for privacy-conscious users
