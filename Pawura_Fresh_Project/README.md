# Pawura Android project

This is a clean Android Studio project for the Pawura starter app.

## Open in Android Studio
1. Extract this ZIP.
2. In Android Studio choose **Open** and select the extracted `Pawura_Fresh_Project` folder.
3. Let Gradle sync complete.
4. Select the `app` run configuration and run on an Android device or emulator (Android 7.0 / API 24 or newer).

## Build an APK with GitHub Actions
1. Upload the entire contents of this folder to the root of a GitHub repository (keep the `.github` folder).
2. Open the repository's **Actions** tab.
3. Select **Build Pawura APK** and run the workflow, or push a commit to `main`/`master`.
4. When the run succeeds, open the run and download the `Pawura-debug-apk` artifact.
5. Install `app-debug.apk` on your Android device.

## Important
- The launcher activity is explicitly declared as `com.pawura.app.MainActivity`.
- The Kotlin file is located at `app/src/main/java/com/pawura/app/MainActivity.kt`.
- The workflow checks that the activity source and manifest agree before compiling.
- This is a starter UI: sign-in, product inventory, payments, delivery and backend services are placeholders and are not connected.
- The APK is produced by GitHub Actions; this environment does not have the Android SDK installed, so I cannot honestly claim a local APK build or device-launch test.
