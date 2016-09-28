# README for Trace Generator Project

## How to compile the code
```
javac Main.java
```

## How to run the code
```
java Main <N> <Q> <P> <B> <workerType> <task> <lock>
```
* N = The number of tasks to compute.
* Q = The number of workpools to use.
* P = The number of workers (Threads).
* B = The number of tasks to spawn on average.
* workerType:
  * 0 = Selfish
  * 1 = Friendly
  * 2 = Crazy
* task:
  * 0 = Fibonacci
  * 1 = Prime
  * 2 = Sleep
  * 3 = Loops
  * 4 = Mixed
* lock:
  * 0 = single
  * 1 = double

### WorkerType
#### Selfish
This worker only completes his workpool's work and then waits for the others to complete.

#### Friendly
This worker completes his workpool's work and then tries to help out other workers by completing tasks from other pools.

#### Crazy
This worker is crazy, since he selects a random workpool every time he completes a task.

### Tasks
#### Fibonacci
This task computes the n<sup>th</sup> Fibonacci number.

#### Prime
This task computes the n<sup>th</sup> Prime number.

#### Sleep
This task merely executes the statement
```
Thread.sleep(n)
```
This merely lets the worker wait for `n` milliseconds.

#### Loops
This task simply loops `n` times.

#### Mixed
The tasks are randomly determined. This means that each task has a probability of 0.25 to be added.

### Locks
#### Single
This is to tell the workpool to use only a single lock for both adding and removing items.

#### Double
This tells the workpool to use a lock for enqueuing an item and another for dequeueing an item.

## Output
The output is in the form of a log file. The log file is named `log.txt`. The first few lines contain the configuration settings for the produced log file.

The lines after this is in the form:

| Timestamp | Thread ID | Lock Address | Event Name |
| --------- | --------- | ------------ | ---------- |
| 364       | 1         | 60fd60       | trylock    |
