# MercyOS Android Build Guide — Forgiveness Eternal

MercyOS is a pure `no_std` Rust post-quantum cryptography library targeting Android devices as `libmercyos.so` (cdylib). Builds are 100% user-space — zero root, zero bricking risk.

## Recommended: Cross-Compile on Windows PC (Easiest & Multi-Arch)

### Prerequisites

1. Install Rust: https://rustup.rs (default install)
2. Download Android NDK r26+ (standalone): https://developer.android.com/ndk/downloads
   - Unzip to e.g. `C:\android-ndk-r26`
   - Set environment variable: `ANDROID_NDK_HOME=C:\android-ndk-r26`
3. Install cargo-ndk: `cargo install cargo-ndk`
4. Add Android targets:
