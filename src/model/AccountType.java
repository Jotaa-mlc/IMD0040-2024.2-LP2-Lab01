package model;

public enum AccountType {
    CURRENT, SAVINGS, SALARY;

    public static AccountType fromInteger(int i) {
        AccountType type = null;
        switch (i) {
            case 0:
                type = CURRENT;
                break;
            case 1:
                type = CURRENT;
                break;
            case 2:
                type = CURRENT;
                break;
            default:
                break;
        }
        return type;
    }

    public static String toString(AccountType type) {
        switch (type) {
            case CURRENT:
                return "Corrente";
            case SAVINGS:
                return "Poupança";
            case SALARY:
                return "Salário";
            default:
                return null;
        }
    }
}