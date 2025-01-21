package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Account;
import model.Client;

public class Agency {
    private final int id;
    private int agAccountId;
    private Map<Integer, Account> accounts;
    private Map<String, List<Account>> accountsByOwner;

    protected List<Account> getAccountByOwner(Client owner) {
        return accountsByOwner.get(owner.getCPF());
    }
    public Agency(int id) {
        this.id = id;
        this.agAccountId = 0;
    }
    public int getAccountId() {return agAccountId;}
    public int getId() {return id;}
    public Account getAccountByID(int id) {
        return accounts.get(id);
    }
    public void addAccount(Account account) {
        try {
            Loader.saveAccount(account);
        } catch (IOException e) {
            System.err.println("Não foi possível acessar o DB da Agência " + this.getId() + ".");
            return;
        }

        accounts.put(account.getAccountId(), account);
        agAccountId++;
        List<Account> accs = accountsByOwner.get(account.getOwnerCPF());
        if (accs != null) {
            accs.add(account);
        }
        else {
            List<Account> ac = new ArrayList<>();
            ac.add(account);
            accountsByOwner.put(account.getOwnerCPF(), ac);
        }
    }
}