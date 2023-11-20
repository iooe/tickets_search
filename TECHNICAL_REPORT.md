# TECHNICAL REPORT

# Intro

In prototyping the project architecture, the team focused on tips and ideas from the classic book "A Craftsman's Guide to Software Structure and Design" by Robert C. Martin

## Project architecture

The architecture of the project is based on a simple idea - the whole project logic should be divided into separate parts, which interact with each other by means of interfaces known for all parts of the program.

As interfaces we mean some entities through which data is transferred. In the case of the Clojure programming language, such entities are the defrecord construct, which allows you to create a data structure.


As interfaces, there are entities through which data is transmitted. In the case of the Clojure programming language, such entities are the defrecord construct, which allows you to create a data structure.

The team decided at the prototyping stage of the project that it would be logical to divide the project into four parts: Core, Data Adapters, Entities/Data Structures and Entry Point/Main.

1. The core of the project is the main business logic, this is where the program results pool is searched and formed.
2. Adapters - this is where the data adapters lie, which allow you to take data from one format and transform it into a format understandable to the core. At the moment only one type of adapters from csv format is created, because of the unnecessity of the other one. But thanks to this solution, the program becomes flexible and theoretical problems will not arise in the future.
3. Entities is a file that contains structures that the adapter transforms the data into, and with which the kernel further works. This is necessary to reduce the probability of error and make the code more predictable.
4. Entry point, aka Main.clj - this is the file where the program is initialized. It is here that the kernel, adapters, function calls and the output to the console are imported.
