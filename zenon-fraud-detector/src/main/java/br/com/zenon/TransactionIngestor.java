package br.com.zenon;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransactionIngestor {

    public List<Transaction> getTransactionList() {
        List<Transaction> transactions = new ArrayList<>();
        try (
        BufferedReader br = new BufferedReader(new java.io.FileReader(
                "C:\\Data\\Datasets\\Academic\\JavaElite\\JavaFundamentals\\PaySim-Fraud-Detection\\raw\\PS_20174392719_1491204439457_log.csv"))
        ) {
            for (String line: br.lines().skip(1).limit(1000).toList()) {
                transactions.add(parseTransaction(line));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return transactions;

    }


    private Transaction parseTransaction(String line) {
        String[] fields = line.split(",");
        return new Transaction(
                Integer.parseInt(fields[0])
                , Transaction.Type.valueOf(fields[1])
                , new BigDecimal(fields[2])
                , fields[3]
                , new BigDecimal(fields[4])
                , new BigDecimal(fields[5])
                , fields[6]
                , new BigDecimal(fields[7])
                , new BigDecimal(fields[8])
                , Integer.parseInt(fields[9])
                , Integer.parseInt(fields[10])

        );

    }



}