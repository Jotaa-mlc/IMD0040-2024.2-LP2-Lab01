package model;
public class Account {
    private final int agencyId;
    private final int accountId;
    private final Client owner;
    private final AccountType type;
    private float balance;

    public Account(int agencyId, int accountId, Client owner, AccountType type, float balance) {
        this.agencyId = agencyId;
        this.accountId = accountId;
        this.owner = owner;
        this.type = type;
        this.balance = balance;
    }
    public int getAgencyId() {
        return agencyId;
    }
    public int getAccountId() {
        return accountId;
    }
    public String getOwnerName() {
        return owner.getName();
    }
    public String getOwnerCPF() {
        return owner.getCPF();
    }
    public boolean authenticate(Client auth) {
        return this.owner.equals(auth);
    }
    @Override
    public String toString() {
        //AgencyId; AccountId; Type; OwnerCPF; Balance
        return agencyId + ";" + accountId + ";" + type + ";" + owner.getCPF() + ";" + balance;
    }
    public void print(){
        //System.out.println("Conta ID: " + id + " | Tipo: " + type + String.format(" | Saldo: R$ %.2f", balance) + " | Proprietário: " + owner.getName());
        System.out.println("Conta ID: " + accountId + String.format(" | Saldo: R$ %.2f", balance));
    }
    public float getBalance() { return balance; }
    public void deposit(float amount) { this.balance += amount; }
    public void withdraw(float amount) { this.balance -= amount; }
}