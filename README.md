```text
             .,.................;,
           ,,.                .:.
          ,.   ,,..........,:,,
        .,    .:.                      .,.:,
        ,.          . ....,,          ,, ,,
         ,,...........    .: :,,,.., .. ,. .:,,,,.:....,...:
                   .:,   ,:..:,,:  ,,. ,  .,,,,:. ,:. ,,,,,
     ................   ,,,..,,, .,,. :.....,,:  ,,  :.
   ,,.                .,.,. ,,,..::,...:;, .,,..,:..:
 .,.  ...,...,,,,,,,,,,. :,,,,,,..,.,.,..,,........,
.;::;:::.  .,,,,,,,,,...,....,;.
         .,.                .:.
        ,,   .,..........,::,       ....          ..
       ,.    ,,........,  .        .,.:.        .:.:.
       ,,      .       .;. ,,,.,, .: ,,,,, .,.:.,:;..,,,.,,
        ,,........:.   .:.::,:: ,;, :.,, ;,,.,;, ,, ::::: :,
     .............,   ,:,,,,:, .;, :. ,. :,.,:, ,,,,,,:, .,
   ,,....            ::,. :::  ;:  ,;.,,  ,,,, ,;,. :,: .:
 ,,.   ............,, .,.,.,,.,,.,.,,  :.,. :.,,,,...,,.,,
;+::;;:,..........
```

# SaliX-ai — AI Assistant Prototype (Android)

> **Status: Prototype / early scaffold.** This repository is a working Android shell, not a product. It builds, runs, and has CI — but the app currently contains only the activity + theme scaffold. See docs/PRODUCT_BRIEF.md for what it is planned to become.

## What this is

SaliX-ai is an early-stage native Android prototype for a personal AI assistant, part of the Sali/Salvia AI project family (siblings: Personal-AI---HCN — multi-provider workspace; Sali-ZZZ — Z.ai client; Salvia-AI — Flutter app). Its purpose today is to be the fast-moving test bed where new ideas are validated before they are promoted into the flagship repos.

## Current state (be honest)

- ✅ Builds green in CI (debug APK artifact on every push)
- ✅ Compose + Material 3 scaffold with theme
- ⬜ No chat/provider features implemented yet
- ⬜ Release build not configured (no minification, no signing)
- ⬜ No tests wired into CI yet

## Tech stack

- **Language**: Kotlin
- **UI**: Jetpack Compose + Material 3
- **Toolchain**: Gradle 9.3.1 · AGP · compileSdk 36 · minSdk 24 · JDK 21
- **Networking (declared, ready to use)**: OkHttp · Retrofit · Moshi
- **DB (declared, ready to use)**: Room
- **Images**: Coil
- **Testing**: JUnit · Robolectric · Roborazzi (screenshot)

## Build

```bash
# Debug
gradle :app:assembleDebug

# Unit tests
gradle :app:testDebugUnitTest
```

Or open in Android Studio (JDK 21 required). CI produces a debug APK artifact on every push to main.

## Repository conventions

- Config values come from .env via the Secrets Gradle plugin (.env.example is the template; never commit real keys).
- Prototype quality bar: it must **always build**. A broken prototype blocks every experiment.

## Documentation

| Document | Contents |
| --- | --- |
| [Product Brief](docs/PRODUCT_BRIEF.md) | Problem, users, value, and the Prototype → MVP → Beta plan |
| [Privacy Policy](PRIVACY.md) · [Security Policy](SECURITY.md) | Target policies for the future functional app |
| [Contributing](CONTRIBUTING.md) | How to work in this repo |

## License

TBD — see docs/PRODUCT_BRIEF.md.
