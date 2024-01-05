# Live Long and Prosper Search Agent

This Java project implements a search agent tasked with planning a sequence of actions to help a town reach a prosperity level of 100, considering resource management and budget constraints. The agent employs various search strategies to find an optimal solution while minimizing the monetary cost.

## Project Overview

- **Resource Management:** Food, materials, and energy are crucial resources, with a limit of 50 units per resource and a depletion factor with each action.
- **Actions:** The agent can request deliveries, wait for them, or perform builds that affect prosperity levels, each with its resource consumption and associated cost.
- **Search Strategies:** Implemented search strategies include breadth-first, depth-first, iterative deepening, uniform cost, greedy, and A* with multiple heuristics to find the best solution.

## Implementation Details

### Classes

- **GenericSearch:** Contains a generic implementation of a search problem.
- **LLAPSearch:** Extends GenericSearch, implementing the "Live Long and Prosper" search problem.
- **Node:** Implements a search-tree node.
- **State:** Implements the state of the current Node.

### Functionality

The `solve` function in `LLAPSearch` is the key function used for testing. It takes in an initial state, a chosen strategy, and a boolean to visualize steps if desired.

- **Input Format:** The initial state is provided as a formatted string containing resource levels, prices, delivery parameters, build costs, and effects.
- **Strategy:** Choose from available search strategies using specific strings (e.g., BF, DF, ID, UC, GRi, ASi).
- **Visualization:** Optional parameter to display state information during solution steps.

## Example Usage

```java
String initialState = "17;" +
                "49,30,46;" +
                "7,57,6;" +
                "7,1;20,2;29,2;" +
                "350,10,9,8,28;" +
                "408,8,12,13,34;";
String strategy = "BF";
boolean visualize = true;
String solution = System.out.println(LLAPSearch.solve(initialState,strategy,visualize));
System.out.println(solution);
```


# Solution Output

The search process resulted in finding a sequence of actions that lead to the town achieving a prosperity level of 100, with a monetary cost of 18871. Here's a breakdown of the solution when using Breadth First Search :

- **Number of Explored Nodes:** 502

### Solution Path
- Depth: 7 - Action: BUILD1
   State: {food=6, materials=2, energy=0, prosperity=100, monetary_cost=18871}

- Depth: 6 - Action: REQUEST_MATERIALS
   State: {food=17, materials=1, energy=10, prosperity=79, monetary_cost=11746}

- Depth: 5 - Action: BUILD2
   State: {food=18, materials=2, energy=11, prosperity=79, monetary_cost=11386}

- Depth: 4 - Action: REQUEST_ENERGY
   State: {food=25, materials=10, energy=6, prosperity=64, monetary_cost=8205}

- Depth: 3 - Action: WAIT
   State: {food=26, materials=11, energy=7, prosperity=64, monetary_cost=7845}

- Depth: 2 - Action: BUILD1
   State: {food=18, materials=12, energy=8, prosperity=64, monetary_cost=7485}

- Depth: 1 - Action: REQUEST_FOOD
   State: {food=29, materials=24, energy=18, prosperity=30, monetary_cost=360}

- Depth: 0 - Initial State
   State: {food=30, materials=25, energy=19, prosperity=30, monetary_cost=0}

### Final Solution 
- Actions: RequestFood, BUILD1, WAIT, RequestEnergy, BUILD2, RequestMaterials, BUILD1
- Monetary Cost: 18871 
- Nodes Expanded: 502

