package br.com.zenon;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionMapRepository implements TransactionRepository {

    @Override
    public Optional<Transaction> getTransactionByCustomerName(String customerName) {
        Map<String, Transaction> transactionMap = new TransactionIngestor()
                .getTransactionList().orElseThrow(() -> new RuntimeException("Transaction list is empty"))
                .stream()
                .collect(Collectors.toMap(
                        Transaction::nameOrigin,
                        transaction -> transaction
                ));
        return Optional.of(transactionMap.get(customerName));
    }
}
