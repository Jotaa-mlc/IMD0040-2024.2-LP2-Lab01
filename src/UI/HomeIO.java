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
            String greetings = "Bem vindo ao LP2 Bank";
            System.out.println("#" + centerText(greetings, " ") + "#");
            printOptions(homeOptions);
            int command = sc.nextInt();
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
                default:
                    printInvalidCommand();
                    break;
            }
        }
        printGoodBye();
    }
    private static void login() {
        String cpf, password;
        System.out.println(centerText("Login", "-"));
        System.out.print("CPF: ");
        cpf = sc.nextLine();
        System.out.print("Senha: ");
        password = sc.nextLine();

        Client client = Bank.login(cpf, password);
        if (client != null) {
            System.out.print("Olá, seja bem vindo " + client.getName() + "!");
            MenuIO.chooseAccount(client);
        }
        else {
            System.out.println("Não foi possível realizar a login!");
        }
    }
    private static void addClient() {
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
    private static void printGoodBye() {
        System.out.println("Muito obrigado por usar os serviços do LP2 Bank!");
        System.out.println("Até a próxima!");
    }
    }