package br.com.zenon;

import java.sql.*;
import java.util.Optional;

public class TransactionSQLRepository implements TransactionRepository {


    @Override
    public Optional<Transaction> findByOriginName(String customerName) {
        String sql = "SELECT * FROM Transactions WHERE nameOrigin = ?";
        try(

                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/paysim", "root", "123");
                PreparedStatement ps = connection.prepareStatement(sql);
                ) {
            ps.setString(1, customerName);
            ResultSet rs =  ps.executeQuery();
            if(rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("Step")
                        , Transaction.Type.valueOf(rs.getString("Type"))
                        , rs.getBigDecimal("Amount")
                        , rs.getString("nameOrigin")
                        , rs.getBigDecimal("oldBalanceOrig")
                        , rs.getBigDecimal("newBalanceOrig")
                        , rs.getString("nameDest")
                        , rs.getBigDecimal("oldBalanceDest")
                        , rs.getBigDecimal("newBalanceDest")
                        , rs.getInt("isFraud")
                        , rs.getInt("isFlaggedFraud")
                );
                return Optional.of(transaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO Transactions (Step, Type, Amount, nameOrigin, oldBalanceOrig, newBalanceOrig, nameDest, oldBalanceDest, newBalanceDest, isFraud, isFlaggedFraud)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/paysim", "root", "123");
        PreparedStatement ps = connection.prepareStatement(sql);
                ) {
            ps.setInt(1, transaction.step());
            ps.setString(2, transaction.type().name());
            ps.setBigDecimal(3, transaction.amount());
            ps.setString(4, transaction.nameOrigin());
            ps.setBigDecimal(5, transaction.oldBalanceOrig());
            ps.setBigDecimal(6, transaction.newBalanceOrig());
            ps.setString(7, transaction.nameDest());
            ps.setBigDecimal(8, transaction.oldBalanceDest());
            ps.setBigDecimal(9, transaction.newBalanceDest());
            ps.setInt(10, transaction.isFraud());
            ps.setInt(11, transaction.isFlaggedFraud());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
