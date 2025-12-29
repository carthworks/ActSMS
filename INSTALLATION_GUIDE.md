# How to Install ActSMS on Android Phones

## 🛡️ Bypassing Google Play Protect

When you try to install ActSMS, Google Play Protect may block it with a warning:
**"App blocked to protect your device - ACTSMS"**

This is normal for apps not from the Play Store. Here's how to install anyway:

---

## ✅ Quick Fix (For Testing)

### **Method 1: Install Anyway**

1. When the Play Protect warning appears, tap **"More details"**
2. Tap **"Install anyway"** or **"Install without scanning"**
3. The app will install successfully

### **Method 2: Temporarily Disable Play Protect**

1. Open **Settings** on your phone
2. Go to **Security** → **Google** → **Play Protect**
3. Tap the **⚙️ Settings** icon (top right)
4. Toggle OFF **"Scan apps with Play Protect"**
5. Install the APK
6. Toggle it back ON after installation (recommended)

---

## 🔐 Permanent Solution (Signed APK)

For a better experience, build a **signed release APK**. This reduces Play Protect warnings.

### **Step 1: Create Keystore (One-time setup)**

1. Open PowerShell/Terminal in the project folder:
   ```powershell
   cd c:\Users\tkart\Dev\products\act_sms
   ```

2. Run the keystore creation script:
   ```powershell
   .\create-keystore.bat
   ```

3. Enter the required information:
   - **Password**: Choose a strong password (remember this!)
   - **Name**: Your name
   - **Organization**: Your company/org name
   - **City, State, Country**: Your location

4. The keystore file `actsms-release-key.jks` will be created

⚠️ **IMPORTANT**: 
- Keep this file safe!
- Remember your password!
- Never share or commit to Git!

### **Step 2: Configure Signing**

Add this to `app/build.gradle.kts` (inside the `android {}` block):

```kotlin
android {
    // ... existing config
    
    signingConfigs {
        create("release") {
            storeFile = file("../actsms-release-key.jks")
            storePassword = "YOUR_PASSWORD_HERE"  // Replace with your password
            keyAlias = "actsms"
            keyPassword = "YOUR_PASSWORD_HERE"    // Replace with your password
        }
    }
    
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

### **Step 3: Build Signed APK**

**Option A: Using Script**
```powershell
.\build-release.bat
```

**Option B: Manual Command**
```powershell
.\gradlew assembleRelease
```

**Option C: Android Studio GUI**
1. **Build → Generate Signed Bundle / APK**
2. Select **APK**
3. Choose your keystore file
4. Enter password
5. Select **release** variant
6. Click **Finish**

### **Step 4: Find the APK**

The signed APK will be at:
```
app\build\outputs\apk\release\app-release.apk
```

This APK is:
- ✅ Properly signed
- ✅ Optimized (smaller size)
- ✅ Less likely to trigger Play Protect
- ✅ Production-ready

---

## 📱 Installation Steps

### **On Your Phone:**

1. **Enable Unknown Sources**:
   - Settings → Security → Install unknown apps
   - Enable for your file manager/browser

2. **Transfer the APK**:
   - USB cable
   - Email/Cloud (Drive, Dropbox)
   - WiFi transfer (ShareIt, Nearby Share)

3. **Install**:
   - Tap the APK file
   - If Play Protect warning appears, tap "Install anyway"
   - Tap **Install**
   - Done! ✅

### **On Other Phones:**

1. Share the APK file via:
   - WhatsApp
   - Email
   - Google Drive
   - Bluetooth
   - Nearby Share

2. Recipient downloads and installs
3. They may need to allow "Install from Unknown Sources"

---

## 🔒 Security Notes

### **Why does Play Protect block it?**
- The app is not from Google Play Store
- It's not signed by a known publisher
- This is normal for sideloaded apps

### **Is it safe?**
- ✅ YES! You built it yourself
- ✅ No malware or tracking
- ✅ 100% privacy-focused
- ✅ Open source code

### **Should I disable Play Protect permanently?**
- ❌ NO! Only disable temporarily
- ✅ Re-enable after installation
- ✅ Play Protect helps protect against malicious apps

---

## 🆘 Troubleshooting

### **"App not installed" error**

**Solution 1**: Uninstall old version first
```
Settings → Apps → ActSMS → Uninstall
```

**Solution 2**: Clear package installer cache
```
Settings → Apps → Package Installer → Storage → Clear Cache
```

**Solution 3**: Restart phone and try again

### **"Parse error" or "Invalid APK"**

- The APK file is corrupted
- Re-download or rebuild the APK
- Ensure complete file transfer

### **Still getting blocked?**

1. Try the signed release APK (more trusted)
2. Disable Play Protect temporarily
3. Use a different file manager to install
4. Check if phone has additional security apps blocking installation

---

## 📊 APK Comparison

| Type | Size | Optimization | Play Protect |
|------|------|--------------|--------------|
| **Debug** | ~20 MB | None | ⚠️ Often blocked |
| **Signed Release** | ~10 MB | Full | ✅ Rarely blocked |

**Recommendation**: Use **Signed Release APK** for distribution

---

## 🚀 Quick Commands

```powershell
# Create keystore (one-time)
.\create-keystore.bat

# Build debug APK (quick testing)
.\gradlew assembleDebug

# Build signed release APK (distribution)
.\build-release.bat

# Clean build
.\gradlew clean
```

---

## 📞 Need Help?

If you encounter issues:
1. Check the error message carefully
2. Try the troubleshooting steps above
3. Rebuild the APK with `.\gradlew clean assembleRelease`
4. Ensure your phone allows app installation from unknown sources

---

**Built with ❤️ for privacy-conscious users**
