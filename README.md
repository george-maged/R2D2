# R2D2
A simple AI java project which utilizes a number of searching algorithms to help R2D2 escape some planet.

## Input

1. Minimum and maximum dimensions for the generated 2D grid.
2. Minimum and maximum number of obstacles inside the grid.
3. Minimum and maximum number of rocks and pads inside the grid.
4. The search strategy to utilize.  
  The options are: Breadth First, Depth First, Uniform Cost, Iterative Deepening, Greedy and A*.

## How he escapes

R2D2 has to _push_ each rock on a any pad. Once all pads are covered by rocks and R2D2 is on the teleportal, the algorithm is complete and a rundown of how R2D2 found the solution (if he did) is printed in the console.

## How to run it

Import the project in your favourite Java IDE and run Main.java
