package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Account;
import model.Client;

public class Agency {
    private final int id;
    private int agAccountId;
    protected HashMap<Integer, Account> accounts;
    protected HashMap<String, List<Account>> accountsByOwner;

    protected int getId() {return id;}
    protected List<Account> getAccountByOwner(Client owner) {
        return accountsByOwner.get(owner.getCPF());
    }
    public Agency(int id) {
        this.id = id;
        this.agAccountId = 0;
        this.accounts = new HashMap<>();
        this.accountsByOwner = new HashMap<>();
    }
    public int getAgAccountId() {return agAccountId;}
    public Account getAccountByID(int id) {
        return accounts.get(id);
    }
    public void addAccount(Account account) {
        try {
            Loader.saveAccount(account);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        accounts.put(account.getAccountId(), account);
        this.agAccountId++;
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