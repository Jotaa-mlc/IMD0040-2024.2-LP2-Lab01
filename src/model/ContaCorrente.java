package model;

import controller.Loader;

public class ContaCorrente extends Account{

    public ContaCorrente(int agencyId, int accountId, Client owner, AccountType type, float balance){
        super(agencyId, accountId, owner, type, balance);
    }
    @Override public void taxar(){
        float taxa = 15;
        this.withdraw(taxa);// 15 reais de taxa
        Loader.log(String.format("Taxa de administração - Ag: %1$d Cc: %2$d - Valor R$ %3$,.2f", this.getAgencyId(), this.getAccountId(), taxa)); 
    }
}
