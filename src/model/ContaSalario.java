package model;

import controller.Loader;

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
    @Override public void withdraw(float amount) { 
        if (saques_efetuados > 5) { throw new IllegalStateException("Maximo de saques já efetuados."); }
        this.balance -= amount; 
        saques_efetuados++;
        try {
            Loader.saveAccount(this);
        } catch (Exception e) {
            System.err.println("Não foi possível salvar a alteração no DB da Agência");
            this.balance += amount;
            saques_efetuados--;
        }
    }
    @Override public String toString() {
        return accountId + ";" + type.ordinal() + ";" + owner.getCPF() + ";" + balance + ";" + cpf_empregador;
    }
    @Override public void taxar() {
        this.saques_efetuados = 0;
        Loader.log(String.format("Restauração de saques - Ag: %1$d Cc: %2$d", this.getAgencyId(), this.getAccountId()));
    }
}