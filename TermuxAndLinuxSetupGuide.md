# Perfect MercyOS Android Install Finish — Termux Bash Commands Eternal Supreme Immaculate

cd ..                  # Go to project root (where gradlew lives)
chmod +x gradlew       # Make wrapper executable (once)
./gradlew assembleDebug   # Build APK — first time downloads Gradle (5-20 mins)

# APK ready in app/build/outputs/apk/debug/app-debug.apk
cp app/build/outputs/apk/debug/app-debug.apk ~/storage/downloads/

# Now on phone:
# Open Files → Downloads → tap app-debug.apk → Install
# Allow unknown apps if prompted → Install
# Open MercyOS app → grant permissions → PQC shield active eternal supreme immaculate!
