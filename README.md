# Flight Search Engine

## Intro

A current product helps clients of the company to find flight tickets that meet their
requirements.

This is a student team project aimed at demonstrating skills in teamwork and software development in the Clojure language.

## Disclaimer 

This product is a study task for assignment, the data is mock, the authors do not bear any responsibility to the users of this program.

## How it works

> This page does not delve into the details of how the algorithm itself works or the decisions behind the chosen architecture. For such in-depth details, please visit
the [technical report](TECHNICAL_REPORT.md) page.

The product is built on top of Clojure — a dynamic and functional dialect of the Lisp programming language that has JVM as the VM.

### Algorithms

The product's algorithm functionality is based on an optimized depth-first search with subsequent filtering of results based on input parameters. That is, the result is always the best result.

### Project Structure

```
.
+-- _Core
|   +-- Core.clj
|   +-- Entities.clj
```
The core of the product, all calculations are made in this part of the code base. The Entities file is a set of structures that are used to transfer data between the adapter and the core.

```
.
+-- _Adapters
|   +-- CsvDataAdapter
```
Adapters. These scripts enable the user to import data from external sources into a format that can be understood by the product core.

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
The product Entry Point.

### Features

1. An architecture designed for the versatility to incorporate new features and changes as required.
2. Code written to be clean and comprehensible, enabling the product's codebase to remain up-to-date.
3. Employing design patterns and breaking down the code into logical entities has enabled the project to be easily comprehensible for third-party developers.
4. Additionally, the inclusion of a Debug mode permits the straightforward tracing of the entire programme execution path.

### Example

#### Input

```clojure
(FlightEngine/prepare-travel-plan "Prague" "Berlin" "g")
```
#### Output
```
Route with prices for segments: PRAGUE (300)> BUDAPEST (200)> ZAGREB (400)> MUNICH (100)> BERLIN
Total price: 1000
Flights: 4
```

### References
- Robert C. Martin. A Craftsman's Guide to Software Structure and Design. Pearson; 1st edition (September 10, 2017)
- Robert C. Martin. Clean Code: A Handbook of Agile Software Craftsmanship. Pearson; 1st edition (August 1, 2008)
- ClojureDocs: community-powered documentation. Open-source (2023)

## Getting Started

### Debug mode

To activate it, set the "isDebug" flag to "true" before calling the main function "prepare-travel-plan". By default, this flag is set on line 112 of the "Main.clj" file

### Installation
#### 1. Uses REPL mode
1. Clone the repository into your Clojure project.
2. Run REPL
3. Enter the product's namespace

```clojure
; Example
> (use 'PCU_SC_ICA_1_2023.Main)
> (in-ns 'PCU_SC_ICA_1_2023.Main)
```
3. Call the function in a terminal with arguments (departure city, arrival city and type)

```clojure
; Example
> (prepare-travel-plan "Prague" "Berlin" "g")
```
#### 2. Uses as a library
1. Clone the repository into your Clojure project.
2. Import the product entry point namespace
```clojure
; Example
(ns [your-namespace]
    (:require [PCU_SC_ICA_1_2023.Main :as FlightEngine])
  )
```
3. Call the function in your code with arguments (departure city, arrival city and type)
```clojure
; Example
(FlightEngine/prepare-travel-plan "Prague" "Berlin" "g")
```
## Teamwork

There were several key time points throughout the product development.

First: the prototyping and architecture phase.
Second: Implementation of the core search algorithm.
Third: Fixing cases, bugs, and formatting of the results output.

The contribution of human involvement varied from stage to stage. Here is a cursory overview, for a more detailed look at the contributions of contributors to the development please explore the history of issues, commits and requests.

Contributions of contributors:

### Roman

Roman wrote the basic product architecture and the first draft of the search algorithm for the core product. Roman also took on the role of code reviewer and the person who merged codebases from other contributors.

All team members were equally engaged in fixing bugs and borderline cases.

Example commits:

291de9fe3728121e5880505499a2ae8ef3318468
60972eaa58e1495c4b1ea693949a5f7f21fac884
2605c22339c734b3ba24c9686ba4dd02915dd206

### Vladyslav

The first implementation of the search algorithm for the core was accepted by the team as unsuitable, after which Vladyslav and Kirill implemented their ideas.

Using the work of both contributors, a new core was written.

All team members were equally engaged in fixing bugs and borderline cases.

Example commits:

212224b066675605f9db00c6b2f0a4ac44188d82
7af35e123a05218a849e77ae7d969d5dd747ebbd
7f2d643cf81c5fd6bd1dac02a6c4dab835857a80

### Kirill

The first implementation of the search algorithm for the core was accepted by the team as unsuitable, after which Vladyslav and Kirill implemented their ideas.
Using the work of both contributors, a new core was written.

All team members were equally engaged in fixing bugs and borderline cases.

Example commits:

7a7bc550f1f57b4374dd7f550646399ae4499677
5b144c64c418931dab558b6750179f21f4c84c6c
e1e4e648e2ed783ac2efcf163b485e9cdf25debd

## Credits

- [Roman Yepanchenko](https://github.com/iooe)
- [Kirill Marchenko]()
- [Vladyslav Zolotarevskyi](https://github.com/TokioBoy)

