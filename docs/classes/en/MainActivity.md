# Class Description: MainActivity

---

## 1. General Information
*   **Class Name:** `MainActivity`
*   **Type:** `Activity` (The main screen of the application)
*   **Purpose:** This class is the core of the **ColorFlip** application. It manages the user interface (UI), the logic of the game, and the interaction with the player. Its main responsibility is to display a 5x5 grid of squares and handle the logic where clicking a square flips the colors of its immediate neighbors.
*   **Interaction:** It interacts with the Android system to display the screen and uses standard Android UI components like `GridLayout`, `Button`, `Switch`, and `TextView`. It also uses a `CountDownTimer` for the "Blitz Mode".

---

## 2. Variables (Class Fields)

| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `BLITZ_MODE_DURATION` | `long` | Constant for the timer duration (30,000ms = 30 seconds). | `startTimer()` |
| `GRID_SIZE` | `int` | Defines the board size (5x5). | Used in grid generation and loops. |
| `grid` | `View[][]` | A 2D array that stores the visual "Square" objects. | `initializeGrid()`, `flipColor()` |
| `colors` | `int[]` | Stores the two possible colors (Red and Blue). | `initializeGrid()`, `flipColor()` |
| `colorState` | `int[][]` | A 2D array storing 0 or 1, representing the current color index of each square. | `onSquareClicked()`, `checkWinCondition()`, `flipColor()` |
| `stepsTextView` | `TextView` | Reference to the UI text showing the number of steps taken. | `onCreate()`, `updateStepsText()` |
| `stepsCount` | `int` | Counter for how many times the player has clicked a square. | `onSquareClicked()`, `initializeGrid()` |
| `blitzModeSwitch` | `Switch` | UI toggle to turn on/off the timed game mode. | `onCreate()`, `initializeGrid()` |
| `timerTextView` | `TextView` | Reference to the UI text showing the remaining time. | `onCreate()`, `startTimer()`, `initializeGrid()` |
| `countDownTimer` | `CountDownTimer`| The object responsible for the background countdown. | `startTimer()`, `stopTimer()` |
| `isBlitzModeActive` | `boolean` | Flag to track if the player is currently playing with a timer. | `blitzModeSwitch` listener |
| `isTimeUp` | `boolean` | Flag to prevent moves once the timer reaches zero. | `onSquareClicked()`, `onFinish()` |

---

## 3. Class Methods

### Method name: `onCreate`
*   **Type:** `protected`
*   **Return value:** `void` (None)
*   **Parameters:**
    *   `savedInstanceState` (`Bundle`): Contains saved data from a previous session (if any).
*   **What it does:** This is the entry point of the Activity. It connects the Java code to the XML layout (`activity_main`), initializes the UI components (Step counter, Switch, Timer text), and calls `initializeGrid` to start the game.
*   **When called:** When the application starts or the Activity is created.

### Method name: `initializeGrid`
*   **Type:** `private`
*   **Return value:** `void`
*   **Parameters:**
    *   `gridLayout` (`GridLayout`): The container where squares are placed.
*   **What it does:** Clears the board, resets the step counter, and stops any running timers. It then calculates the size for each square based on screen width and uses a loop to create 25 squares (`View` objects), assigns them a random color (Red or Blue), and adds a click listener to each.
*   **When called:** On startup and when the "Reset" button is clicked.

### Method name: `onSquareClicked`
*   **Type:** `private`
*   **Return value:** `void`
*   **Parameters:**
    *   `row` (`int`): Row index of the clicked square.
    *   `col` (`int`): Column index of the clicked square.
*   **What it does:** First, it checks if time is up. If not, it increments the steps. Then, it identifies the four neighbors (Top, Bottom, Left, Right). If a neighbor exists and its color is **different** from the clicked square, that neighbor's color is flipped. Finally, it checks if the player won.
*   **When called:** Every time the user taps a square on the grid.

### Method name: `flipColor`
*   **Type:** `private`
*   **Return value:** `void`
*   **Parameters:**
    *   `row` (`int`), `col` (`int`): Coordinates of the square to flip.
*   **What it does:** Changes the internal state (0 to 1 or 1 to 0) and updates the background color of the specific `View` in the grid.
*   **When called:** Inside `onSquareClicked` for the neighbor squares.

### Method name: `checkWinCondition`
*   **Type:** `private`
*   **Return value:** `void`
*   **What it does:** Loops through the entire `colorState` array. It compares every square to the first square. If even one square has a different color, the method ends. If all match, it stops the timer and shows a "You Win!" message.
*   **When called:** After every click.

---

## 4. Lifecycle (Activity)

*   **`onCreate()`**:
    *   **When called:** When the Activity is first launched.
    *   **What happens:** UI initialization and initial game setup.
*   **Note:** This class currently doesn't override other lifecycle methods like `onPause` or `onDestroy`. 
    *   **Suggestion:** It would be good practice to call `stopTimer()` in `onPause()` or `onStop()` to prevent the timer from running in the background if the user exits the app.

---

## 5. Interface Interaction (UI)

*   **Elements:** `TextView` (Steps and Timer), `GridLayout` (Game board), `Button` (Reset), `Switch` (Blitz Mode).
*   **Connection:** Uses `findViewById(R.id.id_name)` to link XML elements to Java variables.
*   **Events:** 
    *   `setOnClickListener` on the Reset button and every Square.
    *   `setOnCheckedChangeListener` on the Blitz Switch to start/stop the timer.

---

## 6. Interaction with other components
*   This application currently consists of a single Activity and does not use `Intents` to move to other screens or interact with databases/APIs. All logic is contained within `MainActivity`.

---

## 7. General logic of the class
1.  **Setup:** The grid is filled with random colors.
2.  **Play:** User clicks a square. The code finds the coordinates and changes colors of neighbors.
3.  **Monitor:** Every click updates the step count and checks if the whole grid is one color (Win).
4.  **Timer:** If Blitz Mode is on, a background thread counts down from 30.

---

## 8. Simplified explanation
**Explanation in simple words:**
Imagine a floor with 25 tiles. Each tile can be Red or Blue. However, these tiles are "magically" connected. When you step on a tile, you don't change its color, but you cast a "spell" on the tiles directly above, below, to the left, and to the right of you. If their color is different from yours, they flip! Your goal is to make the entire floor the same color. It's like trying to turn off all the lights in a house, but every time you flip a switch, the lights in the next room change too!

---
**Technical Note/Bug Warning:** 
In `onSquareClicked`, the logic flips a neighbor *only if* its color is different from the clicked square. This is a specific variant of the "Lights Out" game logic. If the goal is a standard "Lights Out" clone, neighbors should usually flip regardless of their current color.
