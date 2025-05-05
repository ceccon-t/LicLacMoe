# LicLacMoe - Architecture

This file describes the overall architecture of the project, including its most important design decisions, code structure and frameworks used.


## Design decisions

An important early decision (or "axiom") for this project was to try to avoid as much as possible frameworks that offered an API that is too abstract. Instead, the idea was to focus on building by hand most of the relevant logic, as the project itself was an exercise in getting familiar with interacting with LLMs.

Another axiom is to work only with local models. While frontier models would likely generate better results, the purpose of the project is learning and exploring what can be achieved with models that can be owned by the users.


## Structure

### Project meta files

The main folder of the project is named `LicLacMoe`, and it is present at the root of the repository. Also at the root, `README.md` gives a short presentation at a project level, while this `architecture.md` file should quickly put any developer up to speed as to how the code is laid out. The `images` folder at root level contains images used only in the presentation of the repository, not to be used inside the application.

### Code overview

The main class of the application is at the root package, `LicLacMoe`.

The packages can be broadly divided in 3 areas: the game logic, the UI views and integration with the local LLM server.

The game logic is all in one package: `tictactoe`. Likewise, all the UI views are under `gui` package. Finally, three packages handle the integration with the LLM server: `client`, `config` and `conversation`.

#### Package `tictactoe`

This is the main package, as it contains all the game logic. The main class is `Game`, which models all the data needed for a match, using helper classes such as `Cell` and the enums `GameStatus` and `Player`. There are also a few interfaces for the Observer design pattern, mostly used to manage UI changes due to events happening in the game. Class `LLMPlayer` encapsulates the adversary, and uses integration classes from other packages to send requests to the LLM server to get the next move - this class might be expanded (perhaps becoming abstract) in the future to allow for different strategies and/or prompting techniques.

#### Package `gui`

UI elements for the application, using Swing framework. There is nothing particularly complex in the UI code, most worthy of mention is that the board itself is represented as a 2-dimensional array of JButtons, and that the Observer pattern is used whenever something must be updated in the UI due to some change in the game.

#### Package `conversation`

Simple classes to model the interaction with the LLM. It uses a chat format with messages, as the API used is the chat completion one, despite no actual conversation being maintained.

#### Package `config`

Contains a single class to hold the configuration of the LLM server integration - things such as URL, port, model, temperature, etc.

#### Package `client`

Contains classes that handle the actual request and response communication with the LLM server, including DTOs, connection information, etc.


### Prompts

For those interested in checking the prompt that is being used to try and steer the LLM model responses towards the desired results, it can be found on the `LLMPlayer` class, method `getNextMove`. It consists of a system prompt, describing the purpose of the application and how the model should answer, followed by a user prompt that simply sends the current board state in a string representation.

It is possible that improvements to the performance of any model in the game could be achieved simply by coming up with a more complex prompt and a better representation for the state of the game.


## CI/CD

The project uses Github Actions to automatically generate a new release whenever new changes that alter the application itself are pushed into the `main` branch.

If automated tests fail or the build breaks, a red failure sign is displayed near the hash of the commit in the repository. If all goes well, a green success sign is displayed instead. A badge with the status of the latest build for the main branch is also displayed in the Readme of the project.

The script that defines the main workflow can be found under `.github/workflows/main-workflow.yml`.


## Libraries and Frameworks

[JCommander](https://jcommander.org/) for parsing command line options.

[Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) as a build automation tool.

[JUnit](https://junit.org/junit5/docs/current/user-guide/) and [Mockito](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html) for automated tests.

[Swing](https://docs.oracle.com/javase/tutorial/uiswing/) as the GUI framework.

[Github Actions](https://docs.github.com/en/actions/learn-github-actions) for continuous integration and continuous delivery.

