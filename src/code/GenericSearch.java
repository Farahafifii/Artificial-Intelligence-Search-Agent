package code;

import java.util.*;

public abstract class GenericSearch {
    public static Node currNode; //initial State
    public static List<Node> childNodes; //State Space
    public static int unitPriceFood, unitPriceMaterials, unitPriceEnergy , amountRequestFood, delayRequestFood , amountRequestMaterials, delayRequestMaterials, amountRequestEnergy, delayRequestEnergy;
    public static int priceBUILD1, foodUseBUILD1,  materialsUseBUILD1, energyUseBUILD1, prosperityBUILD1;
    public static int priceBUILD2, foodUseBUILD2,materialsUseBUILD2, energyUseBUILD2, prosperityBUILD2, food,prosperity,materials,energy;
    public static int minBuild ;
    public static int maxProperityBuild ;
    public static int minFood ;

    public static void assignInitialVar(String[][] state){
        prosperity = Integer.parseInt(state[0][0]) ;
        food = Integer.parseInt(state[1][0]);
        materials=Integer.parseInt(state[1][1]);
        energy =Integer.parseInt(state[1][2]);
        unitPriceFood = Integer.parseInt(state[2][0]);
        unitPriceMaterials = Integer.parseInt(state[2][1]);
        unitPriceEnergy= Integer.parseInt(state[2][2]);
        amountRequestFood = Integer.parseInt(state[3][0]);
        delayRequestFood = Integer.parseInt(state[3][1]);
        amountRequestMaterials = Integer.parseInt(state[4][0]);
        delayRequestMaterials = Integer.parseInt(state[4][1]);
        amountRequestEnergy = Integer.parseInt(state[5][0]);
        delayRequestEnergy=Integer.parseInt(state[5][1]);
        priceBUILD1 = Integer.parseInt(state[6][0]);
        foodUseBUILD1 = Integer.parseInt(state[6][1]);
        materialsUseBUILD1 = Integer.parseInt(state[6][2]);
        energyUseBUILD1 = Integer.parseInt(state[6][3]);
        prosperityBUILD1 = Integer.parseInt(state[6][4]);
        priceBUILD2 = Integer.parseInt(state[7][0]);
        foodUseBUILD2 = Integer.parseInt(state[7][1]);
        materialsUseBUILD2= Integer.parseInt(state[7][2]);
        energyUseBUILD2 = Integer.parseInt(state[7][3]);
        prosperityBUILD2 = Integer.parseInt(state[7][4]);
        minBuild=priceBUILD1>priceBUILD2? priceBUILD2:priceBUILD1;
        maxProperityBuild=prosperityBUILD1>prosperityBUILD2?prosperityBUILD1:prosperityBUILD2;
        minFood=foodUseBUILD1>foodUseBUILD2?foodUseBUILD2:foodUseBUILD1;
    }
    public static Object[] bfs(boolean visualize) {
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(node -> node.depth));
        frontier.add(currNode);
        Set<Node> explored = new HashSet<>();
        while (!frontier.isEmpty()) {
            currNode= frontier.poll();
            if (currNode.state.prosperity == 100) {
                System.out.println("Number of Explored Nodes: "+ explored.size());
                Object[] result =new Object[]{currNode,explored.size()};
                return result;
            }
            if (!explored.contains(currNode)) {
                explored.add(currNode);
                if (currNode.state.food == 0 || currNode.state.materials == 0 || currNode.state.energy == 0 || currNode.state.monetary_cost > 100000) {
                    continue;
                }
                childNodes = new ArrayList<>();
                childNodes = currNode.generateChildNodes();
                if(visualize){
                    visualize(currNode,null);

//                    System.out.println("Current: " + currNode.state + " " + currNode.action + " " + currNode.depth +" " + (currNode.parentNode==null? "":
//                    " Parent: " + currNode.parentNode.state + " "
//                            + currNode.parentNode.action + " " + currNode.parentNode.depth)
//                    +(currNode.parentNode==null? "": currNode.parentNode.parentNode==null?"":
//                    " GParent: " + currNode.parentNode.parentNode.action + " " + currNode.parentNode.parentNode.depth));
                }
                for (Node childNode : childNodes) {
                    if(visualize)
                        visualize(null,childNode);
//                        System.out.println("CHILDREN: " + childNode.state + " " + childNode.action + " " + childNode.depth );
                    if (childNode != null && !explored.contains(childNode)) {
                        frontier.add(childNode);
                    }
                }
                childNodes = null;
            }
        }
        Object[] result =new Object[]{null,explored.size()};
        return result;
    }


    public static Object[] dfs(boolean visualize ) {
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(node -> -node.depth));
        frontier.add(currNode);
        Set<Node> explored = new HashSet<>();

        while (!frontier.isEmpty()) {
            Node node = frontier.poll();
            currNode = node;
            State state = currNode.state;
            if (state.prosperity == 100) {
                Object[] result = new Object[]{node, explored.size()};
                return result;
            }
            if (!explored.contains(node)) {
                explored.add(node);
                if (currNode.state.food == 0 || currNode.state.materials == 0 || currNode.state.energy == 0 || currNode.state.monetary_cost > 100000) {
                    continue;
                }
                childNodes = new ArrayList<>();
                childNodes = currNode.generateChildNodes();
                // Reverse the order of childNodes to maintain the order of DFS
                Collections.reverse(childNodes);
                if(visualize){
                    visualize(currNode,null);
//                    System.out.println("Current: " + currNode.state + " " + currNode.action + " " + currNode.depth +" " + (currNode.parentNode==null? "":
//                            " Parent: " + currNode.parentNode.state + " "
//                                    + currNode.parentNode.action + " " + currNode.parentNode.depth)
//                            +(currNode.parentNode==null? "": currNode.parentNode.parentNode==null?"":
//                            " GParent: " + currNode.parentNode.parentNode.action + " " + currNode.parentNode.parentNode.depth));
                }
                for (Node childNode : childNodes) {
                    if (childNode != null && !explored.contains(childNode)) {
                        if(visualize)
                            visualize(null,childNode);
//                            System.out.println("CHILDREN: " + childNode.state + " " + childNode.action + " " + childNode.depth );
                        frontier.add(childNode);
                    }
                }
            }
        }
        Object[] result = new Object[]{null, explored.size()};
        return result;
    }
    public static Object[] ids(boolean visualize) {
        int maxDepth = 10000; // Set a maximum depth limit or use an appropriate value
        for (int depth = 0; depth <= maxDepth; depth++) {
            PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(node -> -node.depth));
            frontier.add(currNode);
            Set<Node> explored = new HashSet<>();

            while (!frontier.isEmpty()) {
                Node node = frontier.poll();
                currNode = node;
                State state = currNode.state;
                if (state.prosperity == 100) {
                    return new Object[]{node, explored.size()};
                }
                if (!explored.contains(node) && node.depth <= depth) {
                    explored.add(node);
                    if (currNode.state.food == 0 || currNode.state.materials == 0 || currNode.state.energy == 0 || currNode.state.monetary_cost > 100000) {
                        continue;
                    }
                    List<Node> childNodes = node.generateChildNodes();
                    Collections.reverse(childNodes);
                    if (visualize) {
                        visualize(currNode, null);
                    }
                    for (Node childNode : childNodes) {
                        if (childNode != null && !explored.contains(childNode)) {
                            if (visualize) {
                                visualize(null, childNode);
                            }
                            frontier.add(childNode);
                        }
                    }
                }
            }
        }
        return new Object[]{null, 0}; // No solution found
    }

    public static Object[] ucs(boolean visualize) {
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(node -> currNode.state.monetary_cost));
        frontier.add(currNode);
        Set<Node> explored = new HashSet<>();

        while (!frontier.isEmpty()) {
            Node node = frontier.poll();
            currNode = node;
            State state = currNode.state;
            if (state.prosperity == 100) {
                Object[] result = new Object[]{node, explored.size()};
                return result;
            }
            if (!explored.contains(node)) {
                explored.add(node);
                if (currNode.state.food == 0 || currNode.state.materials == 0 || currNode.state.energy == 0 || currNode.state.monetary_cost > 100000) {
                    continue;
                }
                childNodes = new ArrayList<>();
                childNodes = currNode.generateChildNodes();
                // Reverse the order of childNodes to maintain the order of DFS
                Collections.reverse(childNodes);
                if(visualize){
                    visualize(currNode, null);
                }
                for (Node childNode : childNodes) {
                    if (childNode != null && !explored.contains(childNode)) {
                        if(visualize)
                            visualize(null, childNode);
                        frontier.add(childNode);
                    }
                }
            }
        }
        Object[] result = new Object[]{null, explored.size()};
        return result;
    }

    public static Object[] greedy(int heuristic , boolean visualize) {
        PriorityQueue<Node> frontier;
        if(heuristic==1){
            frontier = new PriorityQueue<>(Comparator.comparingInt(node -> currNode.state.heuristic1));
        }
        else{
            frontier = new PriorityQueue<>(Comparator.comparingInt(node -> currNode.state.heuristic2));
        }

        frontier.add(currNode);
        Set<Node> explored = new HashSet<>();

        while (!frontier.isEmpty()) {
            Node node = frontier.poll();
            currNode = node;
            State state = currNode.state;
            if (state.prosperity == 100) {
                Object[] result = new Object[]{node, explored.size()};
                return result;
            }
            if (!explored.contains(node)) {
                explored.add(node);
                if (currNode.state.food == 0 || currNode.state.materials == 0 || currNode.state.energy == 0 || currNode.state.monetary_cost > 100000) {
                    continue;
                }
                childNodes = new ArrayList<>();
                childNodes = currNode.generateChildNodes();
                // Reverse the order of childNodes to maintain the order of DFS
                Collections.reverse(childNodes);
                if(visualize){
                    visualize(currNode, null);
                }
                for (Node childNode : childNodes) {
                    if (childNode != null && !explored.contains(childNode)) {
                        if(visualize)
                            visualize(null, childNode);
                        frontier.add(childNode);
                    }
                }
            }
        }
        Object[] result = new Object[]{null, explored.size()};
        return result;
    }
    public static Object[] A_star(int heuristic , boolean visualize) {
        PriorityQueue<Node> frontier;
        if(heuristic==1){
            frontier = new PriorityQueue<>(Comparator.comparingInt(node -> currNode.state.admissible_heuristic1));
        }
        else{
            frontier = new PriorityQueue<>(Comparator.comparingInt(node -> currNode.state.admissible_heuristic2));
        }
        frontier.add(currNode);
        Set<Node> explored = new HashSet<>();

        while (!frontier.isEmpty()) {
            Node node = frontier.poll();
            currNode = node;
            State state = currNode.state;
            if (state.prosperity == 100) {
                Object[] result = new Object[]{node, explored.size()};
                return result;
            }
            if (!explored.contains(node)) {
                explored.add(node);
                if (currNode.state.food == 0 || currNode.state.materials == 0 || currNode.state.energy == 0 || currNode.state.monetary_cost > 100000) {
                    continue;
                }
                childNodes = new ArrayList<>();
                childNodes = currNode.generateChildNodes();
                // Reverse the order of childNodes to maintain the order of DFS
                Collections.reverse(childNodes);
                if(visualize){
                    visualize(currNode, null);
                }
                for (Node childNode : childNodes) {
                    if (childNode != null && !explored.contains(childNode)) {
                        if(visualize)
                            visualize(null, childNode);
                        frontier.add(childNode);
                    }
                }
            }
        }
        Object[] result = new Object[]{null, explored.size()};
        return result;
    }
    public static String Generic(String QingFunc,boolean visualize){
        if ( QingFunc.equals("BF")){
            Object[] result = bfs(visualize);
            Node n  = (Node) result[0];
            System.out.println("--->Depth: " + n.depth + n);
            Node z= n ;
            for ( int i = 0 ; i<n.depth ; i++){
                z=z.parentNode ;
                if(z==null) break;
                System.out.println("--->Depth: " + z.depth + z);
            }
            return GenerateSolution(result);
        }
        if ( QingFunc.equals("DF")){
            Object[] result = dfs(visualize);
            return GenerateSolution(result);
        }
        if ( QingFunc.equals("ID")){
            Object[] result = ids(visualize);
            return GenerateSolution(result);
        }
        if ( QingFunc.equals("UC")){
            Object[] result = ucs(visualize);
            return GenerateSolution(result);
        }
        if ( QingFunc.equals("GR1")){
            Object[] result = greedy(1,visualize);
            return GenerateSolution(result);

        }
        if ( QingFunc.equals("GR2")){
            Object[] result = greedy(2,visualize);
            return GenerateSolution(result);

        }
        if ( QingFunc.equals("AS1")){
            Object[] result = A_star(1,visualize);
            return GenerateSolution(result);

        }
        if ( QingFunc.equals("AS2")){
            Object[] result = A_star(2,visualize);
            return GenerateSolution(result);

        }
        return "You didn't enter a valid search algorithm";
    }
    public static String GenerateSolution(Object[] res ){
        StringBuilder sb = new StringBuilder();
        Node n = (Node) res[0];
        int exp = (int) res[1];

        if(n==null){
            sb.append("NOSOLUTION");
        }
        else {
            String[] rs = new String[n.depth];
            Node z = n;
            for (int i = 0 ; i<n.depth ; i++) {
                z = z.parentNode;
                if (z == null) break;
                String a = z.action==Action.REQUEST_ENERGY? "RequestEnergy" :z.action==Action.REQUEST_MATERIALS?"RequestMaterials":z.action==Action.REQUEST_FOOD?"RequestFood": String.valueOf(z.action);
                rs[i] = a;//sb.append(z.action).append(",");
            }
            for(int i  = rs.length-2 ; i >= 0 ; i -- ){
                sb.append(rs[i] + ",");
            }
            sb.append(n.action).append(";").append(n.state.monetary_cost).append(";").append(exp);
        }
        return sb.toString();

    }
    public static void visualize(Node currNode, Node childNode){
        if(currNode!=null){
            System.out.println("Current: " + currNode.state + " " + currNode.action + " " + currNode.depth +" " + (currNode.parentNode==null? "":
                    " Parent: " + currNode.parentNode.state + " "
                            + currNode.parentNode.action + " " + currNode.parentNode.depth)
                    +(currNode.parentNode==null? "": currNode.parentNode.parentNode==null?"":
                    " GParent: " + currNode.parentNode.parentNode.action + " " + currNode.parentNode.parentNode.depth));

        }
        else if(childNode!=null){
            System.out.println("CHILDREN: " + childNode.state + " " + childNode.action + " " + childNode.depth );
        }
    }
    public boolean GoalTest(Node n ){
        if(n.state.prosperity>=100){
            return true;
        }
        return false ;
    }

}
