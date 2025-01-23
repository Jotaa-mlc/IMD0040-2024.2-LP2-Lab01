package UI;

import controller.Agency;
import controller.Bank;
import model.Account;
import model.AccountType;
import model.Client;
import model.ContaSalario;

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
            printMenu(accounts.get(0), client);
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
            System.out.println(String.format("Logado - %1$s | ID Agência: %2$4d | ID Conta: %#3$4d", account.getOwnerName(), account.getAgencyId(), account.getAccountId()));
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
        System.out.println(String.format("Transferênca - %1$s | ID Agência: %2$4d | ID Conta: %#3$4d", account.getOwnerName(), account.getAgencyId(), account.getAccountId()));
        System.out.print("Informe ID da agência para transferir: ");
        int agencyId = sc.nextInt();
        System.out.print("Informe ID da conta para transferir: ");
        int accountId = sc.nextInt();
        System.out.print("Qual valor deseja transferir? ");
        float value = sc.nextFloat();
        Agency agency = Bank.getAgencyByID(agencyId);
        if (agency == null) {
            System.out.println("Não foi possível encontrar uma agência com o ID informado.");
            System.out.println("Cancelando operação...");
            return;
        }
        
        Account targetAccount = agency.getAccountByID(accountId);
        if (targetAccount == null) {
            System.out.println("Não foi possível encontrar uma conta com o ID informado.");
            System.out.println("Cancelando operação...");
            return;
        }
        try {
            Bank.transfer(value, account, account);
            System.out.println("Trasnferência realizada com sucesso!");
            balance(account);
        } catch (Exception e) {
            System.err.println(e.getMessage() + "Cancelando operação...");
        }
    }
    private static void deposit(Account account) {
        System.out.println(String.format("Depósito - %1$s | ID Agência: %2$4d | ID Conta: %#3$4d", account.getOwnerName(), account.getAgencyId(), account.getAccountId()));
        System.out.print("Qual valor deseja depositar? ");
        float value = sc.nextFloat();
        try {
            Bank.deposit(value, account);
            System.out.println("Valor depositado com sucesso!");
            balance(account);  
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\nCancelando operação...");
        }
        
    }
    private static void withdraw(Account account) {
        System.out.println(String.format("Saque - %1$s | ID Agência: %2$4d | ID Conta: %#3$4d", account.getOwnerName(), account.getAgencyId(), account.getAccountId()));
        System.out.print("Qual valor deseja sacar? ");
        float value = sc.nextFloat();
        try {
            Bank.withdraw(value,account);
            System.out.println("Saque realizado com sucesso!");
            balance(account);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\nCancelando operação...");
        }
    }
    private static void balance(Account account) {
        System.out.println(String.format("Saldo - %1$s | ID Agência: %2$4d | ID Conta: %#3$4d", account.getOwnerName(), account.getAgencyId(), account.getAccountId()));
        System.out.printf("Saldo atual: R$ %.2f%n", account.getBalance());
    }
    private static Account addAccount(Client client) {
        System.out.println("Adicionar Conta - " + client.getName());
        System.out.print("Em qual agência deseja abrir a conta?\tAgências disponíveis:");
        List<String> agenciesIds = Bank.getAgenciesIds();
        for (String string : agenciesIds) {
            System.out.println(string);
            if (!string.equals(agenciesIds.get(agenciesIds.size() - 1))) {
                System.out.println(" - ");
            }
        }
        int agencyId = sc.nextInt();
        Agency ag = Bank.getAgencyByID(agencyId);
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
        AccountType type = AccountType.fromInteger(typeInt);
        Account account = new Account(agencyId,ag.getAgAccountId(), client, type, 0);

        if (type == AccountType.Salário) {
            System.out.println("Para abrir uma conta salário precisamos do CPF do seu empregador. Por Favor informe.\nCPF do empregador: ");
            String cpfEmpregador = sc.nextLine();
            account = new ContaSalario(agencyId, agencyId, client, type, 0, cpfEmpregador);
            
        }
        
        Bank.addAccount(account);
        System.out.println("Conta criada com sucesso!");
        System.out.println(account.print());
        return account;
    }
}
