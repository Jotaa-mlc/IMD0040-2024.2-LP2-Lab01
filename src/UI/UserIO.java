package UI;

import controller.Bank;
import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserIO {
    private static final int width = 50;
    private static final Scanner sc = new Scanner(System.in);
    private static final List<String> homeOptions = new ArrayList<String>(){
        {
            add("Adicionar Usuário");
            add("Adicionar Conta");
            add("Login");
            add("Encerrar Programa");
        }
    };
    private static final List<String> menuOptions = new ArrayList<String>(){
        {
            add("Transferir");
            add("Depositar");
            add("Sacar");
            add("Saldo");
            add("Adicionar Conta");
            add("Logout");
        }
    };

    private static String centerText(String text, String fill) {
        if (text.isBlank() || width <= text.length()) {
            return text;
        }

        int leftPadding = (width - text.length()) / 2;
        int rightPadding = width - leftPadding;

        return fill.repeat(leftPadding) + text + fill.repeat(rightPadding);
    }

    public static void printHome() {
        String greetings = "Bem vindo ao LP2 Bank";
        System.out.println("#" + centerText(greetings, " ") + "#");
        printOptions(homeOptions);

        boolean exit = false;

        while (!exit) {
            int command = getCommand();
            switch (command) {
                case 1://Add Client
                    addClient();
                    break;
                case 2://Add Account
                    addAccount();
                    break;
                case 3://Login
                    login();
                    break;
                case 4://Encerrar Programa
                    printGoodBye();
                    exit = true;
                    break;
                default:
                    printInvalidCommand();
                    break;
            }
        }
    }

    private static void printMenu(Client client) {
        System.out.println("Logado: " + client.getName());
        printOptions(menuOptions);

        boolean logout = false;

        while (!logout) {
            int command = getCommand();
            switch (command) {
                case 1://Transferir
                    break;
                case 2://Depositar
                    break;
                case 3://Sacar
                    break;
                case 4://Saldo
                    break;
                case 5://Adicionar Conta
                    break;
                case 6://Logout
                    logout = true;
                    break;
                default:
                    printInvalidCommand();
                    break;
            }
        }
    }

    private static void printOptions(List<String> options) {
        System.out.println("/" + "-".repeat(width) + "\\");
        System.out.println("|" + " ".repeat(width) + "|");
        for (String option : options) {
            int i = 1;
            System.out.println("|" + centerText(i++ + " - " + option, " ") + "|");
        }
        System.out.println("\\" + "-".repeat(width) + "/");
    }

    public static void printGoodBye() {
        System.out.println("Muito obrigado por usar os serviços do LP2 Bank!");
        System.out.println("Até a próxima!");
    }

    public static void printInvalidCommand() {
        System.out.println("Não foi possível realizar ação: comando não reconhecido.\nFavor, escolha uma das opções descritas.");
    }

    public static int getCommand(){
        int command;
        command = sc.nextInt();

        return command;
    }

    public static void addClient() {
        String name, cpf, password;
        System.out.println(centerText("Adicionando novo Cliente", "-"));
        System.out.print("Nome completo: ");
        name = sc.nextLine();
        System.out.print("CPF: ");
        cpf = sc.nextLine();
        System.out.print("Senha: ");
        password = sc.nextLine();

        Client client = new Client(name, cpf, password);

        if (!Bank.addClient(client)) {
            System.out.println("Não foi possível adicionar o novo cliente! Já existe um com este CPF.");
        }
    }

    public static void addAccount() {

    }

    public static void login() {
        String cpf, password;
        System.out.println(centerText("Login", "-"));
        System.out.print("CPF: ");
        cpf = sc.nextLine();

        Client client = Bank.findClientByCPF(cpf);
        if (client == null){
            System.out.println("Não existe cliente cadastrado com esse CPF.");
            return;
        }

        System.out.print("Senha: ");
        password = sc.nextLine();

        if (client.authenticate(password)) {
            System.out.print("Olá, seja bem vindo " + client.getName() + "!");
            printMenu(client);
        }
    }
}