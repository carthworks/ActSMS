# ActSMS - Architecture Structure

## 📐 **Clean Architecture Overview**

ActSMS follows **Clean Architecture** principles with **MVVM** pattern, ensuring separation of concerns, testability, and maintainability.

```
┌─────────────────────────────────────────────────────────────┐
│                     Presentation Layer                       │
│  (UI, ViewModels, Navigation, Compose Screens)              │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                      Domain Layer                            │
│  (Use Cases, Business Logic, Repository Interfaces)         │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                       Data Layer                             │
│  (Repository Implementations, Room DB, DataStore)           │
└─────────────────────────────────────────────────────────────┘
```

---

## 📁 **Complete Project Structure**

```
act_sms/
│
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/actsms/app/
│   │   │   │   │
│   │   │   │   ├── 📱 presentation/          # Presentation Layer (UI)
│   │   │   │   │   ├── MainActivity.kt       # Main entry point
│   │   │   │   │   │
│   │   │   │   │   ├── screens/              # Compose Screens
│   │   │   │   │   │   ├── welcome/
│   │   │   │   │   │   │   ├── WelcomeScreen.kt
│   │   │   │   │   │   │   └── WelcomeViewModel.kt
│   │   │   │   │   │   │
│   │   │   │   │   │   ├── onboarding/
│   │   │   │   │   │   │   ├── OnboardingScreen.kt
│   │   │   │   │   │   │   └── OnboardingViewModel.kt
│   │   │   │   │   │   │
│   │   │   │   │   │   ├── dashboard/
│   │   │   │   │   │   │   ├── DashboardScreen.kt
│   │   │   │   │   │   │   ├── DashboardViewModel.kt
│   │   │   │   │   │   │   └── DashboardUiState.kt
│   │   │   │   │   │   │
│   │   │   │   │   │   └── settings/
│   │   │   │   │   │       └── SettingsScreen.kt
│   │   │   │   │   │
│   │   │   │   │   ├── components/           # Reusable UI Components
│   │   │   │   │   │   ├── ActionCard.kt
│   │   │   │   │   │   ├── FeatureCard.kt
│   │   │   │   │   │   └── PermissionCard.kt
│   │   │   │   │   │
│   │   │   │   │   ├── navigation/           # Navigation Setup
│   │   │   │   │   │   └── Navigation.kt
│   │   │   │   │   │
│   │   │   │   │   └── theme/                # Material You Theme
│   │   │   │   │       ├── Color.kt
│   │   │   │   │       ├── Theme.kt
│   │   │   │   │       └── Type.kt
│   │   │   │   │
│   │   │   │   ├── 🎯 domain/                # Domain Layer (Business Logic)
│   │   │   │   │   │
│   │   │   │   │   ├── model/                # Domain Models
│   │   │   │   │   │   ├── Action.kt         # Action entity
│   │   │   │   │   │   ├── ActionCategory.kt
│   │   │   │   │   │   ├── ActionStatus.kt
│   │   │   │   │   │   ├── SmsMessage.kt
│   │   │   │   │   │   ├── ParsedSmsData.kt
│   │   │   │   │   │   ├── SenderPreference.kt
│   │   │   │   │   │   ├── UserBehaviorData.kt
│   │   │   │   │   │   └── UserActionType.kt
│   │   │   │   │   │
│   │   │   │   │   ├── repository/           # Repository Interfaces
│   │   │   │   │   │   ├── ActionRepository.kt
│   │   │   │   │   │   ├── SmsRepository.kt
│   │   │   │   │   │   ├── ParsingRepository.kt
│   │   │   │   │   │   └── PreferencesRepository.kt
│   │   │   │   │   │
│   │   │   │   │   └── usecase/              # Use Cases (Business Logic)
│   │   │   │   │       ├── GetActionsUseCase.kt
│   │   │   │   │       ├── ProcessSmsUseCase.kt
│   │   │   │   │       ├── ManageActionUseCase.kt
│   │   │   │   │       └── ScanSmsUseCase.kt
│   │   │   │   │
│   │   │   │   ├── 💾 data/                  # Data Layer (Data Sources)
│   │   │   │   │   │
│   │   │   │   │   ├── local/                # Local Data Sources
│   │   │   │   │   │   │
│   │   │   │   │   │   ├── database/         # Room Database
│   │   │   │   │   │   │   └── ActSmsDatabase.kt
│   │   │   │   │   │   │
│   │   │   │   │   │   ├── dao/              # Data Access Objects
│   │   │   │   │   │   │   ├── ActionDao.kt
│   │   │   │   │   │   │   ├── SmsMessageDao.kt
│   │   │   │   │   │   │   ├── ProcessedSmsDao.kt
│   │   │   │   │   │   │   ├── SenderPreferenceDao.kt
│   │   │   │   │   │   │   └── UserBehaviorDao.kt
│   │   │   │   │   │   │
│   │   │   │   │   │   └── entity/           # Room Entities
│   │   │   │   │   │       ├── ActionEntity.kt
│   │   │   │   │   │       ├── SmsMessageEntity.kt
│   │   │   │   │   │       ├── ProcessedSmsEntity.kt
│   │   │   │   │   │       ├── SenderPreferenceEntity.kt
│   │   │   │   │   │       └── UserBehaviorEntity.kt
│   │   │   │   │   │
│   │   │   │   │   ├── parsing/              # SMS Parsing Engine
│   │   │   │   │   │   ├── SmsParser.kt
│   │   │   │   │   │   ├── PatternMatcher.kt
│   │   │   │   │   │   └── EntityExtractor.kt
│   │   │   │   │   │
│   │   │   │   │   └── repository/           # Repository Implementations
│   │   │   │   │       ├── ActionRepositoryImpl.kt
│   │   │   │   │       ├── SmsRepositoryImpl.kt
│   │   │   │   │       ├── ParsingRepositoryImpl.kt
│   │   │   │   │       └── PreferencesRepositoryImpl.kt
│   │   │   │   │
│   │   │   │   ├── 📡 receiver/              # Broadcast Receivers
│   │   │   │   │   ├── SmsReceiver.kt        # Real-time SMS processing
│   │   │   │   │   ├── BootReceiver.kt       # Reschedule alarms after boot
│   │   │   │   │   └── AlarmReceiver.kt      # Handle reminder alarms
│   │   │   │   │
│   │   │   │   ├── 💉 di/                    # Dependency Injection (Hilt)
│   │   │   │   │   ├── AppModule.kt          # App-level dependencies
│   │   │   │   │   ├── DatabaseModule.kt     # Database dependencies
│   │   │   │   │   ├── RepositoryModule.kt   # Repository bindings
│   │   │   │   │   └── UseCaseModule.kt      # Use case dependencies
│   │   │   │   │
│   │   │   │   └── ActSmsApplication.kt      # Application class
│   │   │   │
│   │   │   ├── res/                          # Resources
│   │   │   │   ├── drawable/                 # Icons and images
│   │   │   │   ├── mipmap/                   # App icons
│   │   │   │   ├── values/                   # Strings, colors, themes
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   └── themes.xml
│   │   │   │   └── xml/
│   │   │   │       └── data_extraction_rules.xml
│   │   │   │
│   │   │   └── AndroidManifest.xml           # App manifest
│   │   │
│   │   ├── test/                             # Unit Tests
│   │   │   └── java/com/actsms/app/
│   │   │       ├── domain/
│   │   │       │   └── usecase/
│   │   │       ├── data/
│   │   │       │   ├── parsing/
│   │   │       │   └── repository/
│   │   │       └── presentation/
│   │   │           └── viewmodel/
│   │   │
│   │   └── androidTest/                      # Instrumented Tests
│   │       └── java/com/actsms/app/
│   │           ├── database/
│   │           ├── ui/
│   │           └── integration/
│   │
│   ├── build.gradle.kts                      # App-level Gradle config
│   ├── proguard-rules.pro                    # ProGuard rules
│   └── schemas/                              # Room database schemas
│
├── gradle/                                   # Gradle wrapper
├── build.gradle.kts                          # Project-level Gradle config
├── settings.gradle.kts                       # Gradle settings
├── gradlew                                   # Gradle wrapper script (Unix)
├── gradlew.bat                               # Gradle wrapper script (Windows)
│
├── 📄 Documentation
├── README.md                                 # Main documentation
├── INSTALLATION_GUIDE.md                     # Installation instructions
├── 16KB_PAGE_SIZE_COMPLIANCE.md             # Android 15+ compliance
├── NAVIGATION_SETTINGS_UPDATE.md            # Navigation updates
├── UPDATES_SUMMARY.md                        # Feature updates
├── QUICKSTART.md                             # Quick reference
├── PROJECT_SUMMARY.md                        # Project overview
│
├── 🔧 Build Scripts
├── create-keystore.bat                       # Keystore creation
├── build-release.bat                         # Release build script
│
└── .gitignore                                # Git ignore rules
```

