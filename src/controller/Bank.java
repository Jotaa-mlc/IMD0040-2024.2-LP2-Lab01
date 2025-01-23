package controller;

import model.Account;
import model.AccountType;
import model.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bank {
    private static HashMap<String, Client> clients = new HashMap<>();
    private static HashMap<Integer, Agency> agencies = new HashMap<>();

    protected static Client getClientByCPF(String cpf) {
        return clients.get(cpf);
    }
    protected static Account getAccountByID(int id) {
        for (Agency agency : agencies.values()) {
            Account account = agency.getAccountByID(id);
            if (account != null) {
                return account;
            }
        }
        return null;
    }
    protected static List<Account> getAccountsByOwner(Client owner) {
        List<Account> accounts = new ArrayList<>();

        for (Agency ag : agencies.values()) {
            accounts.addAll(ag.getAccountByOwner(owner));
        }

        return accounts;
    }
    public static boolean addClient(Client client){//consertar os retornos p/ erros
        if (!clients.containsKey(client.getCPF())) {
            try {
                Loader.saveClient(client);
                clients.put(client.getCPF(), client);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                return false;//momentâneo
            }
            return true;
        }
        return false;
    }
    public static void addAccount(Account account){
        Agency ag = agencies.get(account.getAgencyId());
        if (ag != null) {
            ag.addAccount(account);
        }
    }
    public static Client login(String cpf, String password) {
        Client client = clients.get(cpf);
        if (client != null && client.authenticate(password)) {
            return client;
        }
        return null;
    }
    public static int deposit(float amount, Account account) {

        if(account.getAccountType() == AccountType.Salário){
            return 1;
        }
        else{
            if (amount <= 0) { return -1; }
            account.deposit(amount);
            return 1;
        }
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
    public static List<String> getAgenciesIds() {
        List<String> agenciesIds = new ArrayList<>();
        for (Agency ag : agencies.values()) {
            agenciesIds.add(String.valueOf(ag.getId()));
        }
        return agenciesIds;
    }
    public static Agency getAgencyByID(int id){
        return agencies.get(id);
    }
    public static void setClients(HashMap<String, Client> clients_) {
        clients = clients_;
    }
    public static void setAgencies(HashMap<Integer, Agency> agencies_) {
        agencies = agencies_;
    }
}
