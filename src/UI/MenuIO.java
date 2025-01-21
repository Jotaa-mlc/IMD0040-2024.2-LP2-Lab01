package UI;

import controller.Bank;
import model.Account;
import model.AccountType;
import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuIO extends Bank {
    private static final Scanner sc = new Scanner(System.in);
    private static final List<String> menuOptions = new ArrayList<>() {
        {
            add("Transferir");
            add("Depositar");
            add("Sacar");
            add("Saldo");
            add("Adicionar Conta");
            add("Logout");
        }
    };

    public static void chooseAccount(Client client) {
        List<Account> accounts = Bank.getAccountsByOwner(client);

        if (accounts.isEmpty()) {
            System.out.println("Não temos nenhuma conta registrada no seu nome.\nDeseja abrir uma? 1 - para sim");
            int command = sc.nextInt();
            sc.nextLine();
            if (command == 1) printMenu(addAccount(client), client);
        } else if (accounts.size() == 1) {
            printMenu(accounts.getFirst(), client);
        } else {
            System.out.println("Favor escolha entre uma de suas contas, informe o ID.");
            for (Account account : accounts) System.out.println(account.toString());
            int id = sc.nextInt();
            sc.nextLine();

            for (Account account : accounts) {
                if (account.getAccountId() == id){
                    printMenu(account, client);
                }
                else {
                    System.out.println("Você não possui nenhuma conta com esse ID.");
                }
            }
        }
    }
    public static void printMenu(Account account, Client client) {
        boolean logout = false;

        while (!logout) {
            System.out.println("Logado - " + account.getOwnerName() + " | ID da Conta: " + account.getAccountId());
            IO.printOptions(menuOptions);
            int command = sc.nextInt();
            sc.nextLine();
            switch (command) {
                case 1://Transferir
                    transfer(account);
                    break;
                case 2://Depositar
                    deposit(account);
                    break;
                case 3://Sacar
                    withdraw(account);
                    break;
                case 4://Saldo
                    balance(account);
                    break;
                case 5://Adicionar Conta
                    addAccount(client);
                    break;
                case 6://Logout
                    logout = true;
                    break;
                default:
                    IO.printInvalidCommand();
                    break;
            }
        }
        System.out.println("Encerrando sua secção...");
    }
    private static void transfer(Account account) {
        System.out.println("Transferência - " + account.getOwnerName() + " | ID da Conta: " + account.getAccountId());
        System.out.print("Informe ID da conta para transferir: ");
        int id = sc.nextInt();
        System.out.print("Qual valor deseja transferir? ");
        float value = sc.nextFloat();
        switch (Bank.transfer(value, account, id)) {
            case 1:
                System.out.println("Transferência realizada com sucesso!");
                balance(account);
                return;
            case 0:
                System.out.println("Não foi possível encontrar uma conta com o ID informado.");
                break;
            case -1:
                System.out.println("Valor inválido para operação!");
                break;
            case -2:
                System.out.println("Saldo insuficiente!");
                break;
        }
        System.out.println("Cancelando operação...");
    }
    private static void deposit(Account account) {
        System.out.println("Depósito - " + account.getOwnerName() + " | ID da Conta: " + account.getAccountId());
        System.out.print("Qual valor deseja depositar? ");
        float value = sc.nextFloat();
        if(Bank.deposit(value, account) == 1) {
            System.out.println("Valor depositado com sucesso!");
            balance(account);
            return;
        }
        System.out.println("Valor inválido para operação!");
        System.out.println("Cancelando operação...");
    }
    private static void withdraw(Account account) {
        System.out.println("Saque - " + account.getOwnerName() + " | ID da Conta: " + account.getAccountId());
        System.out.print("Qual valor deseja sacar? ");
        float value = sc.nextFloat();
        switch (Bank.withdraw(value,account)){
            case 1:
                System.out.println("Saque realizado com sucesso!");
                balance(account);
                return;
            case -1:
                System.out.println("Valor inválido para operação!");
                break;
            case -2:
                System.out.println("Saldo insuficiente!");
                break;
        }
        System.out.println("Cancelando operação...");
    }
    private static void balance(Account account) {
        System.out.println("Saldo - " + account.getOwnerName() + " | ID da Conta: " + account.getAccountId());
        System.out.printf("Saldo atual: R$ %.2f%n", account.getBalance());
    }
    private static Account addAccount(Client client) {
        System.out.println("Adicionar Conta - " + client.getName());
        System.out.println("Qual tipo de conta deseja abrir?\t1 - Corrente\t2 - Poupança");
        int opt = sc.nextInt();
        if (opt > 2 || opt < 1) {
            System.out.println("Tipo de conta inválido! Cancelando operação...");
            return null;
        }
        //String type = opt == 1 ? "Corrente" : "Poupança";
        Account account = new Account(0,0, client, AccountType.CURRENT, 0);
        Bank.addAccount(account);

        System.out.println("Conta criada com sucesso!");
        account.print();
        return account;
    }
}