---

## 🏗️ **Layer Breakdown**

### **1. Presentation Layer** 📱

**Responsibility:** UI and user interaction

**Components:**
- **Screens** - Jetpack Compose UI screens
- **ViewModels** - State management and UI logic
- **Components** - Reusable UI components
- **Navigation** - Screen navigation setup
- **Theme** - Material You theming

**Key Files:**
```kotlin
presentation/
├── MainActivity.kt              # Entry point
├── screens/
│   ├── welcome/
│   │   ├── WelcomeScreen.kt    # UI
│   │   └── WelcomeViewModel.kt # State
│   ├── dashboard/
│   │   ├── DashboardScreen.kt
│   │   └── DashboardViewModel.kt
│   └── settings/
│       └── SettingsScreen.kt
├── components/
│   └── ActionCard.kt            # Reusable card
├── navigation/
│   └── Navigation.kt            # NavHost setup
└── theme/
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

---

### **2. Domain Layer** 🎯

**Responsibility:** Business logic and rules

**Components:**
- **Models** - Domain entities (pure Kotlin)
- **Repository Interfaces** - Data contracts
- **Use Cases** - Business operations

**Key Files:**
```kotlin
domain/
├── model/
│   ├── Action.kt              # Core action model
│   ├── ActionCategory.kt      # BILL, EMI, DELIVERY, etc.
│   ├── ActionStatus.kt        # PENDING, COMPLETED, etc.
│   ├── SmsMessage.kt          # SMS representation
│   └── ParsedSmsData.kt       # Parsed SMS result
├── repository/
│   ├── ActionRepository.kt    # Action data contract
│   ├── SmsRepository.kt       # SMS data contract
│   └── ParsingRepository.kt   # Parsing contract
└── usecase/
    ├── GetActionsUseCase.kt   # Fetch actions
    ├── ProcessSmsUseCase.kt   # Process SMS
    └── ManageActionUseCase.kt # Manage actions
