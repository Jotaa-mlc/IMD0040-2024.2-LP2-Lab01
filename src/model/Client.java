package model;

public class Client {
    private final String Name;
    private final String CPF;
    private final String Password;

    public String getName() {
        return Name;
    }

    public String getCPF() {
        return CPF;
    }

    public boolean authenticate(String Password) {
        return this.Password.equals(Password);
    }

    public Client(String name, String CPF, String Password) {
        this.Name = name;
        this.CPF = CPF;
        this.Password = Password;
    }
}
