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
    public int getId() {
        return id;
    }
    public String getOwnerName() {
        return owner.getName();
    }
    public boolean authenticate(Client owner) {
        return this.owner.equals(owner);
    }
    @Override
    public String toString() {
        return "Account ID: " + id + " | Type: " + type;
    }
    public void print(){
        System.out.println("Conta ID: " + id + " | Tipo: " + type + " | Saldo: " + balance + " | Proprietário: " + owner.getName());
    }
    public float getBalance() { return balance; }
    public void deposit(float amount) { this.balance += amount; }
    public void withdraw(float amount) { this.balance -= amount; }
}