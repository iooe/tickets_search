# Flight Search Engine

## Intro

The program helps clients of the company to find flight tickets that meet their
requirements.

This is a student team project aimed at demonstrating skills in teamwork and software development in the Clojure language.

## Disclaimer 

This programme is a study project, the data is mock, the authors do not bear any responsibility to the users of this program.

## How it works

The program is built on top of Clojure — a dynamic and functional dialect of the Lisp programming language that has JVM as the VM.

### Algorithms

The program functionality is based on an optimized depth-first search with subsequent filtering of results based on input parameters. That is, the result is always the best result.

### Project Structure

```
.
+-- _Core
|   +-- Core.clj
|   +-- Entities.clj
```
The core of the program, all calculations are made in this part of the code base. The Entities file is a set of structures that are used to transfer data between the adapter and the core.

```
.
+-- _Adapters
|   +-- CsvDataAdapter
```
Adapters. These scripts enable the user to import data from external sources into a format that can be understood by the program core.

```
.
+-- _Datasets
|   +-- flights-ICA1.csv
```
This is where the datasets are located.

```
.
+-- Main.clj
```
The program Entry Point.


