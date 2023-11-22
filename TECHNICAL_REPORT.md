# TECHNICAL REPORT

# Intro

In prototyping the product architecture, the team focused on tips and ideas from the classic book "A Craftsman's Guide to Software Structure and Design" by Robert C. Martin

## Project architecture

The architecture of the product is based on a simple idea - the whole product logic should be divided into separate parts, which interact with each other by means of interfaces known for all parts of the program.

As interfaces, we mean some entities through which data is transferred. In the case of the Clojure programming language, such entities are the defrecord construct, which allows you to create a data structure.

As interfaces, there are entities through which data is transmitted. In the case of the Clojure programming language, such entities are the defrecord construct, which allows you to create a data structure.

The team decided at the prototyping stage of the product that it would be logical to divide the product into four parts: Core, Data Adapters, Entities/Data Structures and Entry Point/Main.

1. The core of the product is the main business logic, this is where the program results pool is searched and formed.
2. Adapters - this is where the data adapters lie, which allow you to take data from one format and transform it into a format understandable to the core. At the moment only one type of adapters from csv format is created, because of the unnecessity of the other one. But thanks to this solution, the program becomes flexible and theoretical problems will not arise in the future.
3. Entities is a file that contains structures that the adapter transforms the data into, and with which the kernel further works. This is necessary to reduce the probability of error and make the code more predictable.
4. Entry point, aka Main.clj - this is the file where the program is initialized. It is here that the kernel, adapters, function calls and the output to the console are imported.

# Algorithms and Ideas

The initial implementation of the kernel searched only for the single best result, after which the search stopped. This was not suitable for the task conditions, as the product should not only search for the best result, but also search with constraints on price and route length, and the result, referring to the task conditions, should be the most expensive of the possible ones, but corresponding to the constraints.

The team's research led to the fact that the participants wrote their own implementations of the path search, after which they were implemented together as a new core, based on the architecture of the old one - since the solutions of the team members separately had their own problems and shortcomings, but they could be eliminated by combining the ideas of the individual team members and creating on their basis the final, at the moment, implementation of the core.

In particular, the new kernel is based on the ideas of not only searching in depth, but also the idea of backtracking on the basis of the found final route. This concept surprisingly allows combining not only finding all possible route variants, but also optimizing for time and work, as well as conciseness of this solution.

# Choices and evaluation of solutions

Other potential methods for implementing the core were considered, yet they were ultimately deemed less promising.

This may be due to their complexity or brevity of implementation, or because they failed to provide objective advantages over the solution that was ultimately implemented as the main kernel.

Possible alternative ideas included:

1. Creating numerous hash map structures based on the input graph and searching through them in the future.
   It's a fascinating concept, but it requires a considerable amount of unnecessary work in computation and debugging complexity.
2. The idea of Modified Breadth-First Search was dismissed because it did not offer any advantages over the Modified Depth-First Search, as per the provided dataset. Searching for tickets based on connections between cities can be a challenging endeavour, with final routes often being highly intricate.
3. Breadth-first search appears ill-suited to this task due to its reliance on neighbouring nodes. Additionally, the use of the A* path search algorithm was deemed impractical due to its specific graph requirements. Adapting graphs to fit this algorithm via adapters would require extra time, but the resulting differences compared to the chosen modified depth-first path search would be minimal.


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
