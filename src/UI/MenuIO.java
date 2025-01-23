package UI;

import controller.Agency;
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
            System.out.println("Escolha entre uma de suas contas, informe o número a esquerda da conta que deseja.");
            for (int i = 0; i < accounts.size(); i++) {
                System.out.println(i+1 + " - " + accounts.get(i).print());
            }
            int accountIndex = sc.nextInt();
            sc.nextLine();

            if (accountIndex > 0 && accountIndex <= accounts.size()) {
                printMenu(accounts.get(accountIndex), client);
            }
            else {
                System.out.println("Não foi possível escolher a conta.");
                return;
            }
        }
    }
    private static void printMenu(Account account, Client client) {
        boolean logout = false;

        while (!logout) {
            String.format("Logado - %1$s | ID Agência: %2$4d | ID Conta: %#3$4d", account.getOwnerName(), account.getAgencyId(), account.getAccountId());
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
        System.out.print("Em qual agência deseja abrir a conta?\tAgências disponíveis:");
        List<String> agenciesIds = Bank.getAgenciesIds();
        for (String string : agenciesIds) {
            System.out.println(string);
            if (!string.equals(agenciesIds.getLast())) {
                System.out.println(" - ");
            }
        }
        int agencyId = sc.nextInt();
        Agency ag = Bank.getAgencyById(agencyId);
        if (ag == null) {
            System.out.println("A agencia que você escolheu não está disponível. Cancelando operação...");
            return null;
        }

        System.out.println("Qual tipo de conta deseja abrir?");
        AccountType[] types = AccountType.values();
        for (int i = 0; i < types.length ; i++) {
            System.out.println(i+1 + " - " + types[i]);
        }
        int typeInt = sc.nextInt();
        if (typeInt < 0 || typeInt > types.length) {
            System.out.println("Tipo de conta inválido! Cancelando operação...");
            return null;
        }
        Account account = new Account(agencyId,ag.getAgAccountId(), client, AccountType.fromInteger(typeInt), 0);
        Bank.addAccount(account);

        System.out.println("Conta criada com sucesso!");
        account.print();
        return account;
    }
}
