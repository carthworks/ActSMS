

# 📘 PRODUCT REQUIREMENTS DOCUMENT (PRD)

## Product Name (Working)

**ActSMS** — *SMS → Action Planner*

---

## 1. Product Vision

Convert important SMS into **structured actions** (reminders, tasks, alerts) automatically, **on-device**, with zero manual input.

This is **not** a messaging app.
This is an **automation + planning engine powered by AI**.

---

## 2. Goals (Success Criteria)

* Reduce missed payments / appointments
* Zero manual task entry for 80% of use cases
* High daily retention (utility product)
* Trust-first, privacy-first design

---

## 3. Target Platform

* **Android 10+**
* India-first (SMS formats, banks, couriers, utilities)
* Offline-first, local-only processing

---

## 4. Target Users (Primary)

* Working professionals (25–45)
* Managing EMIs, bills, deliveries, appointments
* Low tolerance for manual planning

---

## 5. Core User Flow

1. User installs app
2. Grants **SMS Read Permission** (with clear justification)
3. App scans last 30 days of SMS (local only)
4. Detects actionable messages
5. Auto-creates:

   * Reminder
   * Task
   * Alert
6. User confirms / snoozes / ignores
7. App learns preferences over time

---

## 6. Functional Requirements

### 6.1 SMS Ingestion

* Read incoming and historical SMS
* Filter **transactional SMS only**
* Ignore OTPs, spam, promos

### 6.2 AI Parsing Engine

Hybrid approach:

**Phase 1 (MVP)**

* Rule-based regex parsing
* Sender-based classification
* Keyword detection

**Phase 2**

* On-device NLP (TensorFlow Lite)
* Confidence scoring

Extract:

* Category: Bill | EMI | Delivery | Appointment | Info
* Due date / time
* Amount
* Sender
* Urgency score (0–1)

---

### 6.3 Action Engine

Auto-create:

* Reminder (time-based)
* Task (checklist-style)
* Temporary alert (auto-expire)

Default reminder rules:

* Bills → T-2 days
* EMI → T-3 days
* Delivery → Same day morning
* Appointment → T-1 hour

User can override rules.

---

### 6.4 Action Dashboard (Home Screen)

Tabs:

* **Today**
* **Upcoming**
* **Completed**

Each card shows:

* Title
* Due date
* Action buttons (Pay / Track / Call / Open App)

---

### 6.5 Notification System

* Smart notifications only
* No spam
* Group similar alerts
* Silent mode respected

---

### 6.6 Learning & Personalization

* Track user actions:

  * Accept
  * Snooze
  * Ignore
* Adapt reminder timings
* Allow sender-level rules:

  * “Always ignore Swiggy promos”
  * “Always remind for bank SMS”

---

### 6.7 Privacy & Security (Mandatory)

* No cloud sync by default
* No analytics SDKs
* No ads
* No login required
* Optional encrypted local backup/export

---

## 7. Edge Cases (Must Handle)

### SMS Issues

* No date mentioned → mark as “Info only”
* Multiple dates → pick nearest future date
* Ambiguous language → ask user once
* Duplicate SMS → deduplicate
* Partial SMS (truncated)

### User Behavior

* User ignores many reminders → auto-reduce frequency
* Phone reboot → reminders persist
* App killed → alarms still fire

### Permissions

* Permission denied → graceful degraded mode
* Permission revoked later → notify user clearly

---

## 8. Non-Functional Requirements

### Performance

* SMS scan < 2 seconds (30 days)
* Parsing latency < 200ms per SMS

### Reliability

* Reminders must fire even in Doze mode
* Battery-efficient background work

### Accessibility

* Large text support
* High-contrast mode
* One-hand usage

---

## 9. Tech Stack (Recommended)

* **Language:** Kotlin
* **Architecture:** MVVM + Clean Architecture
* **DB:** Room (encrypted)
* **Background:** WorkManager + AlarmManager
* **AI:** Regex + TensorFlow Lite (on-device)
* **UI:** Jetpack Compose (Material You)

---

## 10. Testing Requirements

### Unit Tests

* SMS parsing rules
* Date extraction
* Duplicate detection

### Integration Tests

* SMS → Action pipeline
* Reminder scheduling

### Edge Case Tests

* Invalid SMS formats
* Locale differences
* Timezone change
* DST change

### Manual QA

* Battery optimization scenarios
* Permission revoke flows
* OEM-specific behavior (Samsung, Xiaomi, Oppo)

---

## 11. MVP Out of Scope (Explicitly)

* iOS
* Cloud sync
* Chat features
* Notes
* Ads
* Social sharing

---

# 🎯 VIBECODE PROMPT (FOR AI BUILD AGENT)

Use this **exact prompt** with VibeCode / Cursor / Copilot / AI Dev Agent:

---

**Prompt:**

> Build a production-ready Android application called **“ActSMS”**.
>
> The app reads **transactional SMS locally on the device**, parses them using **rule-based logic and on-device AI**, and automatically converts them into **reminders, tasks, and alerts**.
>
> **Hard constraints:**
>
> * Android 10+
> * Kotlin + Jetpack Compose
> * MVVM + Clean Architecture
> * Room DB (encrypted)
> * WorkManager + AlarmManager for reminders
> * NO cloud services
> * NO ads
> * NO analytics SDKs
> * NO user login
>
> **Core features to implement:**
>
> 1. SMS permission onboarding with clear explanation
> 2. Local SMS scanning and filtering
> 3. AI-powered SMS parsing (regex first, pluggable ML later)
> 4. Automatic reminder/task creation with smart defaults
> 5. Action dashboard (Today / Upcoming / Done)
> 6. One-tap actions (Pay, Track, Call, Open App)
> 7. Learning system to adapt reminder behavior
> 8. Full edge-case handling (ambiguous dates, duplicates, missing info)
>
> **UI requirements:**
>
> * Modern Material You design
> * Minimal, calm, trust-first interface
> * One-hand friendly
> * Accessibility compliant
>
> **Testing:**
>
> * Unit tests for parsing and scheduling
> * Integration tests for SMS → Action flow
> * Robust error handling
>
> Deliver clean, modular, well-documented code suitable for Play Store submission.

---

## Final Reality Check

This is:

* **Buildable**
* **Defensible**
* **Actually useful**

But only if you **ship MVP fast**, measure usage, and kill features aggressively.

If you want next:

* Regex patterns for Indian SMS
* Jetpack Compose screen layout
* Play Store permission justification text
* 30-day execution plan

Say what you want next.
