package Code;// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
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
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
//        System.out.printf("Hello and welcome!");
//
//        // Press Shift+F10 or click the green arrow button in the gutter to run the code.
//        for (int i = 1; i <= 5; i++) {
//
//            // Press Shift+F9 to start debugging your code. We have set one breakpoint
//            // for you, but you can always add more by pressing Ctrl+F8.
//            System.out.println("i = " + i);
//        }
        String[][] twoDArray = splitString("50;20,20,20");
        for (int i = 0; i < twoDArray.length; i++) {
            // Iterate through the columns
            for (int j = 0; j < twoDArray[i].length; j++) {
                System.out.print(twoDArray[i][j] + " ");
            }
            // Print a new line to move to the next row
            System.out.println();
        }
//        System.out.println(Arrays.toString());
    }
}