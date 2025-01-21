package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Loader;

public class Agency {
    private final int id;
    private int agAccountId;
    private Map<Integer, Account> accounts;
    private Map<String, List<Account>> accountsByOwner;

    protected List<Account> getAccountByOwner(Client owner) {
        return accountsByOwner.get(owner.getCPF());
    }
    public Agency(int id, int agAccountId) {
        this.id = id;
        this.agAccountId = agAccountId;
    }
    public int getNextAccountId() {return ++agAccountId;}
    public int getId() {return id;}
    public Account getAccountByID(int id) {
        return accounts.get(id);
    }
    public void addAccount(Account account) {
        try {
            Loader.saveAccount(this, account);
        } catch (IOException e) {
            System.err.println("Não foi possível acessar o DB da Agência " + this.getId() + ".");
            return;
        }

        accounts.put(account.getId(), account);
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