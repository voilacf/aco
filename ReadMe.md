#Ant Colony Optimisation Algorithm

A command-line application in Java solving the
"Garbage Collection by Ants"

---

##Run Project

- set configurations in config.json (intervals: size [10;50], density [0.05;0.75], ants [10;50])
- run Main class within the main directory
- see initialised playground: playground-default.txt
- see playground after algorithm has finished: playground.txt
- see how often each piece of garbage has been moved: garbageMovement.txt

---

###Base
In a tableau (here a playground) with randomly distributed objects,
an ant moves 10.000 times based on the rules
-> Test Management checks correct behaviour of the ant

###Configuration and Logging

- The tableau can vary in size (min 10, max 50), density (min: 0.05, max: 0.75) and defined in a JSON file
- If the configuration does not apply to the specification, default settings will be used instead
- After 10,000 iterations, the current tableau is recorded in a log file
- The count of stacked objects is presented
  -> Test Management verifies the correct configuration and logging

###Multithreading | Concurrency

- Count of concurrent-operated ants is defined in the configuration file (min: 10, max: 50)

###Statistics
A log file containing statistics documenting how often a piece with a unique
ID has changed place is implemented, too.

---

As part of a university project
