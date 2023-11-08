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

        LLAPSearch.solve(initialState0,"BF",false);
    }
}