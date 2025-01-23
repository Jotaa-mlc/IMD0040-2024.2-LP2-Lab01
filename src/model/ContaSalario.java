package model;

public class ContaSalario extends Account{
    
    private String cpf_empregador;
    private int saques_efetuados;

    public ContaSalario(int agencyId, int accountId, Client owner, AccountType type, float balance, String CPFempregador){
        super(agencyId, accountId, owner, type, balance);
        this.saques_efetuados = 0;
        this.cpf_empregador = CPFempregador;
    }
    public static ContaSalario fromAccount(Account ac, String cpf_empregador) {
        return new ContaSalario(ac.agencyId, ac.accountId, ac.owner, ac.type, ac.balance, cpf_empregador);
    }
    public String getCpfEmpregador() { return cpf_empregador; }
    public void withdraw(float amount) { 
        if (saques_efetuados > 5) { throw new IllegalStateException("Maximo de saques já efetuados."); }
        this.balance -= amount; 
    }
    @Override
    public String toString() {
        //AgencyId; AccountId; Type; OwnerCPF; Balance
        return agencyId + ";" + accountId + ";" + type.ordinal() + ";" + owner.getCPF() + ";" + balance + ";" + cpf_empregador;
    }
    public void taxar() {
        saques_efetuados = 0;
    }
}