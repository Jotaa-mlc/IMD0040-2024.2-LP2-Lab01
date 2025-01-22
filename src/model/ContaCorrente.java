public class ContaCorrente extends Account{

    public ContaCorrente(int agencyId, int accountId, Client owner, AccountType type, float balance){
        super(agencyId, accountId, owner, type, balance);
    }

    public void taxar(ContaCorrente conta){
        conta.withdraw(15); // 15 reais de taxa
    }
}