```

---

### **3. Data Layer** 💾

**Responsibility:** Data sources and storage

**Components:**
- **Database** - Room database with SQLCipher
- **DAOs** - Database access objects
- **Entities** - Room entities
- **Parsing** - SMS parsing engine
- **Repository Implementations** - Data operations

**Key Files:**
```kotlin
data/
├── local/
│   ├── database/
│   │   └── ActSmsDatabase.kt  # Room DB
│   ├── dao/
│   │   ├── ActionDao.kt       # Action CRUD
│   │   ├── SmsMessageDao.kt   # SMS CRUD
│   │   └── ProcessedSmsDao.kt # Processed SMS
│   └── entity/
│       ├── ActionEntity.kt    # Room entity
│       └── SmsMessageEntity.kt
├── parsing/
│   ├── SmsParser.kt           # Main parser
│   ├── PatternMatcher.kt      # Regex patterns
│   └── EntityExtractor.kt     # Extract data
└── repository/
    ├── ActionRepositoryImpl.kt
    ├── SmsRepositoryImpl.kt
    └── ParsingRepositoryImpl.kt
```

---

### **4. Receiver Layer** 📡

**Responsibility:** Background processing

**Components:**
- **SmsReceiver** - Real-time SMS processing
- **BootReceiver** - Reschedule alarms after boot
- **AlarmReceiver** - Handle reminder alarms

**Key Files:**
```kotlin
receiver/
├── SmsReceiver.kt       # SMS_RECEIVED broadcast
├── BootReceiver.kt      # BOOT_COMPLETED broadcast
└── AlarmReceiver.kt     # Alarm triggers
```

---

### **5. Dependency Injection** 💉

**Responsibility:** Dependency management

**Components:**
- **Hilt Modules** - Provide dependencies

**Key Files:**
```kotlin
di/
├── AppModule.kt         # App-level dependencies
├── DatabaseModule.kt    # Database instance
├── RepositoryModule.kt  # Repository bindings
└── UseCaseModule.kt     # Use case providers
```

---

## 🔄 **Data Flow**

### **Example: Processing Incoming SMS**

```
1. SMS Arrives
   ↓
