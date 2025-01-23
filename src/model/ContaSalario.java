package model;

public class ContaSalario extends Account{
    
    private String cpf_empregador;
    private int saques_efetuados;

    public ContaSalario(int agencyId, int accountId, Client owner, AccountType type, float balance, String CPFempregador){
        super(agencyId, accountId, owner, type, balance);
        this.saques_efetuados = 0;
        this.cpf_empregador = CPFempregador;
    }

    public void IncrementarSaque(){
        this.saques_efetuados++;
    }
    
    public Boolean PodeDepositar(String CPF){
        return (CPF == this.cpf_empregador);
    }

    public Boolean PodeSacar(){
        return (saques_efetuados < 10);
    }
}