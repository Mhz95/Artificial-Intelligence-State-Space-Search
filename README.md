# Artificial Intelligence State Space Search

## - Problem Statement

Consider the following problem: 4x4-Maze Traversal.
The problem is to move a robot from the start state to the goal state, one step at a time, avoiding
any obstacles (i.e., the squares which are highlighted with the dark color).

<img src="https://github.com/Mhz95/Artificial-Intelligence-State-Space-Search/blob/master/screenshots/problem.png" width="600">


Write a program which solves this problem using BFS, DFS, and A* (using Manhattan distance as a heuristic). Note that the operators (i.e., actions) to be used are go-left, go-right, go-up and godown. Each operator costs 1.

## - Algorithms

The following are the searches algorithms with their fringes as:
- **Depth First Search (DFS)** - Stack Iterative
- **Breadth First Search (BFS)** - Queue
- **A Star Search (A\*)** - Priority Queue

**Solution**

The solution is for N x M dimensional grid. Algorithms search for solution path. There are some input files at /input given that can be tested, and users can input their own maze file. 

To create the testcase file, the format is as:

```
N M
grid of NxM dimensions:   
0 for free cell, 1 for blocks
2 for starting cell
9 for ending cell
```
After choosing the search algorithm from the GUI, it will be shown in the maze frame as well as the logs area on the GUI.

## - Examples 

<img src="https://github.com/Mhz95/Artificial-Intelligence-State-Space-Search/blob/master/screenshots/q1_1.png" width="600">

<img src="https://github.com/Mhz95/Artificial-Intelligence-State-Space-Search/blob/master/screenshots/q1_2.png" width="600">

<img src="https://github.com/Mhz95/Artificial-Intelligence-State-Space-Search/blob/master/screenshots/q1_3.png" width="600">

<img src="https://github.com/Mhz95/Artificial-Intelligence-State-Space-Search/blob/master/screenshots/q1_4.png" width="600">

## - Animations

- **DFS vs BFS Animation**

![BFSvsDFS](http://i1.wp.com/blog.hackerearth.com/wp-content/uploads/2015/05/dfsbfs_animation_final.gif)

- **A* Animation**

![A*Animation](https://upload.wikimedia.org/wikipedia/commons/9/98/AstarExampleEn.gif)

## - References

[1] [Wikipedia - State Space Search](https://en.wikipedia.org/wiki/State_space_search)

> Developed as part of a Computer Science MSc course   
> Supervisor: Dr. Mohammad R. Alsulmi   
> Course: CSC562: Artificial Intelligence   
> King Saud university    
> February 2020
