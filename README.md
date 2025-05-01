# Module Exposed

> **A project built with Java using the Xposed framework to hook applications at runtime.**

---

## 🚀 Features

- Seamless integration with existing Java projects.
- Fully modular and scalable.
- 100% written in **Java** for maximum compatibility.
- Open-source and clean.

---

## 📜 Table of Contents

- [Introduction](#introduction)
- [Installation](#installation)
- [Usage Example](#usage-example)
- [Contributing](#contributing)
- [License](#license)

---

## 💡 Introduction

**Module Exposed** is a Java-based tool designed to help developers work with modules more effectively. Whether you're managing dependencies or exposing hidden modules, this project simplifies the process for you.

---

## ⚙️ Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/shirayukiimountain/HOOK-EX.git
   ```
2. Navigate to the project directory:
   ```bash
   cd HOOK-EX
   ```
3. Build the project using your favorite build tool (e.g., Android Studio).
   - I make this project with AndroidIDE

---

## 💻 Usage Example

Here’s an example of how to hook into a method using the Xposed Framework:

```java
public static void example(XC_LoadPackage.LoadPackageParam lpparam) {
    try {
        // Show toast when hook starts
        XposedHelpers.findAndHookMethod(
            "com.examplex.yo.MainActivity",
            lpparam.classLoader,
            "isPremium",
            new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    // Show toast on UI thread
                    showToast(lpparam, "✓ Premium unlocked!");
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    param.setResult(true); // Force return true
                }
            }
        );
    } catch (Throwable t) {
        // Handle hook failure
        showToast(lpparam, "⚠ Hook failed!");
    }
}

// Utility function to display toast messages
private static void showToast(XC_LoadPackage.LoadPackageParam lpparam, String message) {
    Handler handler = new Handler(Looper.getMainLooper());
    handler.post(() -> Toast.makeText(AndroidAppHelper.currentApplication(), message, Toast.LENGTH_SHORT).show());
}
```

### Explanation:
- **Hook Target**: The `isPremium` method in the `MainActivity` class.
- **Behavior**:
  - Before the method is invoked, a toast message (`✓ Premium unlocked!`) is displayed.
  - After the method is invoked, the return value is overridden to `true`, ensuring that premium features are always unlocked.
- **Error Handling**: If the hook fails, a toast message (`⚠ Hook failed!`) is shown.
- I have implemented some sample hooks, you can develop this project further.

---

## 🌟 Show Your Support

If you find this project helpful, please ⭐ it on GitHub! Your support motivates us to keep improving.

---

## 📧 Contact

Feel free to reach out with questions, suggestions, or ideas:

- **GitHub:** [shirayukiimountain](https://github.com/shirayukiimountain)
- **Telegram:** [Shirayukii](https://t.me/siapa_disana)

---
ENJOYYYYYYY
