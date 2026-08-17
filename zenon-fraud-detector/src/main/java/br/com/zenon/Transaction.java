package br.com.zenon;

import java.math.BigDecimal;

public record Transaction(
        Integer step, Type type, BigDecimal amount, String nameOrigin
        , BigDecimal oldBalanceOrig, BigDecimal newBalanceOrig, String nameDest
        , BigDecimal oldBalanceDest, BigDecimal newBalanceDest, Integer isFraud, Integer isFlaggedFraud
) {

    public enum Type {
        CASH_IN, CASH_OUT, DEBIT, PAYMENT, TRANSFER
    }

    @Override
    public String toString() {
        return step +
                ", " + type +
                ", " + amount +
                ", " + nameOrigin +
                ", " + oldBalanceOrig +
                ", " + newBalanceOrig +
                ", " + nameDest +
                ", " + oldBalanceDest +
                ", " + newBalanceDest +
                ", " + isFraud +
                ", " + isFlaggedFraud;
    }
}
