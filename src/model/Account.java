package model;

public class Account {
    private static int accountIDs = 0;
    private final int id;
    private final String type;
    private final Client owner;
    private float balance;

    public Account(String type, Client owner) {
        this.type = type;
        this.owner = owner;
        this.id = accountIDs++;
        this.balance = 0;
    }
}
