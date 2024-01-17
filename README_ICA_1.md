# Flight Search Engine

## Intro

A current product helps clients of the company to find flight tickets that meet their
requirements.

This is a student team project aimed at demonstrating skills in teamwork and software development in the Clojure language.

## Disclaimer 

This product is a study task for assignment, the data is mock, the authors do not bear any responsibility to the users of this program.

## How it works

> This page does not delve into the details of how the algorithm itself works or the decisions behind the chosen architecture. For such in-depth details, please visit
the [technical report](TECHNICAL_REPORT_ICA_1.md) page.

The product is built on top of Clojure — a dynamic and functional dialect of the Lisp programming language that has JVM as the VM.

### Algorithms

The product's algorithm functionality is based on an optimized depth-first search with subsequent filtering of results based on input parameters. That is, the result is always the best result.

### Project Namespace Structure

```
.
+-- _src\clojure_ica
```

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
> (use 'clojure_ica.Main)
> (in-ns 'clojure_ica.Main)
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
    (:require [clojure_ica.Main :as FlightEngine])
  )
```
3. Call the function in your code with arguments (departure city, arrival city and type)
```clojure
; Example
(FlightEngine/prepare-travel-plan "Prague" "Berlin" "g")
```
## Credits

- [Roman Yepanchenko](https://github.com/iooe)
- [Kirill Marchenko]()
- [Vladyslav Zolotarevskyi](https://github.com/TokioBoy)

