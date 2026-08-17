package br.com.zenon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;

public class DBMain {

    static void main() {

        TransactionIngestor transactionIngestor = new TransactionIngestor();
        List<Transaction> listTransaction =  transactionIngestor.getTransactionList(10000).orElseThrow(() -> new RuntimeException("Transaction list is empty"));
        listTransaction.forEach(System.out::println);

        TransactionSQLRepository transactionSQLRepository = new TransactionSQLRepository();

        Long initialTime = System.currentTimeMillis();

        listTransaction.forEach(transactionSQLRepository::addTransaction);

        Long endTime = System.currentTimeMillis();

        String message = "Insertion of 10000 Transactions into Database";

        try {
            Files.writeString(
                    Path.of("Benchmark.csv"),
                    LocalDateTime.now() + " - Execution time (ms): " + (endTime - initialTime) + " - " + message + "\n",
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }



    }

}
