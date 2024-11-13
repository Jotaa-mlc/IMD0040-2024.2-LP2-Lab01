package model;

public class Client {
    private final String Name;
    private final String CPF;
    private final String Password;

//    public static boolean matchClient(Client a, Client b) {
//        boolean match;
//        match = a.CPF.equals(b.CPF);
//        match = a.Password.equals(b.Password);
//        match = a.Name.equals(b.Name);
//        return match;
//    }
    public Client(String name, String CPF, String Password) {
        this.Name = name;
        this.CPF = CPF;
        this.Password = Password;
    }
    public String getName() {
        return Name;
    }
    public String getCPF() {
        return CPF;
    }
    public boolean authenticate(String Password) {
        return this.Password.equals(Password);
    }
}
