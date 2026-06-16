# PrivacyShield

PrivacyShield is an Android utility application that acts as an App Permissions & Privacy Auditor. It scans all installed applications on your device in the background, filters them based on their privacy risk level, and provides a simple dashboard to help you control your privacy.

## Features

- **Privacy Score Dashboard**: A visual meter that calculates the overall privacy health of your device based on installed apps.
- **Risk Categorization**:
  - **High Risk**: Apps with access to Camera, Microphone, Location, SMS, and Contacts.
  - **Medium Risk**: Apps with access to Storage, Media, and Call Logs.
  - **Low Risk**: Standard/Normal access apps.
- **App Filters**: Easily toggle between System Apps and User-installed Apps.
- **Actionable Insights**: Click on any app to view its detailed granted and denied permissions, and easily navigate to the system's "Manage Permissions" page.

## Tech Stack

- **Language**: Kotlin (1.9+)
- **UI Framework**: Jetpack Compose (Modern Declarative UI)
- **Architecture**: MVVM + Clean Architecture Repository Pattern
- **Concurrency**: Kotlin Coroutines & Flow
- **SDK Support**: Target API 34+ (Android 14/15), Minimum API 26 (Android 8.0 Oreo)

## Screenshots

*(Add screenshots here)*

## How to Build

1. Clone the repository: `git clone https://github.com/the-anshul/Privacy-Shield-.git`
2. Open the project in Android Studio.
3. Allow Gradle to sync the project dependencies.
4. Click **Run** to build and install the app on an emulator or physical device.

## License

This project is licensed under the MIT License.
