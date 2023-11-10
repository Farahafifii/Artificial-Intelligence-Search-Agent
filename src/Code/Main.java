package Code;
import java.util.Arrays;
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
        String init = "50;"+
                "22,22,22;" +
                "50,60,70;" +
                "30,2;19,1;15,1;" +
                "300,5,7,3,20;" +
                "500,8,6,3,40;";
        LLAPSearch.solve(init,"BF",false);
    }
}