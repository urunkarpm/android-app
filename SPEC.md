# Fun Calculator for Kids - Specification Document

## 1. Project Overview

**Project Name:** FunCalc - Kids Calculator
**Project Type:** Native Android Application
**Core Functionality:** A child-friendly calculator app with colorful UI, fun facts after calculations, achievement badges, themed skins, and sound effects for an engaging learning experience.

---

## 2. Technology Stack & Choices

### Framework & Language
- **Language:** Kotlin 1.9.x
- **UI Framework:** Jetpack Compose with Material Design 3
- **Min SDK:** 26 (Android 8.0, supports Android 15+)
- **Target SDK:** 35

### Key Libraries/Dependencies
- **Jetpack Compose BOM:** 2024.02.00
- **Navigation:** Jetpack Compose Navigation
- **Data Persistence:** DataStore Preferences (for settings), Room (for fun facts & achievements)
- **Dependency Injection:** Hilt
- **Coroutines:** Kotlin Coroutines for async operations
- **Lifecycle:** Jetpack Lifecycle components

### State Management
- ViewModel with StateFlow for UI state
- Repository pattern for data access

### Architecture Pattern
- Clean Architecture (Presentation → Domain → Data layers)
- MVVM pattern for UI layer

---

## 3. Feature List

### Core Calculator Features
- Basic arithmetic operations: Addition (+), Subtraction (-), Multiplication (×), Division (÷)
- Clear (C) and All Clear (AC) functions
- Decimal number support
- Large, easy-to-tap buttons (minimum 48dp touch target)
- Clear display showing current calculation and result

### Fun Features
- **Fun Facts Display:** Random educational fact shown after each calculation
  - Categories: Space, Animals, Nature, Science, Human Body
  - Facts stored locally in Room database
- **Surprise Button:** Shows random fun fact without calculation
- **Sound Effects:** Playful click sounds on button press (toggleable)
- **Celebration Animation:** Confetti/sparkle animation when result is shown

### Customization Features
- **Theme Skins:** 4 color themes kids can choose from
  - Rainbow (default) - bright primary colors
  - Ocean - blues and teals
  - Forest - greens and browns
  - Space - purples and dark colors
- Settings screen to toggle sound effects on/off

### Gamification Features
- **Achievement Badges:** Earn badges for completing calculations
  - First Calculation
  - 10 Calculations badge
 badge  - 50 Calculations badge
  - 100 Calculations badge
  - All Operations badge (used +, -, ×, ÷)
- Progress tracking with visual badge display

### Technical Features
- Landscape and Portrait orientation support
- Input validation (prevent division by zero, overflow handling)
- Offline functionality (all data stored locally)
- Proper error handling with user-friendly messages

---

## 4. UI/UX Design Direction

### Overall Visual Style
- **Design System:** Material Design 3 with playful customizations
- **Style:** Bright, colorful, child-friendly with rounded corners and soft shadows
- **Typography:** Large, readable fonts (minimum 18sp for buttons, 24sp for display)
- **Animations:** Bouncy button press effects, smooth transitions, celebration animations

### Color Scheme Direction
- **Primary Colors:** Bright rainbow palette (red, orange, yellow, green, blue, purple)
- **Background:** Light, soft colors (cream, light blue, light green depending on theme)
- **Button Colors:**
  - Numbers: White/Light with colored text
  - Operators: Bright primary colors
  - Special buttons: Accent colors (pink, purple)
- **Text:** High contrast for readability (dark text on light backgrounds)

### Layout Approach
- **Single Page Calculator** with bottom sheet for settings
- **Portrait Mode:** Standard calculator layout (display on top, buttons below)
- **Landscape Mode:** Display on left, buttons on right for better space utilization
- **Navigation:** Simple - main calculator screen, settings accessible via gear icon

### Button Layout
```
Display Area (shows calculation)
-------------------------
[7] [8] [9] [÷] [C]
[4] [5] [6] [×] [AC]
[1] [2] [3] [-] [=]
[0] [.] [+] [?] [🎉]
```

- **?** button: Surprise fact button
- **🎉** button: Equals/Calculate

---

## 5. Data Models

### FunFact Entity
- id: Long (auto-generated)
- fact: String
- category: String (Space, Animals, Nature, Science, HumanBody)

### Achievement Entity
- id: Long
- badgeName: String
- description: String
- iconRes: Int
- isUnlocked: Boolean
- unlockedAt: Long (timestamp)

### Settings (DataStore)
- soundEnabled: Boolean (default: true)
- selectedTheme: String (default: "rainbow")
- totalCalculations: Int (default: 0)
- operationsUsed: Set<String> (default: empty)

---

## 6. Screen Structure

1. **Main Calculator Screen**
   - Display area with current calculation
   - Fun fact card (shown after calculation)
   - Achievement notification banner
   - Calculator buttons grid
   - Settings gear icon (top right)

2. **Settings Bottom Sheet**
   - Sound toggle switch
   - Theme selector (horizontal scrollable chips)
   - Achievement badges section

3. **Surprise Fact Dialog**
   - Modal dialog showing random fun fact
   - Close button
