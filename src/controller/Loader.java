package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import model.Account;
import model.AccountType;
import model.Agency;
import model.Client;

public class Loader {
    private String agencyFileExtension = ".agency.csv";
    private String separator = ";";

    public HashMap<Integer, Agency> loadAgencies(String directoryPath) {
        HashMap<Integer, Agency> agencies = new HashMap<>();
        List<String> fileNames = new ArrayList<>();
        File[] files = new File(directoryPath).listFiles();

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(agencyFileExtension)) {
                fileNames.add(file.getName());
            }
        }

        for (String fileName : fileNames) {
            File file = new File(directoryPath + fileName);
            try {
                Agency ag = loadAgency(file);
                agencies.put(ag.getId(), ag);
            } catch (Exception e) {
                System.err.println("Erro ao carregar arquivo: " + file.getName());
            }
        }

        return agencies;
    }
    private Agency loadAgency(File csvFile) throws FileNotFoundException {
        int agencyId = Integer.parseInt(csvFile.getName().substring(0, csvFile.getName().indexOf('.', 0))); //Guarda o nome do arquivo sem extensão i.e. o ID da Agencia

        Scanner sc = new Scanner(csvFile);
        int agAccountId = sc.nextInt();

        Agency ag = new Agency(agencyId, agAccountId);

        while (sc.hasNextLine()) {
            String buffer = sc.nextLine();
            String[] data = buffer.split(separator); //AccountId;Type;OwnerCPF;Balance

            int accountId = Integer.parseInt(data[0]);
            AccountType type = AccountType.fromInteger(Integer.parseInt(data[1]));
            String ownerCPF = data[2];
            float balance = Float.parseFloat(data[3]);

            Client cl = Bank.getClientByCPF(ownerCPF);

            Account ac = new Account(accountId, cl, type, balance);
            ag.addAccount(ac);
        }

        sc.close();
        return ag;
    }

    public HashMap<String, Client> loadClients(String filePath) throws FileNotFoundException {
        HashMap<String, Client> clients = new HashMap<>();

        File file = new File(filePath);
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

    
}
