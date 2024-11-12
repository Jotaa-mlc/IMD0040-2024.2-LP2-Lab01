package controller;

import model.Account;
import model.Client;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private static List<Client> clients = new ArrayList<>();
    private static List<Account> accounts = new ArrayList<>();


    public static Client findClientByCPF(String cpf) {
        for (Client client : clients) {
            if (client.getCPF().equals(cpf)) {
                return client;
            }
        }
        return null;
    }

    public static boolean addClient(Client client){
        if (findClientByCPF(client.getCPF()) != null) {
            return false;
        }
        clients.add(client);
        return true;
    }

    public static void addAccount(Account account){
        accounts.add(account);
    }
}
