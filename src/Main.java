import java.io.FileNotFoundException;

import UI.HomeIO;
import controller.Bank;
import controller.Loader;

public class Main {
    public static void main(String[] args) {
        try {
            Bank.setClients(Loader.loadClients());
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        try {
            Bank.setAgencies(Loader.loadAgencies());
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(-2);
        }
        
        HomeIO.printHome();
    }
}