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
        int rightPadding = width - leftPadding - text.length();

        return fill.repeat(leftPadding) + text + fill.repeat(rightPadding);
    }
    public static void printOptions(List<String> options) {
        System.out.println("/" + "-".repeat(width) + "\\");
        System.out.println("|" + " ".repeat(width) + "|");
        for (int i = 0; i < options.size(); i++) {

            System.out.println("|" + centerText(i+1 + " - " + options.get(i), " ") + "|");
        }
        System.out.println("\\" + "-".repeat(width) + "/");
    }
    public static void printInvalidCommand() {
        System.out.println("Não foi possível realizar ação: comando não reconhecido.\nFavor, escolha uma das opções descritas.");
    }
}