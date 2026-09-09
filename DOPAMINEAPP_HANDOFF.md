# DOPAMINEAPP_HANDOFF.md

## A. Project Purpose
A Jetpack Compose Android application centered on simulated digital gratification for luxury consumer products. The platform mimics a hyper-realistic e-commerce experience (mock checkouts, delivery tracking, virtual EMIs) tailored around dopamine-driven user interactions, utilizing satirical parity items to avoid IP litigation.

## B. Current State
Development is active but transitioning to a new session. The core Jetpack Compose UI shell, Room Database, and simulated gratification engines are established. The code currently in the `main` branch of `CodeCrafter-AT/DopamineApp` is the absolute source of truth.

## C. What Sessions 1–7 Accomplished
*   **Session 1:** Established the minimalist App Shell, 3-tab navigation (Feed, Vault, Profile), and locked in the luxury design system.
*   **Session 2:** Audited the codebase, fixed Gradle (locked to 9.3.1), generated a local `debug.keystore`, fixed Room deprecations, and mapped hardcoded colors to the Theme.
*   **Session 3:** Built the `SapolskyEngine` (variable reward probability) and the independent background `OrderEngine` (simulating a 5-second progression delay for orders). Deferred rewards until the "Delivered" state.
*   **Session 4:** Built the Virtual EMI checkout and "UPI Reflex" module. Implemented a realistic 2-step mock payment overlay utilizing Android `LocalHapticFeedback` and a localized `TextToSpeech` merchant soundbox chime.
*   **Session 5:** Created `DPDPPrivacyMiddleware` to permanently coarsen location data (e.g., "UP-Sitapur") and mask names on the backend *only*. Added localized Affiliate Coupon and Refer & Earn modals triggered upon delivery. 
*   **Session 6:** Upgraded the tracking dashboard with a hyper-realistic timeline (Dispatched → Sorting → Out for Delivery → Delivered) and embedded a monetization/ad placeholder.
*   **Session 7:** Built the Satirical Brand Registry (Folex, Ghumato, ZeptOut). Integrated the `Coil` library for async image loading. Updated CTA text from "Add to Cart" to "Simulate Purchase" for legal UX clarity.

## D. Important Decisions
*   **Source of Truth:** Historical claims are subordinate to the current Git repository state.
*   **PII Masking Scope:** Data masking (`maskPII`) must strictly happen on the backend intent side before saving to `OrderEntity`. The frontend profile/login must always retain the unmasked real name.
*   **Background Processing:** The `OrderEngine` relies on an independent Coroutine scope (`SupervisorJob`) so it continues running even if the user closes the checkout modal.
*   **Starting Order State:** The default starting status for a new order in `OrderEntity` is "Order Dispatched from Delhi Hub".

## E. Features Already Implemented
*   Jetpack Compose 3-tab navigation.
*   Room Database (`AppDatabase.kt`) with `Entities.kt`.
*   Delayed Gratification / Sapolsky logic.
*   Haptic and TextToSpeech UI feedback.
*   Coil image loading with Vector Canvas fallback.

## F. Features Still Incomplete / Known Bugs
*   *Technical Debt:* Needs migration from manual ViewModel dependency injection to Dagger-Hilt.
*   *Technical Debt:* Heavy `Box` and `Canvas` logic re-evaluated without remembering derived states.
*   *Technical Debt:* Need to refactor duplicated button stylings into a unified `NoirButton`.
*   *Pending Feature:* The monetization placeholder added in Session 6 needs to be wired to a functional mock ad or affiliate network.

## G. Important Files and Their Contents
*   `MainActivity.kt`: Core navigation and crossfade routing.
*   `Theme.kt` & `Color.kt`: The absolute source of truth for all styling.
*   `SapolskyEngine.kt` & `OrderEngine.kt`: Core business logic for gratification and background order progression.
*   `DPDPPrivacyMiddleware.kt`: Location coarsening and name masking.
*   `UpiPaymentSheet.kt`: The 2-step checkout state machine with haptics/audio.

## H. Important Dependencies/Integrations
*   Jetpack Compose (UI)
*   Room Database (Local Storage)
*   Coil (`io.coil-kt:coil-compose`) for image loading.
*   Native Android `TextToSpeech` and `LocalHapticFeedback`.

## I. UI/UX Decisions to Preserve
*   **Strict Color Palette:** White canvas (`#FFFFFF`), light gray surfaces (`#FAFAFA`), subtle borders (`#EAEAEA`), rich black typography (`#111111`).
*   **Single Accent:** The accent purple color is reserved *exclusively* for rewards/success states.
*   **Terminology:** Use "Simulate Purchase" instead of "Add to Cart". Show "Virtual EMI" alongside MRP. 

## J. Technical Architecture
*   Frontend: Android Jetpack Compose
*   Backend/Local Storage: Room Database
*   Build System: Gradle (Minimum v9.3.1 required)

## K. Current Next Task
*   Address the UI Technical Debt: Consolidate the duplicated button stylings into a unified `NoirButton` component and replace all hardcoded strings with XML string resources.

## L. DO NOT CHANGE
*   Do not overwrite existing working Room Database configurations or migrations.
*   Do not alter the `DPDPPrivacyMiddleware` logic inside the frontend `loginUser` function.
*   Do not introduce any new colors or spacing systems outside of `Theme.kt`.