# Product Brief — SaliX-ai

_Status: prototype · Last updated: 2026-11-02_

## Why this repo exists

The four mobile repos have different jobs:

- **Personal-AI---HCN** — flagship, closest to production (multi-provider AI workspace).
- **Sali-ZZZ** — focused Z.ai client, MVP+ quality.
- **Salvia-AI** — Flutter cross-platform app for the general audience.
- **SaliX-ai (this repo)** — **experiment bed**: validate risky ideas cheaply here, then promote proven ones into the flagship repos.

## Product origin (the question to answer before building features)

- **Problem**: trying new AI-assistant ideas inside a production-grade codebase is slow and risky; the family needs a fast, disposable prototype target.
- **Users**: the developer/maintainer themselves — and, through validated experiments, the future users of the flagship apps.
- **Value**: an always-buildable Android shell where a new idea (new provider, new UI pattern, new interaction) reaches a running APK on a real device within a day.
- **Non-goal**: shipping SaliX-ai to end users. If an experiment survives validation, it graduates to a flagship repo.

## Prototype → MVP → Beta plan

### Phase 1 — Prototype (current)
- [x] Android shell: activity, theme, CI debug build.
- [ ] Wire unit tests into CI.
- [ ] First real screen with mock data.

### Phase 2 — Experiment set 1 (the first validation targets)
Pick from, in priority order:
1. Provider conversation UX: streaming chat with one provider (mock or real), measure how the interaction feels on a low-end device.
2. Onboarding flow draft (key entry + first success action) — prototype the pattern the flagship repos will adopt.
3. Any new model-capability idea (reasoning display, tool use) — prove it here first.

### Phase 3 — MVP gate (definition for this repo)
- Two experiments completed, documented (what was learned, what was dropped).
- Unit tests wired into CI and green.
- Decision log in docs/ for each promoted idea (what moved, to which repo).

### Phase 4 — Beta: not applicable to this repo
Experiments that need beta users belong in a flagship repo, not here.

## Rules for experiments

1. The main branch must always build; broken experiments live on branches.
2. Every experiment gets a short log entry: hypothesis → result → decision.
3. Experiments do not copy the flagship's full architecture — prototype speed wins over purity.
4. Never commit real API keys (.env only).

## Open decisions for the maintainer

- **License**: none chosen yet. The sibling repo Sali-ZZZ uses MIT; pick one explicitly before this repo is promoted/shared.
- **Provider**: which provider the first real experiment should target.
- **Promotion criteria**: what evidence is required to move an experiment into Personal-AI---HCN vs Sali-ZZZ.
