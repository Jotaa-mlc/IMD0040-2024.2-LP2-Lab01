package UI;

import controller.Bank;
import model.Client;

import java.util.ArrayList;
import java.util.List;

public class HomeIO extends IO {
    private static final List<String> homeOptions = new ArrayList<>() {
        {
            add("Login");
            add("Adicionar Usuário");
            add("Encerrar Programa");
        }
    };

    public static void printHome() {
        boolean exit = false;

        while (!exit) {
            String greetings = "\nBem vindo ao LP2 Bank";
            System.out.println("#" + centerText(greetings, " ") + "#");
            printOptions(homeOptions);
            int command = sc.nextInt();
            sc.nextLine();
            switch (command) {
                case 1://Login
                    login();
                    break;
                case 2://Add Client
                    addClient();
                    break;
                case 3://Encerrar Programa
                    exit = true;
                    break;
                case 123://Encerrar Programa
                    Bank.processar();
                    break;
                default:
                    printInvalidCommand();
                    break;
            }
        }
        printGoodBye();
    }
    private static void login() {
        String cpf, password;
        System.out.println(centerText("Login", " "));
        System.out.print("CPF: ");
        cpf = sc.nextLine();
        System.out.print("Senha: ");
        password = sc.nextLine();

        Client client = Bank.login(cpf, password);
        if (client != null) {
            System.out.println("Olá, seja bem vindo " + client.getName() + "!\n");
            MenuIO.chooseAccount(client);
        }
        else {
            System.out.println("Não foi possível realizar a login!\n");
        }
    }
    private static void addClient() {
        String name, cpf, password;
        System.out.println(centerText("Adicionando novo Cliente", " "));
        System.out.print("Nome completo: ");
        name = sc.nextLine();
        System.out.print("CPF: ");
        cpf = sc.nextLine();
        System.out.print("Senha: ");
        password = sc.nextLine();

        if (name.isBlank() || cpf.isBlank() || password.isBlank()) {
            System.out.println("Nenhum dos campos podem ser vazios!\nNão foi possível adicionar novo cliente!");
            return;
        }

        Client client = new Client(name, cpf, password);
        
        try {
            Bank.addClient(client);
            System.out.println("Cliente adicionado com sucesso!");
        } catch (Exception e) {
            System.err.println(e.getMessage() + "\nCancelando operação...");
        }
    }
    private static void printGoodBye() {
        System.out.println("\nMuito obrigado por usar os serviços do LP2 Bank!");
        System.out.println("Até a próxima!");
    }
}