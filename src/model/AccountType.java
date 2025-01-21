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
}