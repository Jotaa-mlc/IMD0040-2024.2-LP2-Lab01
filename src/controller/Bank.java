package controller;

import model.Account;
import model.Client;
import model.ContaSalario;

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
        if (client != null && client.authenticate(password)) { return client; }
        return null;
    }
    public static void deposit(float amount, Account account) {
        if (amount <= 0) { throw new IllegalArgumentException("Valor inválido para depósito."); }
        account.deposit(amount);
    }
    public static void withdraw(float amount, Account account) {
        if (amount <= 0) { throw new IllegalArgumentException("Valor inválido para saque."); }
        if (amount > account.getBalance()) { throw new ArithmeticException("Saldo Insuficiente para saque."); }
        account.withdraw(amount);
    }
    public static void transfer(float amount, Account from, Account to) {
        if (amount <= 0) { throw new IllegalArgumentException("Valor inválido para transferência."); }
        if (amount > from.getBalance()) { throw new ArithmeticException("Saldo Insuficiente para transferir."); }
        if (to instanceof ContaSalario) {
            ContaSalario to_ContaSalario = (ContaSalario) to;
            if (!from.getOwnerCPF().equals(to_ContaSalario.getCpfEmpregador())) { throw new IllegalStateException("Não foi possível realizar a transferência pois você não é o empregador dessa conta."); }
        }
        from.withdraw(amount);
        to.deposit(amount);
    }
    public static List<String> getAgenciesIds() {
        List<String> agenciesIds = new ArrayList<>();
        for (Agency ag : agencies.values()) {
            agenciesIds.addLast(String.valueOf(ag.getId()));
        }
        return agenciesIds;
    }
    public static Agency getAgencyById(int id){
        return agencies.get(id);
    }
    public static void setClients(HashMap<String, Client> clients_) {
        clients = clients_;
    }
    public static void setAgencies(HashMap<Integer, Agency> agencies_) {
        agencies = agencies_;
    }
}
