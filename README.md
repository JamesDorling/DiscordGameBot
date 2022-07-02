# Discord Game Bot
#### By James D

This is a bot for discord to play games. 
I am making this entirely as a challenge to myself, as I believe it will be fun.

## Contents
* [How to use](#how-to-use)
* [Games currently implemented](#games-currently-implemented)
* [Currently being worked on](#currently-being-worked-on)

## How To Use
To use this bot, firstly create a bot on the discord website, with these permissions:
* Read/Write messages
* View channels
* Use slash commands

Then, get your bot token. This will be important in a second.
Next, clone this repo and run it from the IDE of your choosing. (I have not made it into a .jar file yet.)

Running this project will generate a config.properties file at the filepath of "src/main/resources/config.properties"
and all you need to do is put your bot's token in there and re-run the project to make your bot connect and be able
to play the implemented games.

## Games Currently Implemented
### Battleships
Battleships is the first game implemented. This can be played with the following commands:
* /challenge [user]
* /setup
* /shoot [coords]

There is also another method called /setuptest, used to test the system.


## Currently Being Worked On
I am currently working on a connect four game to be added to this system.