package br.com.zenon;

import java.util.List;
import java.util.Optional;

public class TransactionListRepository implements TransactionRepository {

    @Override
    public Optional<Transaction> findByOriginName(String customerName) {
        return new TransactionIngestor()
                .getTransactionList().orElseThrow(() -> new RuntimeException("Transaction list is empty"))
                .stream()
                .filter(t -> t.nameOrigin().equals(customerName))
                .findAny();
    }

    @Override
    public void addTransaction(Transaction transaction) {

    }


}
