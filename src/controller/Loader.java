package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import model.Account;
import model.AccountType;
import model.Client;

public class Loader {
    private static final String agencyFileExtension = ".agency.csv";
    private static final String agencyFolderPath = "\\data\\agencies";
    private static final String clientsFilePath = "\\data\\clients.csv";
    private static final String TransacaoFilePath = "\\data\\transacao.log"; // novo arquivo com as tranfrencias
    private static final String separator = ";";

    public static HashMap<Integer, Agency> loadAgencies() throws FileNotFoundException {
        HashMap<Integer, Agency> agencies = new HashMap<>();

        List<String> fileNames = new ArrayList<>();
        File[] files = new File(agencyFolderPath).listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(agencyFileExtension)) {
                fileNames.add(file.getName());
            }
        }
        if (fileNames.isEmpty()) {
            throw new FileNotFoundException("Não foi possível localizar os DBs das agências.");
        }

        for (String fileName : fileNames) {
            File file = new File(agencyFolderPath + fileName);
            try {
                Agency ag = loadAgency(file);
                agencies.put(ag.getId(), ag);
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }
        return agencies;
    }
    private static Agency loadAgency(File file) throws FileNotFoundException{
        try {
            int agencyId = Integer.parseInt(file.getName().substring(0, file.getName().indexOf('.', 0)));
            Agency ag = new Agency(agencyId);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String buffer = sc.nextLine();
                String[] data = buffer.split(separator); //AccountId;Type;OwnerCPF;Balance

                int accountId = Integer.parseInt(data[0]);
                AccountType type = AccountType.fromInteger(Integer.parseInt(data[1]));
                String ownerCPF = data[2];
                float balance = Float.parseFloat(data[3]);

                Client cl = Bank.getClientByCPF(ownerCPF);

                Account ac = new Account(agencyId, accountId, cl, type, balance);
                ag.addAccount(ac);
            }
            sc.close();
            return ag;
        } catch (Exception e) {
            throw new FileNotFoundException("Não foi possível carregar o DB da agência: " + file.getName());
        }
    }
    public static HashMap<String, Client> loadClients() throws FileNotFoundException {
        HashMap<String, Client> clients = new HashMap<>();
        File file = new File(clientsFilePath);
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String buffer = sc.nextLine();
                String[] data = buffer.split(separator);//Name;CPF;Password
    
                Client cl = new Client(data[0], data[1], data[2]);
                clients.put(cl.getCPF(), cl);
            }
            
            sc.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Não foi possivel carregar o DB de clientes.");
        }
        return clients;
    }
    public static void saveAccount(Account account) throws IOException {
        try {
            save2file(agencyFolderPath + account.getAgencyId() + agencyFileExtension, account.toString());
        } catch (IOException e) {
            throw new IOException("Não foi possível salvar a alteração no DB da agência " + account.getAgencyId());
        }
    }

    public static void saveClient(Client client) throws IOException {
        try {
            save2file(clientsFilePath, client.toString());
        }
        catch (IOException e) {
            throw new IOException("Não foi possível salvar a alteração no DB de clientes.");
        }
    }

    private static void save2file(String filePath, String msg) throws IOException {
        FileWriter fw = new FileWriter(filePath, true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.append(msg);
        bw.newLine();
        bw.close();
    }

    public static void Log(String msg) throws IOException { // para as transacoes
        FileWriter fw = new FileWriter(TransacaoFilePath, true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.append(msg);
        bw.newLine();
        bw.close();
    }
}     