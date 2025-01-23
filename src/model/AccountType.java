package model;

public enum AccountType {
    Corrente, Poupança, Salário;

    public static AccountType fromInteger(int i) {
        AccountType type = null;
        switch (i) {
            case 0:
                type = Corrente;
                break;
            case 1:
                type = Poupança;
                break;
            case 2:
                type = Salário;
                break;
            default:
                break;
        }
        return type;
    }
}