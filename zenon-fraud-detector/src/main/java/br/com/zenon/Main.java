package br.com.zenon;

import java.math.BigDecimal;

public class Main {
    static void main() {

        taskSixMethodA();
        taskSixMethodB();

    }
    private static TransactionRepository transactionRepository;

    private static void taskSixMethodB() {
        transactionRepository = new TransactionMapRepository();
        System.out.println();
        long beginningTime = System.nanoTime();
        transactionRepository.getTransactionByCustomerName("C1868032458")
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
        transactionRepository.getTransactionByCustomerName("C12345")
                .ifPresentOrElse(
                    System.out::print,
                    () -> System.out.println("Transaction not found for custumner C12345")
                );
        long beginningTime = System.nanoTime();
        transactionRepository.getTransactionByCustomerName("C1868032458")
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
