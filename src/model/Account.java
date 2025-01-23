package model;
public class Account {
    protected final int agencyId;
    protected final int accountId;
    protected final Client owner;
    protected final AccountType type;
    protected float balance;

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
    public AccountType getAccountType(){
        return this.type;
    }
    public boolean authenticate(Client auth) {
        return this.owner.equals(auth);
    }
    @Override
    public String toString() {
        //AgencyId; AccountId; Type; OwnerCPF; Balance
        return agencyId + ";" + accountId + ";" + type.ordinal() + ";" + owner.getCPF() + ";" + balance;
    }
    public String print(){
        return String.format("ID Agência: %1$4d | ID Conta: %2$4d | Tipo: %3s | Saldo: R$ %6,.2f", agencyId, accountId, type, balance);
    }
    public float getBalance() { return balance; }
    public void deposit(float amount) { 
        this.balance += amount; 
    }
    public void withdraw(float amount) { 
        this.balance -= amount; 
    }
    public void taxar() {}
}