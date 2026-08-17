package br.com.zenon;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.NumberFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    static void main() {

        Long initialTime = System.currentTimeMillis();

        String header =
                "step" + ", type" + ", amount" + ", nameOrigin" +
                ", oldBalanceOrig" + ", newBalanceOrig" + ", nameDest" +
                ", oldBalanceDest" + ", newBalanceDest" + ", isFraud" + ", isFlaggedFraud\n";


        TransactionSQLRepository transactionSQLRepository = new TransactionSQLRepository();
        Transaction transaction = transactionSQLRepository.findByOriginName("C1231006815")
                .orElseThrow(() -> new RuntimeException("Transaction not found for custumner C1231006815"));
        System.out.println(transaction);

        try  {
            Path file = Path.of("Test.xlsx");
            Files.writeString(
                    file,
                    header + transaction.toString() + "\n",
                    StandardOpenOption.APPEND
//                    StandardOpenOption.TRUNCATE_EXISTING
            );

        } catch (IOException ioe) {
            System.err.println("Error: " + ioe.getMessage());
        }

        Long endTime = System.currentTimeMillis();
        System.out.println("Execution time (ms): " + (endTime - initialTime));

    }
    private static TransactionRepository transactionRepository;

    private static void taskEightMethod() {
        TransactionIngestor transactionIngestor = new TransactionIngestor();
        List<Transaction> listTransaction = transactionIngestor.getTransactionList()
                .orElseThrow(() -> new RuntimeException("Transaction list is empty"));
        listTransaction
                .stream()
                .map(Transaction::nameOrigin)
                .map(String::length)
                .max(Comparator.comparing(Integer::valueOf
                )).ifPresent(m -> System.out.println("Max: " + m));
    }

    private static void taskSevenMethod() {

        System.out.println(Locale.getDefault());
        System.out.println();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL);
        Locale localeUS = Locale.US;
        Locale localeBR = Locale.of("pt", "BR");

        System.out.println(formatter.format(ZonedDateTime.now()));
        System.out.println(formatter.withLocale(localeUS).format(ZonedDateTime.now()));
        System.out.println(formatter.withLocale(localeBR).format(ZonedDateTime.now()));

        System.out.println(NumberFormat.getCurrencyInstance().format(123456.78));
        System.out.println(NumberFormat.getCurrencyInstance(localeUS).format(123456.78));
        System.out.println(NumberFormat.getCurrencyInstance(localeBR).format(123456.78));

        System.out.println("\n > > > Resource Bundle\n");

        ResourceBundle message = ResourceBundle.getBundle("mensagens");
        ResourceBundle messageBR = ResourceBundle.getBundle("mensagens", localeBR);
        ResourceBundle messageUS = ResourceBundle.getBundle("mensagens", localeUS);

        System.out.println(message.getString("report.total.transactions"));
        System.out.println(messageBR.getString("report.total.transactions"));
        System.out.println(messageUS.getString("report.total.transactions"));

    }

    private static void taskSixMethodB() {
        transactionRepository = new TransactionMapRepository();
        System.out.println();
        long beginningTime = System.nanoTime();
        transactionRepository.findByOriginName("C1868032458")
                .ifPresentOrElse(
                        System.out::print,
                        () -> System.out.println("Transaction not found for custumner C1868032458")
                );
        long finalTime = System.nanoTime();
        System.out.println();
        System.out.println("Map Execution Time (ms): " + ((finalTime - beginningTime) / 1000000));
    }

    private static void taskSixMethodA() {
        transactionRepository = new TransactionListRepository();
        System.out.println();
        transactionRepository.findByOriginName("C12345")
                .ifPresentOrElse(
                    System.out::print,
                    () -> System.out.println("Transaction not found for custumner C12345")
                );
        long beginningTime = System.nanoTime();
        transactionRepository.findByOriginName("C1868032458")
                .ifPresentOrElse(
                    System.out::print,
                    () -> System.out.println("Transaction not found for custumner C1868032458")
//                    () -> System.out.println("Transaction not found for custumner C1577304395")
                );
        long finalTime = System.nanoTime();
        System.out.println();
        System.out.println("List Execution Time (ms): " + ((finalTime - beginningTime) / 1000000));
    }

    private static void taskFiveMethod() {
        FraudAnalyzer fraudAnalyzer = new FraudAnalyzer();
        System.out.print("\nTotal of fraudulent transactions: ");
        System.out.println(fraudAnalyzer.getFraudulentTransactions().size());
        fraudAnalyzer.getFraudulentTransactions().forEach(t ->  System.out.println(" - " + t));

        System.out.println("\nThree highest fraudulent transactions:");
        fraudAnalyzer.getThreeHighestFraudulentTransactions().forEach(t ->  System.out.println(" - " + t));

        System.out.println("\nFive highest fraudulent customers:");
        fraudAnalyzer.getFiveHighestSuspiciousCustomers().forEach(t ->  System.out.println(" - " + t));

        System.out.print("\nTotal loss on frauds: ");
        System.out.println(fraudAnalyzer.getTotalLossOnFrauds());

        System.out.println("\nTotal fraud per transaction type:");
        fraudAnalyzer.getFraudPerTransacionType().forEach(t ->  System.out.println(" - " + t));
    }


    private void taskOneMethod () {
        Transaction t1 = new Transaction(
                1, Transaction.Type.PAYMENT, new BigDecimal("9839.64"), "C1231006815"
                , new BigDecimal("170136.0"), new BigDecimal("160296.36"), "M1979787155"
                , new BigDecimal("0.0"), new BigDecimal("0.0"), 0, 0
        );

        Transaction t2 = new Transaction(
                743, Transaction.Type.CASH_OUT, new BigDecimal("850002.52"), "C1280323807"
                , new BigDecimal("850002.52"), new BigDecimal("0.0"), "C873221189"
                , new BigDecimal("6510099.11"), new BigDecimal("7360101.63"), 1, 0
        );

        System.out.println(t1);
        System.out.println(t2);
    }

}
