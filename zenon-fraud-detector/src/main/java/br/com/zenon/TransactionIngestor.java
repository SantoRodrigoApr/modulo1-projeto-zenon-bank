package br.com.zenon;

import javax.swing.text.html.HTMLDocument;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class TransactionIngestor {

    private final String PAY_SIM =
            "C:\\Data\\Datasets\\Academic\\JavaElite\\JavaFundamentals\\PaySim-Fraud-Detection\\raw\\PS_20174392719_1491204439457_log.csv";
    private final String PAY_SIM_BAD_DATA =
            "C:\\Data\\Datasets\\Academic\\JavaElite\\JavaFundamentals\\PaySim-Fraud-Detection\\raw\\paysim_with_bad_data.txt";

    public Optional<List<Transaction>> getTransactionList() {
        List<Transaction> transactions = new ArrayList<>();
        try (
            BufferedReader br = new BufferedReader(new java.io.FileReader(PAY_SIM))
        ) {
            for (String line: br.lines().skip(1).limit(100000).toList()) {
                try {
                    transactions.add(parseTransaction(line));
                } catch (RuntimeException e) {
                    System.err.println("Error: " + line + " >> " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error: File not found >> " + PAY_SIM);
        }
        return Optional.of(transactions);
    }

    public Path getPaySimPath() {
        return Path.of(PAY_SIM);
    }


    private Transaction parseTransaction(String line) {
        String[] fields = line.split(",");

        validateFields(fields);

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

    private void validateFields(String[] fields) {
        if (fields[0] == null || fields[0].isEmpty()) throw new InvalidTransactionException("Step cannot be null");
        if (fields[1] == null || fields[1].isEmpty()) throw new InvalidTransactionException("Type cannot be null");
        if (fields[2] == null || fields[2].isEmpty()) throw new InvalidTransactionException("Amount cannot be null");
        if (fields[3] == null || fields[3].isEmpty()) throw new InvalidTransactionException("NameOrigin cannot be null");
        if (fields[4] == null || fields[4].isEmpty()) throw new InvalidTransactionException("OldBalanceOrig cannot be null");
        if (fields[5] == null || fields[5].isEmpty()) throw new InvalidTransactionException("NewBalanceOrig cannot be null");
        if (fields[6] == null || fields[6].isEmpty()) throw new InvalidTransactionException("NameDest cannot be null");
        if (fields[7] == null || fields[7].isEmpty()) throw new InvalidTransactionException("OldBalanceDest cannot be null");
        if (fields[8] == null || fields[8].isEmpty()) throw new InvalidTransactionException("NewBalanceDest cannot be null");
        if (fields[9] == null || fields[9].isEmpty()) throw new InvalidTransactionException("isFraud cannot be null");
        if (fields[10] == null || fields[10].isEmpty()) throw new InvalidTransactionException("isFlaggedFraud cannot be null");

        List<String> fieldsList = Arrays.stream(Transaction.Type.values()).map(Enum::name).toList();
        if (!fieldsList.contains(fields[1])) throw new InvalidTransactionException("Invalid Type - Given value: " + fields[1]);

        if (Integer.parseInt(fields[0]) <= 0) throw new InvalidTransactionException("Step must be greater than zero - Given value: " + fields[0]);
        if (Double.parseDouble(fields[2]) < 0) throw new InvalidTransactionException("Amount must be greater than zero - Given value: " + fields[2]);
        if (Double.parseDouble(fields[4]) < 0) throw new InvalidTransactionException("OldBalanceOrig must be greater than zero - Given value: " + fields[4]);
        if (Double.parseDouble(fields[5]) < 0) throw new InvalidTransactionException("NewBalanceOrig must be greater than zero - Given value: " + fields[5]);
        if (Double.parseDouble(fields[7]) < 0) throw new InvalidTransactionException("OldBalanceDest must be greater than zero - Given value: " + fields[7]);
        if (Double.parseDouble(fields[8]) < 0) throw new InvalidTransactionException("NewBalanceDest must be greater than zero - Given value: " + fields[8]);

        if(!List.of("0", "1").contains(fields[9])) {
            throw new IllegalArgumentException("Fields isFraud must be 0 or 1 - Given value: " + fields[9]);
        }

        if(!List.of("0", "1").contains(fields[10])) {
            throw new IllegalArgumentException("Fields isFraud must be 0 or 1 - Given value: " + fields[10]);
        }
    }



}