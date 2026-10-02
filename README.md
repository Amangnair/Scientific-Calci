# 🧮 Scientific Calculator App

A sleek, modern Android Scientific Calculator application built with **Java**, **Android XML**, and **Material Design 3** components, featuring a custom mathematical expression parsing engine for evaluating complex expressions.

## ✨ Features

### 🔢 Standard & Scientific Operations

- **Basic arithmetic:** Addition (`+`), Subtraction (`−`), Multiplication (`×`), Division (`÷`)
- **Trigonometric functions:** `sin`, `cos`, `tan` — evaluated in degrees
- **Logarithmic functions:** Common logarithm (`log`) and Natural Log (`ln`)
- **Constants & Roots:** Square root (`√`), Pi (`π`), Euler's number (`e`)
- **Instant operations:** Square (`x²`), Reciprocal (`x⁻¹`), Factorial (`!`)

### 🧮 Expression Evaluation

- Supports nested parentheses `(` and `)` for complex arithmetic expressions.
- Handles standard mathematical operator precedence.
- Supports mathematical functions and constants within expressions.
- Uses a lightweight **custom recursive-descent parser** without relying on heavy external mathematical libraries.

### 🖥️ User Interface

- Modern **Material Design 3** interface.
- Dark-themed calculator layout.
- Styled `MaterialButton` components.
- Color-coded key groups for enhanced usability.
- Dual-line expression display showing:
  - The active primary expression.
  - The evaluated secondary result.

## 📱 Pre-built Testing APK

A ready-to-test **Debug APK** is available for direct testing on Android devices without requiring a separate build setup.

### Quick Test / Direct Installation

The debug APK is generated at:

    app/build/outputs/apk/debug/app-debug.apk

To install the APK:

1. Transfer the `.apk` file to your Android phone using USB, Google Drive, messaging apps, or another method.
2. Open the APK on your device.
3. Follow the installation prompts.
4. If prompted, allow installation from unknown sources for the application used to open the APK.

> **Note:** If a pre-built APK is attached to a GitHub Release, it can also be downloaded from the repository's **Releases** section.

## 📂 Project Structure

    scientific-calculator-android/
    │
    ├── app/
    │   ├── src/
    │   │   └── main/
    │   │       ├── java/
    │   │       │   └── com/example/scientificcalci/
    │   │       │       └── MainActivity.java
    │   │       │
    │   │       └── res/
    │   │           └── layout/
    │   │               └── activity_main.xml
    │   │
    │   └── build.gradle
    │
    ├── gradle/
    │   └── ...
    │
    ├── build.gradle
    ├── settings.gradle
    ├── gradlew
    ├── gradlew.bat
    └── README.md

### Key Files

| File | Description |
|---|---|
| `MainActivity.java` | Handles calculator UI events, expression processing, and mathematical evaluation. |
| `activity_main.xml` | Defines the calculator interface and button layout using Material Components. |
| `app/build.gradle` | Contains application dependencies and Android build configuration. |
| `README.md` | Project documentation and setup instructions. |

## 🛠️ Tech Stack & Architecture

| Category | Technology |
|---|---|
| **Language** | Java |
| **UI Framework** | Android XML |
| **UI Components** | Google Material Design 3 |
| **Mathematical Engine** | Custom Recursive-Descent Parser |
| **Minimum SDK** | API 21 (Android 5.0 Lollipop) |
| **Target SDK** | API 34 (Android 14) |
| **Architecture** | Single-Activity Pattern |
| **Main Activity** | `MainActivity` |

### Material Components

The application uses Google's Material Design components, including:

    com.google.android.material.button.MaterialButton

## 🚀 Getting Started

### Prerequisites

Before setting up the project, make sure you have:

- **Android Studio** — Hedgehog (2023.1.1) or newer recommended
- **JDK 17** or higher
- **Android SDK API 34**
- An Android emulator or a physical Android device

### Installation & Setup

#### 1. Clone the Repository

    git clone https://github.com/your-username/scientific-calculator-android.git
    cd scientific-calculator-android

> Replace `your-username/scientific-calculator-android` with the actual GitHub repository URL.

#### 2. Open the Project in Android Studio

1. Launch **Android Studio**.
2. Select **Open** / **Open an Existing Project**.
3. Select the cloned `scientific-calculator-android` directory.
4. Allow Android Studio to sync the Gradle project and download the required dependencies.

#### 3. Build the Debug APK

You can build the application directly from Android Studio or using the Gradle wrapper.

**Windows:**

    gradlew assembleDebug

**macOS / Linux:**

    ./gradlew assembleDebug

The generated APK will be located at:

    app/build/outputs/apk/debug/app-debug.apk

#### 4. Run on Emulator / Device

1. Connect an Android device with **USB Debugging** enabled or launch an Android Emulator.
2. Select the desired device from the Android Studio device selector.
3. Click **Run ▶** or use:

    Shift + F10

The application will be installed and launched on the selected device.

## 🧠 How Expression Evaluation Works

The calculator utilizes a custom **recursive-descent parser** implemented in `MainActivity.java`.

The parser evaluates expressions according to standard mathematical operator precedence.

### 1. Factors / Functions

The lowest-level parsing stage handles:

- Parentheses `()`
- Numbers
- Constants:
  - `π`
  - `e`
- Functions:
  - `sin`
  - `cos`
  - `tan`
  - `log`
  - `ln`
  - `√`

Examples:

    √(25)
    sin(30)
    log(100)
    π × 2

### 2. Terms

Terms handle multiplication and division:

    ×
    ÷

For example:

    2 × 3 ÷ 2

is evaluated according to multiplication/division precedence.

### 3. Expressions

Expressions handle addition and subtraction:

    +
    −

For example:

    10 + 5 × 2

is evaluated as:

    10 + (5 × 2)

resulting in:

    20

This parsing hierarchy allows the application to evaluate complex expressions while maintaining standard mathematical precedence.

## 🧪 Example Expressions

The calculator supports expressions such as:

- `25 + 15`
- `10 × 5 ÷ 2`
- `√(144)`
- `sin(30)`
- `log(100)`
- `ln(e)`
- `(10 + 5) × 2`
- `π × 5²`

## 📦 Dependencies

The project primarily uses Android's standard development libraries together with Google's Material Design components.

The Material Design dependency provides components such as:

    MaterialButton

Gradle manages the required dependencies automatically when the project is opened and synced in Android Studio.

## 🔧 Development

The project is intentionally kept lightweight and uses a **custom mathematical parser** instead of relying on an external expression-evaluation library.

This keeps the expression-processing logic self-contained within the application and provides an example of implementing mathematical expression parsing in Java.
