# 📱 Android Application Documentation: ColorFlip

---

## 🧾 General Information
**Project Name:**  
ColorFlip  
**Author(s):**  
Zeev Fraiman  
**Date:**  
July 2026  
**Language:**  
Java  
**Development Environment:**  
Android Studio  
**Android Version (minSdk / targetSdk):**  
28 / 36  

---

## 🎯 Project Goal
*   **What task does the app solve:** Provides an engaging logic puzzle game where the player must unify the grid color.
*   **Why is this task important:** Logic puzzles help in cognitive training and stress relief.
*   **Target audience:** Puzzle lovers of all ages looking for a quick mental challenge.

---

## 📌 Application Requirements
### Functional Requirements
*   5x5 grid of squares with two alternating colors.
*   Clicking a square flips the color of its neighbors (up, down, left, right).
*   Step counter to track performance.
*   Reset button to start a new game.
*   Blitz mode: A timed challenge (30 seconds).
*   Win detection: Game ends when all squares have the same color.

### Non-functional Requirements
*   **Performance:** Instant UI response to clicks.
*   **Usability:** Minimalist and intuitive interface.
*   **Reliability:** Stable timer behavior and state management during the session.

---

## 🧠 General Architecture
*   **Approach:**  
    – MVC (Model-View-Controller)
*   **Why this approach:**  
    The application is simple enough that a monolithic Activity structure provides clarity without over-engineering. The `MainActivity` serves as both the Controller and the View, while arrays like `colorState` act as the Model.
*   **Main system components:**
    – `MainActivity`: Orchestrates UI, game logic, and timer.
    – `GridLayout`: Dynamically renders the game board.

---

## 🧩 UML Diagram (High-level)
`[MainActivity]` –> `[GridLayout]`  
`[MainActivity]` –> `[CountDownTimer]`  
`[MainActivity]` –> `[colorState (Model)]`

---

## 🧩 Package structure
*   `zeev.fraiman.colorflip`: Contains the core logic.  
*   **Why:** Kept simple for a single-screen application to avoid unnecessary complexity.

---

## 🧩 Detailed Class Description
### 📌 Class: MainActivity
*   **Role:** Main entry point and game controller.
*   **Responsibility:** Initializing the grid, handling user clicks, managing the timer, and checking the win condition.
*   **Main methods:**
    – `onCreate()`: Sets up the initial UI.
    – `initializeGrid()`: Randomizes the board and resets counters.
    – `onSquareClicked()`: Implements the "flip" logic.
    – `checkWinCondition()`: Scans the grid to see if the player won.
    – `startTimer()`: Manages the Blitz mode countdown.
*   **Interaction with other classes:** Interacts with Android SDK components like `GridLayout`, `View`, and `CountDownTimer`.

---

## 🔄 App Workflow Diagram
1. User opens the app.
2. Grid is generated with random colors.
3. User clicks a square.
4. Neighbors of the clicked square (if they match the clicked color's logic) flip colors.
5. Steps count increases.
6. System checks if all squares are the same color.
7. If Yes -> "You Win!" toast.

---

## 🎨 UI/UX Analysis
*   **Why the interface is made this way:** Grid-based puzzles are most intuitive when the board fills most of the screen.
*   **Principles used:**
    – **Simplicity:** No complex menus; the game starts immediately.
    – **Logical Flow:** Controls (Reset, Blitz) are placed where they are easily reachable.
    – **Accessibility:** Large squares are easy to tap.
*   **Improvements:** Add animations for color flipping or sound effects.

---

## ⚙️ Threading
*   **Used:** `CountDownTimer`.
*   **Why:** It is a convenient high-level API provided by Android for UI-bound countdowns. It handles thread synchronization with the Main Thread automatically.
*   **Prevention:**
    – **ANR:** Game logic is lightweight and runs on the Main Thread without blocking.
    – **Memory Leaks:** The timer is stopped (`cancel()`) when no longer needed.

---

## 💾 Data Management
*   **Storage:** In-memory variables (`int[][] colorState`).
*   **Why:** Game state is transient and doesn't need to persist across app restarts for this version.
*   **Ensuring:**
    – **Safety:** Array bounds are strictly checked.
    – **Correctness:** State is reset on every "Initialize" call.

---

## 🌐 Network (N/A)
*   The app works entirely offline.

---

## 🔐 Security
*   No sensitive user data is collected or stored.

---

## 🧪 Testing
*   **Unit Tests:** Logic for color flipping can be tested in isolation.
*   **UI Tests:** Espresso can be used to simulate clicks and verify win conditions.

---

## 🐞 Error Handling
*   Input validation: User cannot click squares after the time is up in Blitz mode.
*   Exception prevention: Null checks for the timer.

---

## ⚡ Performance
*   **Optimizations:** Efficient grid rendering by reusing `LayoutParams`.
*   **Bottlenecks:** None identified for a 5x5 grid.

---

## 🚀 Scalability
*   **Developments:** Add different grid sizes (3x3, 7x7).
*   **New Features:** High scores, levels with pre-defined patterns, and themes.
