# Contributing to SaliX-ai

SaliX-ai is an **experiment bed**: it validates ideas quickly, and proven ideas graduate to the flagship repos (Personal-AI---HCN, Sali-ZZZ, Salvia-AI). Read docs/PRODUCT_BRIEF.md first.

## Setup

- Android Studio · JDK 21 · Gradle 9.3.1 · compileSdk 36 · minSdk 24.
- Build: `gradle :app:assembleDebug` · Tests: `gradle :app:testDebugUnitTest`

## Experiment rules

1. **main must always build.** Broken experiments live on branches.
2. Every experiment gets a log entry in docs/PRODUCT_BRIEF.md (or its own doc): hypothesis → result → decision.
3. Prototype speed beats architectural purity — do not port the flagship's full architecture here.
4. **Never commit real API keys** (.env only, and .env is git-ignored).
5. Short imperative commit messages (fix:/feat:/docs:/test:/chore:).

## PR checklist

- [ ] main still builds and CI is green
- [ ] Experiment documented (hypothesis/result/decision)
- [ ] No secrets in the diff
