# HUNT THE WUMPUS
![Hunt the Wumpus.PNG](HuntTheWumpusGame/src/static/Hunt%20the%20Wumpus.PNG)


"Hunt the Wumpus" is a classic text-based adventure game where the player navigates a cave system to hunt down the dangerous Wumpus. The cave is structured as a dodecahedron (a 3D shape with 12 faces). The player must avoid pits and the Wumpus while using arrows to hunt the Wumpus. The game uses a turn-based mechanic where the player must make decisions based on limited information about the cave system, testing their strategy and resource management.

## Overview
"Hunt the Wumpus" is a console-based game implemented using **Java Swing** with an **MVC** architecture. Players navigate a cave system, avoiding hazards like pits and the deadly Wumpus while trying to kill the Wumpus using arrows.

## Project Structure

- **Model**: Represents the game state and logic.
- **View**: Displays the game interface.
- **Controller**: Handles user input and coordinates the game flow.

### Model
- **`WumpusGameModel`**: Manages game state, moves the player, and updates the game world.
- **`Cave`**: Represents individual caves in the dodecahedron.
- **`WumpusGameModelHard`**: An extension that introduces harder gameplay mechanics.

### View
- **`WumpusGameView`**: Main game screen that displays the current state of the game.
- **`WumpusGameEndView`**: Game over screen.
- **`WumpusGameStartView`**: Start screen with game controls.
- **`WumpusViewInterface`**: Interface for the view components.

### Controller
- **`WumpusGameController`**: The game controller that processes inputs, updates the model, and refreshes the views accordingly.

## Features

- Start screen for user interaction and game settings.
- Movement and shooting mechanics using arrow keys.
- Dynamic game over handling with validation of player and Wumpus status.
- Cheats mode to reveal game elements.

## Testing

### Unit Tests
The project contains unit tests to ensure the game mechanics work as expected. The main areas covered by tests are:
- **Game state validation**: Ensures the game ends when the player encounters a pit or the Wumpus.
- **Movement and shooting**: Validates player moves and arrow shots.
- **Game model logic**: Ensures that the Wumpus can be killed and arrows are counted correctly.