2. SmsReceiver.onReceive()
   ↓
3. ProcessSmsUseCase.invoke()
   ↓
4. ParsingRepository.parseSms()
   ↓
5. SmsParser.parse() → Extract data
   ↓
6. ActionRepository.insertAction()
   ↓
7. ActionDao.insert() → Save to Room DB
   ↓
8. DashboardViewModel observes Flow
   ↓
9. DashboardScreen updates UI
```

### **Example: User Completes Action**

```
1. User taps "Complete" button
   ↓
2. DashboardScreen.onClick()
   ↓
3. DashboardViewModel.completeAction()
   ↓
4. ManageActionUseCase.completeAction()
   ↓
5. ActionRepository.markAsCompleted()
   ↓
6. ActionDao.update() → Update Room DB
   ↓
7. PreferencesRepository.recordUserBehavior()
   ↓
8. UserBehaviorDao.insert() → Save behavior
   ↓
9. DashboardViewModel shows snackbar
   ↓
10. DashboardScreen refreshes list
```

---

## 📊 **Technology Stack by Layer**

| Layer | Technologies |
|-------|-------------|
| **Presentation** | Jetpack Compose, Material 3, Navigation Compose, ViewModel, StateFlow |
| **Domain** | Pure Kotlin, Coroutines, Flow |
| **Data** | Room, SQLCipher, DataStore, Kotlin Serialization |
| **DI** | Hilt, Dagger |
| **Testing** | JUnit, Mockk, Compose Testing, Espresso |

---

## 🎯 **Design Patterns Used**

1. **MVVM** - Model-View-ViewModel
2. **Repository Pattern** - Data abstraction
3. **Use Case Pattern** - Single responsibility business logic
4. **Observer Pattern** - Flow/StateFlow for reactive updates
5. **Dependency Injection** - Hilt for loose coupling
6. **Factory Pattern** - ViewModel creation
7. **Strategy Pattern** - SMS parsing strategies

---

## 📈 **Module Dependencies**

```
presentation → domain → data
     ↓           ↓        ↓
  Compose    Use Cases  Room DB
  ViewModel  Models     DAOs
  Navigation Interfaces Entities
```

**Dependency Rules:**
- ✅ Presentation depends on Domain
- ✅ Data depends on Domain
- ❌ Domain does NOT depend on Presentation or Data
- ❌ No circular dependencies

---

## 🔐 **Security Architecture**

```
User Data
    ↓
DataStore (Preferences)
    ↓
Room Database
    ↓
SQLCipher Encryption
    ↓
Encrypted File on Device
```

**Security Layers:**
1. **Encrypted Database** - SQLCipher
2. **No Network** - 100% offline
3. **Minimal Permissions** - SMS only
4. **Secure Storage** - Android Keystore

---

## 🧪 **Testing Strategy**

```
Unit Tests (domain/)
    ├── Use Case Tests
    ├── Model Tests
    └── Parser Tests

Integration Tests (data/)
    ├── Repository Tests
    ├── DAO Tests
    └── Database Tests

UI Tests (presentation/)
    ├── Screen Tests
    ├── ViewModel Tests
    └── Navigation Tests
```

---

## 📦 **Build Configuration**

```
build.gradle.kts (project)
    ├── Kotlin version
    ├── Gradle plugins
    └── Dependencies

build.gradle.kts (app)
    ├── compileSdk: 34
    ├── minSdk: 29
    ├── targetSdk: 34
    ├── Compose BOM
    ├── Room
    ├── Hilt
    └── 16 KB page size support
```

---

## 🚀 **Key Features by Layer**

### **Presentation**
- ✅ Material You dynamic theming
- ✅ Dark mode support
- ✅ Smooth animations
- ✅ Snackbar notifications
- ✅ Navigation with back stack

### **Domain**
- ✅ 6 action categories
- ✅ 5 action statuses
- ✅ Confidence scoring
- ✅ Duplicate detection
- ✅ Learning system foundation

### **Data**
- ✅ SQLCipher encryption
- ✅ Room database
- ✅ DataStore preferences
- ✅ SMS parsing engine
- ✅ Behavior tracking

---

**Status:** ✅ **Production Ready**  
**Architecture:** Clean Architecture + MVVM  
**Completion:** 80% MVP  
**Last Updated:** December 30, 2024
