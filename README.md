# LicLacMoe

![Build status](https://github.com/ceccon-t/LicLacMoe/actions/workflows/main-workflow.yml/badge.svg "Build status")


## Description

LicLacMoe is a desktop tic-tac-toe game in which you play against a local LLM.

![Start of a match](https://raw.githubusercontent.com/ceccon-t/LicLacMoe/main/images/LicLacMoe_v1-0-0_sc0.png "Start of a match")


## How to use

This is a classic 3x3 tic-tac-toe game. Click on a cell to make a move, then wait until the AI player makes its move. Human will always play as X and go first, while AI will always play as O.

At any point, click the Reset button to start over.

The point of this project is to have fun interacting with an LLM model without any conversational interface at all. Switching models (check section "Command-line options" to see how to use different models) and evaluating each one's performance in the game makes for an unusual manner of thinking about these models.

Since the project is open source, you can always check the implementation for the specific prompt being used, and speculate (or test!, if you know how to write software) how different prompts might lead to better or worse performance for any particular model.

![End of a match](https://raw.githubusercontent.com/ceccon-t/LicLacMoe/main/images/LicLacMoe_v1-0-0_sc1.png "End of a match")


## Dependencies

The application assumes you have an LLM API listening on port 8080 of your computer (port number is configurable, check section below). If you have experience configuring and running them, any with an OpenAI-compatible API should work nicely - if you do not have experience, the easiest way to get one running is to use [llamafile](https://github.com/mozilla-Ocho/llamafile), which is both easy to use and has very good documentation on how to set it up.

Don't like the results you have been getting? Just try another model! Llamafile's documentation explains how to use a single executable to run different models, the same should be true for any other tool you might choose.


## How to run

The application is written in Java, so you will need to have the Java runtime installed. Assuming it has already been installed, either download the Jar file from the latest entry in the [Releases](https://github.com/ceccon-t/LicLacMoe/releases) section of this repository or build the project following the instructions below, and execute it.

Example with llm server running on default port (8080):

`$ java -jar LicLacMoe.jar`

Example using a custom port (8888, in this case):

`$ java -jar LicLacMoe.jar -p 8888`

### Run with ollama

If you are using ollama for the LLM server, you will have to at a minimum pass the name of the model you want to chat with when starting the application by using the `-m <model_name>` option. You will probably also want to use ollama's default port, 11434. Here is an example of how to chat with llama3.1 using ollama (check ollama's documentation for other model options):

`$ java -jar LLP.jar -p 11434 -m llama3.1`

Keep in mind that ollama will use an incredibly small context size, regardless of the model you are using. In order to have a larger context size, you will need to create a custom version of the model, specifying the size to be used - this might be a somewhat more advanced configuration that you should try only after you feel comfortable with ollama. Please refer to its documentation for steps on how to set the context size for the model when using the OpenAI-like API (which LicLacMoe does).


## Command-line options

Here is the list of command-line options available when starting the application:

- `-p <port_number>`: Set the port where to reach the LLM server, <port_number> must be an integer indicating a port in your machine with a LLM server listening. Defaults to 8080.

- `-m <model_name>`: Specify the name of the LLM model to be used, <model_name> must be a string. Necessary when using ollama as backend for text generation, has no effect when using llamafile.

- `-t <temperature>`: Specify the temperature to be used when generating text, the larger the temperature the more randomness it includes. <temperature> must be a decimal number, and usually fits in the [0.0-1.0) range. Make sure to use a dot (`.`) and not a comma to separate the parts of the number. Defaults to 0.9.

- `-v <true|false>`: Verbose mode, specify if should log to console the raw answers received from the LLM.

These options are independent of each other, and can be combined as desired and in any order. Examples of using some of them can be found in section "How to run" of readme.


## How to build the project

This is a simple Maven project, so the easiest way to build it is running `mvn clean package` in the LicLacMoe folder (assuming Maven is installed - if not, check its site and install from there). A jar file containing everything the application needs to run will be created at `LicLacMoe/target/LicLacMoe-<VERSION>.jar`.

