package code;

public class Main {
    public static String[][] splitString(String input) {
        String[] semiColonSplit = input.split(";");
        String[][] result = new String[semiColonSplit.length][];

        for (int i = 0; i < semiColonSplit.length; i++) {
            result[i] = semiColonSplit[i].split(",");
        }

        return result;
    }
    public static void main(String[] args) {
        String initialState0 = "17;" +
                "49,30,46;" +
                "7,57,6;" +
                "7,1;20,2;29,2;" +
                "350,10,9,8,28;" +
                "408,8,12,13,34;";
        String initialState1 = "50;" + //initialPros
                "12,12,12;" + //init food,materials,energy
                "50,60,70;" + //cost food , materials ,enery
                "30,2;19,2;15,2;" + //amountReq ,delay : food , materials , energy
                "300,5,7,3,20;" + //cost Build1 ,use food , materials , energy , prosperity
                "500,8,6,3,40;"; //cost Build2 ,use food , materials , energy , prosperity
        String initialState2 = "30;" +
                "30,25,19;" +
                "90,120,150;" +
                "9,2;13,1;11,1;" +
                "3195,11,12,10,34;" +
                "691,7,8,6,15;";
        System.out.println(LLAPSearch.solve(initialState2,"BF",false));
    }
}