# Security Policy — SaliX-ai

## Supported versions

The main branch is the only supported version. Prototype releases are debug builds from CI.

## Rules that apply from day one

- **No secrets in source**: configuration comes from .env via the Secrets Gradle plugin; .env is git-ignored and .env.example contains no real values.
- **CI never receives real keys**.
- Any future networking code must use **HTTPS only**.

## Reporting a vulnerability

Open a **private security advisory** in this repository. Do not open public issues for exploitable findings. Even in a prototype, key-handling mistakes are worth reporting early.
