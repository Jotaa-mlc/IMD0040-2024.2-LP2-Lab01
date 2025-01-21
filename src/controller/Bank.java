package controller;

import model.Account;
import model.Client;
import model.Agency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bank {
    private static HashMap<String, Client> clients2 = new HashMap<>();
    private static HashMap<Integer, Agency> agencies = new HashMap<>();
    private static final List<Client> clients = new ArrayList<>();
    private static final List<Account> accounts = new ArrayList<>();

    protected static Client getClientByCPF(String cpf) {
        return clients2.get(cpf);
    }
    protected static Account getAccountByID(int id) {
        for (Account account : accounts) {
            if (account.getId() == id) {
                return account;
            }
        }
        return null;
    }
    protected static List<Account> getAccountsByOwner(Client owner) {
        List<Account> clientsAccounts = new ArrayList<>();
        for (Account account : accounts) {
            if (account.authenticate(owner)) {
                clientsAccounts.add(account);
            }
        }
        return clientsAccounts;
    }
    public static boolean addClient(Client client){
        if (getClientByCPF(client.getCPF()) != null) {
            return false;
        }
        clients.add(client);
        return true;
    }
    public static void addAccount(Account account){
        accounts.add(account);
    }
    public static Client login(String cpf, String password) {
        Client client = getClientByCPF(cpf);
        if (client != null && client.authenticate(password)) {
            return client;
        }
        return null;
    }
    public static int deposit(float amount, Account account) {
        if (amount <= 0) { return -1; }
        account.deposit(amount);
        return 1;
    }
    public static int withdraw(float amount, Account account) {
        if (amount <= 0) { return -1; }
        if (account.getBalance() < amount) { return -2; }
        account.withdraw(amount);
        return 1;
    }
    public static int transfer(float amount, Account from, int idTo) {
        if (amount <= 0) { return -1; }
        if (from.getBalance() < amount) { return -2; }
        Account to = getAccountByID(idTo);
        if (to != null) {
            from.withdraw(amount);
            to.deposit(amount);
            return 1;
        }
        return 0;
    }
}
