# Optimization based on data

[ICA 1 (Flight Search Engine) README link](README_ICA_1.md)

## Intro

A current product helps clients of the company to find flight tickets that meet their
requirements.

This is a student team project aimed at demonstrating skills in teamwork and software development in the Clojure language.

## Disclaimer 

This product is a study task for assignment, the data is mock, the authors do not bear any responsibility to the users of this program.

## How it works

> This page does not delve into the details of how the algorithm itself works or the decisions behind the chosen architecture. For such in-depth details, please visit
the [technical report](TECHNICAL_REPORT_ICA_2.md) page.

The product is built on top of Clojure — a dynamic and functional dialect of the Lisp programming language that has JVM as the VM.

## CHANGES TO CLOJURE_ICA ORIGINAL CODE

> The Optimisation Based on Data source code is located at clojure_ica2. The original Flight Search Engine source code is located at clojure_ica.

+ [+] ./clojure_ica/API.clj 
is an alternative way to use the Flight Search Service that was developed as a part of the ICA1.

### Project Namespace Structure

```
.
+-- _src\clojure_ica2
```

```
.
+-- _Core
|   +-- Entities.clj -> the project defrecords
```

```
.
+-- _AnalysisService
|   +-- AnalysisService.clj
|   +-- CsvDataAdapter.clj
|   +-- sales_team_1.csv
```
AnalysisService. This service allows to extract meaningful data based on input parameters (in this case, sales_team_1.csv file), which are further used to predict prices for groups of passengers/families.
```
.
+-- Main.clj -> is the product Entry Point
+-- Store.clj -> a storage for the data that was generated useing Analysis Service.
```


### Features

1. An architecture designed for the versatility to incorporate new features and changes as required.
2. Code written to be clean and comprehensible, enabling the product's codebase to remain up-to-date.
3. Employing design patterns and breaking down the code into logical entities has enabled the project to be easily comprehensible for third-party developers.

### Example

#### Input

```clojure
(OptimizedFlightEngine/prepare_travel_plan "Prague" "Berlin" "g" [["PassengerName" "YearOfBirth"] ["PassengerName" "YearOfBirth"] ["PassengerName" "YearOfBirth"]])
```
#### Output
```
900
```

### References
- Robert C. Martin. A Craftsman's Guide to Software Structure and Design. Pearson; 1st edition (September 10, 2017)
- Robert C. Martin. Clean Code: A Handbook of Agile Software Craftsmanship. Pearson; 1st edition (August 1, 2008)
- ClojureDocs: community-powered documentation. Open-source (2023)

## Getting Started

### Installation
#### 1. Uses REPL mode
1. Clone the repository into your Clojure project.
2. Run REPL
3. Enter the product's namespace

```clojure
; Example
> (use 'clojure_ica2.Main)
> (in-ns 'clojure_ica2.Main)
```
3. Call the function in a terminal with arguments (departure city, arrival city, type and vector of vectors that includes passegners data)

```clojure
; Example
> (prepare_travel_plan "Prague" "Berlin" "g" [["PassengerName" "YearOfBirth"] ["PassengerName" "YearOfBirth"] ["PassengerName" "YearOfBirth"]])
```
#### 2. Uses as a library
1. Clone the repository into your Clojure project.
2. Import the product entry point namespace
```clojure
; Example
(ns [your-namespace]
    (:require [clojure_ica2.Main :as OptimizedFlightEngine])
  )
```
3. Call the function in your code with arguments (departure city, arrival city and type)
```clojure
; Example
(OptimizedFlightEngine/prepare_travel_plan "Prague" "Berlin" "g" [["PassengerName" "YearOfBirth"] ["PassengerName" "YearOfBirth"] ["PassengerName" "YearOfBirth"]])
```
## Credits

- [Roman Y](https://github.com/iooe)
- [Kirill Marchenko]()
- [Vladyslav Zolotarevskyi](https://github.com/TokioBoy)

