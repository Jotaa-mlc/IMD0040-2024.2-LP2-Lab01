package UI;

import java.util.List;
import java.util.Scanner;

public class IO {
    protected static final int width = 50;
    protected static final Scanner sc = new Scanner(System.in);

    public static String centerText(String text, String fill) {
        if (text.isBlank() || width <= text.length()) {
            return text;
        }

        int leftPadding = (width - text.length()) / 2;
        int rightPadding = width - leftPadding;

        return fill.repeat(leftPadding) + text + fill.repeat(rightPadding);
    }
    public static void printOptions(List<String> options) {
        System.out.println("/" + "-".repeat(width) + "\\");
        System.out.println("|" + " ".repeat(width) + "|");
        for (String option : options) {
            int i = 1;
            System.out.println("|" + centerText(i++ + " - " + option, " ") + "|");
        }
        System.out.println("\\" + "-".repeat(width) + "/");
    }
    public static void printInvalidCommand() {
        System.out.println("Não foi possível realizar ação: comando não reconhecido.\nFavor, escolha uma das opções descritas.");
    }
}