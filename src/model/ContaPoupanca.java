package model;

import controller.Loader;

public class ContaPoupanca extends Account{

    public ContaPoupanca(int agencyId, int accountId, Client owner, AccountType type, float balance){
        super(agencyId, accountId, owner, type, balance);
    }
    @Override public void taxar(){
        float profit = this.balance * 0.009f;
        this.deposit(profit); // taxa de 0,9%
        Loader.log(String.format("Rendimento Poupança - Ag: %1$d Cc: %2$d - Valor R$ %3$,.2f", this.getAgencyId(), this.getAccountId(), profit));
    }
}