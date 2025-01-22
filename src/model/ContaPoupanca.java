public class ContaPoupanca extends Account{

    public ContaPoupanca(int agencyId, int accountId, Client owner, AccountType type, float balance){
        super(agencyId, accountId, owner, type, balance);
    }

    public void taxar(ContaPoupanca conta){
        conta.deposit(conta.getBalance() * 0.009); // taxa de 0,9%
    }
}