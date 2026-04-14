# SafeHer 🛡️

**SafeHer** is a comprehensive personal safety application designed to provide security and peace of mind. Whether you're walking home late at night or traveling in an unfamiliar area, SafeHer acts as your digital companion, ensuring help is always just a tap away.

## 🚀 Key Features

- **🆘 Instant SOS Alert:** A dedicated emergency button that sends your real-time location and an alert message to all your trusted contacts simultaneously.
- **📍 Live Location Sharing:** Share your live movement with friends or family so they can track your journey until you reach safety.
- **⏲️ Safety Check-In:** Set a timer before heading out. If you don't check in by the time it expires, SafeHer automatically notifies your emergency contacts.
- **📞 Fake Call Simulator:** Discreetly trigger a realistic incoming call to help you exit uncomfortable or potentially dangerous social situations.
- **👥 Emergency Contact Management:** Easily manage a list of trusted individuals who will be notified during emergencies.
- **💡 Safety Tips & Resources:** Access curated safety advice and local emergency numbers (like 112) directly within the app.

## 🛠️ Tech Stack

- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI Framework:** Jetpack (ViewBinding, Navigation Component, Material Components)
- **Database:** [Room Persistence Library](https://developer.android.com/training/data-storage/room) for local contact and setting storage.
- **Architecture:** MVVM (Model-View-ViewModel)
- **Build System:** Gradle (Kotlin DSL)

## 📸 Screenshots

| Home Screen | SOS Active | Fake Call |
| :---: | :---: | :---: |
| ![Home](https://via.placeholder.com/200x400?text=Home+Screen) | ![SOS](https://via.placeholder.com/200x400?text=SOS+Interface) | ![Fake Call](https://via.placeholder.com/200x400?text=Fake+Call) |

## 🛡️ Permissions Used

To provide its core safety features, SafeHer requires:
- `SEND_SMS`: To alert contacts during an SOS.
- `ACCESS_FINE_LOCATION`: For precise real-time location sharing.
- `CALL_PHONE`: To quickly dial emergency services.
- `VIBRATE`: For haptic feedback during alerts.

## 🏗️ Building the Project

You can build the project directly using the provided automation script:

1. Clone the repository.
2. Ensure you have **JDK 17** installed.
3. Run `build_apk.bat` in the root directory.
4. The generated APK will be available in `app/build/outputs/apk/debug/`.

## 📄 License

This project is developed for personal safety and community empowerment.
