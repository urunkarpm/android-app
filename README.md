# FunCalc - Kids Calculator

A child-friendly calculator app for Android with colorful UI, fun facts, achievement badges, and themed skins. 
It was made possible with **Claude Code** and **Ollama**

## Features

### Core Calculator
- Basic arithmetic operations: Addition (+), Subtraction (-), Multiplication (×), Division (÷)
- Clear (C) and All Clear (AC) functions
- Decimal number support
- Large, easy-to-tap buttons (minimum 48dp touch target)
- Clear display showing current calculation and result

### Fun Features
- **Fun Facts Display:** Random educational fact shown after each calculation
  - Categories: Space, Animals, Nature, Science, Human Body
  - Facts stored locally in Room database
- **Surprise Button (?):** Shows random fun fact without calculation
- **Celebration Animation:** Visual feedback when result is shown

### Customization
- **Theme Skins:** 4 color themes kids can choose from
  - Rainbow (default) - bright primary colors
  - Ocean - blues and teals
  - Forest - greens and browns
  - Space - purples and dark colors
- **Settings:** Toggle sound effects on/off (coming soon)

### Gamification
- **Achievement Badges:**
  - First Calculation
  - 10 Calculations
  - 50 Calculations
  - 100 Calculations
  - All Operations (used +, -, ×, ÷)
- Progress tracking with visual badge display

## Technology Stack

- **Language:** Kotlin 1.9.x
- **UI Framework:** Jetpack Compose with Material Design 3
- **Min SDK:** 26 (Android 8.0)
- **Target SDK:** 35

### Key Libraries
- Jetpack Compose BOM 2024.02.00
- Navigation Compose 2.7.7
- Room Database 2.6.1
- DataStore Preferences
- Hilt 2.50 for Dependency Injection
- Kotlin Coroutines

### Architecture
- Clean Architecture (Presentation → Domain → Data layers)
- MVVM pattern for UI layer
- Repository pattern for data access

## Project Structure

```
app/src/main/java/com/funcalc/app/
├── data/
│   ├── local/
│   │   ├── dao/           # Room DAOs
│   │   ├── database/      # Room Database
│   │   └── entity/        # Room Entities
│   └── repository/        # Data Repositories
├── di/                    # Hilt Dependency Injection Modules
├── domain/
│   └── model/             # Domain Models
└── presentation/
    ├── calculator/        # Calculator Screen & ViewModel
    ├── components/        # Reusable UI Components
    ├── settings/          # Settings Screen
    └── theme/             # App Theme & Colors
```

## Building the Project

### Prerequisites
- Android Studio (Arctic Fox or newer)
- JDK 17
- Android SDK 35

### Build Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Run tests
./gradlew test
```

### Output
Debug APK will be generated at:
```
app/build/outputs/apk/debug/app-debug.apk
```

## Installation

1. Enable "Install from unknown sources" on your Android device
2. Transfer the APK to your device
3. Open the APK file and install
4. Launch "FunCalc" from the app drawer

## Screenshots

The app features a colorful, child-friendly interface with:
- Large, easy-to-tap buttons
- Rainbow color scheme
- Fun fact dialogs after calculations
- Settings bottom sheet for theme selection
- ![Layout](https://github.com/user-attachments/assets/a4d73e0d-f9c3-494a-b8b2-c8a771e5faa5) ![Settings](https://github.com/user-attachments/assets/95161194-1c6f-4248-9180-3e7b69bed8af) ![Fact](https://github.com/user-attachments/assets/107e0b60-c696-4619-9d2f-e200c8542a7c)

## License

This project is for educational purposes.

## Author

Created with Jetpack Compose
