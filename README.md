# DAM 2525 — Desenvolvimento de Aplicações Móveis - LEIM61N Class

> Android development with Jetpack Compose — building a real production-quality app from the ground up across 12 weeks.

![Kotlin](https://img.shields.io/badge/Kotlin-2.0-7c6af7?style=flat&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-2024.10-6ecfb0?style=flat&logo=android&logoColor=white)
![ISEL](https://img.shields.io/badge/ISEL-DAM%202525-ff8c69?style=flat)
![License](https://img.shields.io/badge/License-Academic-ffd166?style=flat)

---

## Goals

By the end of this course students will have built **Trivia Sparks** — a full-featured multiplayer quiz app — using the same tools, architecture patterns, and practices used in professional Android development.

Along the way students will be able to:

- Build production-grade Android UIs with **Jetpack Compose** and **Material Design 3**
- Apply **Clean Architecture** with **MVVM** to structure scalable, testable apps
- Fetch, cache, and display real data using **Retrofit**, **ROOM**, and an offline-first strategy
- Integrate **Firebase** for authentication, a real-time leaderboard, and live multiplayer
- Write **unit tests** for ViewModels and **UI tests** for critical screens
- Work with **Git** using a professional branching and commit workflow
- Document code following **KDoc** standards

---

## Repository Structure

```
dam-2525/
│
├── app/                        ← Trivia Sparks — the course project
│   └── See app/README.md for setup instructions and architecture overview
│
└── classes/                    ← Weekly class materials
    ├── week-01/
    │   ├── exercises/          ← Kotlin exercises done in class
    │   ├── challenges/         ← Take-home challenges
    │   └── snippets/           ← Code snippets from the presentations
    ├── week-02/
    │   └── ...
    └── ...
```

### `app/` — Trivia Sparks

The main course project. A real-time multiplayer quiz app built progressively across all 12 weeks. Each week adds a new layer — from static UI in Week 2 to full Firebase multiplayer in Week 11.

→ **[app/README.md](./app/README.md)** — setup instructions, architecture overview, and design system  
→ **[Course Wiki](../../wiki)** — detailed week-by-week guides, KDoc standards, troubleshooting, and glossary

### `classes/` — Weekly Materials

Everything written or discussed in class lives here, organised by week. Each week folder contains:

| Folder | Contents |
|---|---|
| `exercises/` | Kotlin and Compose exercises completed during the session |
| `challenges/` | Take-home challenges to submit before the next class |
| `snippets/` | Self-contained code snippets that illustrate concepts from the presentations |

> Code in `classes/` is intentionally simplified — it demonstrates individual concepts in isolation. The production-quality implementation of the same concepts lives in `app/`.

---

## What We Learn

The course is divided into four phases. Each phase builds directly on the previous one.

### Phase 1 — Foundation

| Topic | Deliverable |
|---|---|
| Kotlin essentials — data classes, sealed classes, null safety, lambdas | Project setup + domain models |
| Jetpack Compose — layouts, modifiers, Material3, theme | HomeScreen · CategoryScreen · QuizDetailScreen · QuizScreen |
| State management — `remember`, `mutableStateOf`, `LaunchedEffect` | Interactive quiz — timer, answer selection, search filtering |
| Navigation — `NavHost`, `NavController`, type-safe routes | Full screen navigation graph |

### Phase 2 — Data & Network

| Topic | Deliverable |
|---|---|
| MVVM — ViewModel, StateFlow, Unidirectional Data Flow | Quiz logic driven by ViewModel |
| Coroutines + REST API — Retrofit, Open Trivia Database | Real questions and categories from the API |
| ROOM + offline-first — DAO, entities, caching strategy | Quiz history + full offline support |

### Phase 3 — Firebase

| Topic | Deliverable |
|---|---|
| Firebase Auth — email, Google Sign-In, session persistence | Login · Register · Profile screens |
| Cloud Firestore — real-time queries, score sync | Global leaderboard |
| Hilt — dependency injection, modules, scopes | Full DI refactor of the entire app |

### Phase 4 — Advanced

| Topic | Deliverable |
|---|---|
| Firebase Realtime Database — multiplayer rooms, live sync | Real-time head-to-head quiz |
| Testing + polish — JUnit, Turbine, Compose UI tests, dark mode, accessibility | Unit tests · UI tests · Accessible app |

---

## Releases & Tags

Each deliverable is marked with a Git tag in the `app/` folder. You can check out any tag to see the app in the exact state it was in at the end of that delivery.

| Tag | Deliverable |
|---|---|
| `v0.1-foundation-ui` | Static screens — HomeScreen, CategoryScreen, QuizDetailScreen, QuizScreen |
| `v0.2-state` | Interactive quiz — timer, answer selection, search filtering |
| `v0.3-navigation` | Full navigation graph — all screens connected |
| `v0.4-mvvm` | MVVM — ViewModel and StateFlow driving every screen |
| `v0.5-api` | REST API — real questions and categories from Open Trivia Database |
| `v0.6-room` | ROOM — quiz history persisted, full offline support |
| `v0.7-auth` | Firebase Auth — login, register, Google Sign-In, session |
| `v0.8-firestore` | Firestore — global leaderboard with real-time updates |
| `v0.9-hilt` | Hilt — full dependency injection refactor |
| `v0.10-multiplayer` | Multiplayer — real-time head-to-head quiz rooms |
| `v1.0.0` | Final release — tests, dark mode, accessibility, polish |

### Viewing a tagged version on GitHub

Navigate to **Code → Tags** in the repository, or append `/releases/tag/v0.1-foundation-ui` to the repo URL to jump directly to any release.

---

## Tech Stack

| | Technology |
|---|---|
| **Language** | Kotlin 2.0 |
| **UI** | Jetpack Compose + Material Design 3 |
| **Architecture** | Clean Architecture + MVVM |
| **Navigation** | Navigation Compose |
| **DI** | Hilt |
| **Local DB** | ROOM |
| **Networking** | Retrofit + OkHttp |
| **Auth** | Firebase Authentication |
| **Remote DB** | Firebase Firestore |
| **Real-time** | Firebase Realtime Database |
| **Images** | Coil 3 |
| **Testing** | JUnit · Turbine · Compose UI Tests |

---

## Important Dates

| Date | Event |
|---|---|
| `DD/MM/YYYY` | First class — Week 1 |
| `DD/MM/YYYY` | Week 6 deliverable deadline |
| `DD/MM/YYYY` | Mid-term review |
| `DD/MM/YYYY` | Week 12 — final session |
| `DD/MM/YYYY` | Project submission deadline |
| `DD/MM/YYYY` | Final presentations |

> ⚠️ All deadlines are at **23:59 (Lisbon time)** on the date indicated. Late submissions are not accepted unless agreed in advance.

---

## Important Links

### Course

| | Link |
|---|---|
| 📚 Course Wiki | [github.com/your-username/dam-2525/wiki](https://github.com/your-username/dam-2525/wiki) |
| 🎨 Figma designs | [Trivia Sparks — Figma file](#) *(link coming)* |
| 🏫 Course page | [Academic portal](#) *(link coming)* |
| 💬 Class channel | [Slack / Teams channel](#) *(link coming)* |

### Documentation

| | Link |
|---|---|
| 📖 Kotlin docs | [kotlinlang.org/docs](https://kotlinlang.org/docs/home.html) |
| 📖 Jetpack Compose | [developer.android.com/compose](https://developer.android.com/jetpack/compose) |
| 📖 Material Design 3 | [m3.material.io](https://m3.material.io/) |
| 📖 Android architecture | [developer.android.com/architecture](https://developer.android.com/topic/architecture) |
| 📖 Firebase docs | [firebase.google.com/docs](https://firebase.google.com/docs) |
| 🔌 Open Trivia API | [opentdb.com/api_config.php](https://opentdb.com/api_config.php) |

### Tools

| | Link |
|---|---|
| 🛠 Android Studio | [developer.android.com/studio](https://developer.android.com/studio) |
| 🛠 Firebase Console | [console.firebase.google.com](https://console.firebase.google.com) |
| 🛠 Conventional Commits | [conventionalcommits.org](https://www.conventionalcommits.org/) |

---

## Wiki

The course Wiki is the main reference for students between sessions.

| Page | Description |
|---|---|
| [Home](../../wiki/Home) | Table of contents and weekly progress tracker |
| [00 — Project Setup](../../wiki/00-project-setup) | Folder structure, dependencies, KDoc guidelines, Git workflow |
| [App — Trivia Sparks](../../wiki/App-Trivia-Sparks) | Features, design system, Figma screens, data models |
| [Course Roadmap](../../wiki/Course-Roadmap) | All 12 topics mapped to app features |
| [Week 1](../../wiki/Week-1) | Kotlin essentials |
| [Week 2](../../wiki/Week-2) | Jetpack Compose fundamentals |
| [Glossary](../../wiki/Glossary) | Plain-English definitions of every term introduced |
| [Troubleshooting](../../wiki/Troubleshooting) | Common errors and their fixes |

---

## Getting Started

### Prerequisites

- Android Studio **Ladybug 2024.2.1** or later
- JDK 17 (bundled with Android Studio)
- Android device or emulator running **API 24+**
- Git configured with your university email

### Clone and run

```bash
git clone https://github.com/your-username/dam-2525.git
cd dam-2525/app
```

Open the `app/` folder in Android Studio and wait for Gradle sync.

> The app requires a `google-services.json` file for Firebase. This file is provided separately by the professor — do not commit it to the repository.

---

## Code of Conduct

- Write code you would be comfortable showing in a professional interview
- Document every public composable and every domain model (see [KDoc guidelines](../../wiki/00-project-setup#kdoc-guidelines))
- Review your teammates' PRs constructively
- Ask questions early — in class, on the class channel, or by opening a GitHub issue

---

<div align="center">
  DAM 2525 · ISEL · 2025/2026
</div>


