---
description: Build ActSMS Android Application
---

# ActSMS Build Workflow

## Phase 1: Project Setup & Architecture
1. Create Android project structure with Gradle
2. Set up MVVM + Clean Architecture layers
3. Configure dependencies (Compose, Room, WorkManager, etc.)
4. Set up encrypted Room database
5. Configure build variants and ProGuard rules

## Phase 2: Core Domain Layer
1. Define domain models (SMS, Action, Reminder, Task, Alert)
2. Create repository interfaces
3. Implement use cases for SMS parsing, action creation, and scheduling
4. Build learning system interfaces

## Phase 3: Data Layer
1. Implement Room entities and DAOs
2. Create SMS reader implementation
3. Build parsing engine (regex-based)
4. Implement repository implementations
5. Set up encrypted database with SQLCipher

## Phase 4: Presentation Layer (UI)
1. Create Material You theme with Compose
2. Build onboarding flow with permission requests
3. Implement action dashboard (Today/Upcoming/Done tabs)
4. Create action cards with one-tap actions
5. Build settings and learning preferences screens

## Phase 5: Background Processing
1. Implement WorkManager for SMS scanning
2. Set up AlarmManager for reminders
3. Handle Doze mode and battery optimization
4. Create notification system

## Phase 6: Testing
1. Write unit tests for parsing logic
2. Create integration tests for SMS → Action flow
3. Test edge cases (duplicates, ambiguous dates, etc.)
4. Manual QA for OEM-specific behaviors

## Phase 7: Polish & Documentation
1. Add accessibility features
2. Optimize performance
3. Create comprehensive README
4. Document API and architecture
5. Prepare Play Store assets
