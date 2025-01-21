package model;
public class Account {
    private final int id;
    private final Client owner;
    private final AccountType type;
    private float balance;

    public Account(int id, Client owner, AccountType type, float balance) {
        this.id = id;
        this.owner = owner;
        this.type = type;
        this.balance = balance;
    }
    public int getId() {
        return id;
    }
    public String getOwnerName() {
        return owner.getName();
    }
    public boolean authenticate(Client auth) {
        return this.owner.equals(auth);
    }
    @Override
    public String toString() {
        //AccountId; Type; OwnerCPF; Balance
        return id + ";" + type + ";" + owner.getCPF() + ";" + balance;
    }
    public void print(){
        //System.out.println("Conta ID: " + id + " | Tipo: " + type + String.format(" | Saldo: R$ %.2f", balance) + " | Proprietário: " + owner.getName());
        System.out.println("Conta ID: " + id + String.format(" | Saldo: R$ %.2f", balance));
    }
    public float getBalance() { return balance; }
    public void deposit(float amount) { this.balance += amount; }
    public void withdraw(float amount) { this.balance -= amount; }
}