package model;

import java.util.Map;

public class Agency {
    private final int id;
    private int agAccountId;
    private Map<Integer, Account> accounts;

    public Agency(int id, int agAccountId) {
        this.id = id;
        this.agAccountId = agAccountId;
    }

    public int getId() {return id;}

    public Account getAccountByID(int id) {
        return accounts.get(id);
    }

    public void addAccount(Account account) {
        accounts.put(account.getId(), account);
    }
}