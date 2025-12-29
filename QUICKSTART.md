# 🚀 ActSMS - Quick Installation Guide

## ⚡ FASTEST WAY (For Testing)

### When Play Protect blocks the app:

1. **Tap "More details"**
2. **Tap "Install anyway"**
3. Done! ✅

---

## 🔐 BEST WAY (For Distribution)

### Build a signed APK (reduces warnings):

```powershell
# 1. Create keystore (one-time only)
.\create-keystore.bat

# 2. Build signed APK
.\build-release.bat

# 3. Share the APK from:
app\build\outputs\apk\release\app-release.apk
```

---

## 📱 On the Phone

1. **Settings → Security → Install unknown apps** → Enable
2. **Tap the APK file**
3. **Tap "Install anyway"** if warned
4. **Done!** ✅

---

## 🆘 If Installation Fails

```
Settings → Apps → ActSMS → Uninstall old version
Then try again
```

---

See **INSTALLATION_GUIDE.md** for detailed instructions.
