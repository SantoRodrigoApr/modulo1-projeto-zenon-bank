package br.com.zenon;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FraudAnalyzer {
    private final List<Transaction> transactionList =
            new TransactionIngestor().getTransactionList().orElseThrow(() -> new RuntimeException("Transaction list is empty"));

    public List<Transaction> getFraudulentTransactions () {
        return transactionList.stream()
                .filter(t -> t.isFraud() == 1)
                .toList();
    }

    public List<Transaction> getThreeHighestFraudulentTransactions () {
        return getFraudulentTransactions().stream()
                .sorted(Comparator.comparing(Transaction::amount))
                .toList()
                .reversed()
                .subList(0, 3);
    }

    public List<Map.Entry<String, BigDecimal>> getFiveHighestSuspiciousCustomers() {
        return getFraudulentTransactions().stream()
                .collect(Collectors.groupingBy(
                    Transaction::nameOrigin,
                    Collectors.reducing(
                            BigDecimal.ZERO,
                            Transaction::amount,
                            BigDecimal::add

                    )
                )).entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .toList().subList(0, 5);
    }

    public BigDecimal getTotalLossOnFrauds() {
        return getFraudulentTransactions().stream()
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Map.Entry<Transaction.Type, Long>> getFraudPerTransacionType() {
        return getFraudulentTransactions().stream()
                .collect(Collectors.groupingBy(Transaction::type,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .toList();


    }

}
