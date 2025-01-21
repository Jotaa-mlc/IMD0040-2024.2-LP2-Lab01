package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import model.Account;
import model.AccountType;
import model.Client;

public class Loader {
    private static final String agenciesFilePath = "\\data\\agency.csv";
    private static final String clientsFilePath = "\\data\\clients.csv";
    private static final String separator = ";";

    public static HashMap<Integer, Agency> loadAgencies() {
        HashMap<Integer, Agency> agencies = new HashMap<>();
        File file = new File(agenciesFilePath);
        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String buffer = sc.nextLine();
                String[] data = buffer.split(separator); //AgencyId;AccountId;Type;OwnerCPF;Balance
    
                int agencyId = Integer.parseInt(data[0]);
                int accountId = Integer.parseInt(data[1]);
                AccountType type = AccountType.fromInteger(Integer.parseInt(data[2]));
                String ownerCPF = data[3];
                float balance = Float.parseFloat(data[4]);
    
                Client cl = Bank.getClientByCPF(ownerCPF);
    
                Account ac = new Account(agencyId, accountId, cl, type, balance);
                Agency ag = agencies.get(ac.getAgencyId());
                if (ag != null) {
                    ag.addAccount(ac);
                }
                else {
                    Agency newAgency = new Agency(ac.getAgencyId());
                    newAgency.addAccount(ac);
                    agencies.put(newAgency.getId(), newAgency);
                }
            }

            sc.close();
        } catch (FileNotFoundException e) {
            System.err.println("Não foi possível carregar as Agências.");
            return null;
        }

        return agencies;
    }
    public static HashMap<String, Client> loadClients() throws FileNotFoundException {
        HashMap<String, Client> clients = new HashMap<>();
        File file = new File(clientsFilePath);
        Scanner sc = new Scanner(file);
        
        while (sc.hasNextLine()) {
            String buffer = sc.nextLine();
            String[] data = buffer.split(separator);//Name;CPF;Password

            Client cl = new Client(data[0], data[1], data[2]);
            clients.put(cl.getCPF(), cl);
        }
        
        sc.close();
        return clients;
    }
    public static void saveAccount(Account account) throws IOException {
        save2file(agenciesFilePath, account.toString());
    }
    public static void saveClient(Client client) throws IOException {
        save2file(clientsFilePath, client.toString());
    }

    private static void save2file(String filePath, String msg) throws IOException {
        FileWriter fw = new FileWriter(filePath, true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.append(msg);
        bw.newLine();
        bw.close();
    }
}