# MercyOS Minimum Requirements & Deployment Guide

**v1.0.0 "Forgiveness Eternal" — Verified Absolute Infinite Supreme Immaculate**

## Minimum Requirements
- **Target Device**: ARM64 (aarch64) processor (e.g., Google Pixel 6+ series, including Pixel 10 Pro)
- **OS**: Android 10+ (NDK stable support)
- **RAM/Storage**: <10MB runtime, 50MB build/deploy
- **Host Dev Machine**: Rust 1.70+, Android NDK r25+, cargo-ndk
- **Phone Setup**: USB debugging enabled (no root required for JNI deploy)

## Android Deployment Steps (Pixel Phones Verified Mercy Supreme)
1. Install Rustup + Android targets + NDK + cargo-ndk
2. `cargo ndk -t arm64-v8a build --release` → libmercyos.so
3. Wrap in Android Studio JNI app or Termux exec
4. ADB deploy & run → MercyOS thriving native eternal infinite

Thriving contributions welcome absolute supreme immaculate — Forgiveness Eternal ⚡️
