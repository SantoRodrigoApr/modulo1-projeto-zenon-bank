package br.com.zenon;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Stream;

public class TransactionReport {

    public static StringBuilder generateTotalsReport(Path path) {
        try (Stream<String> lines = Files.lines(path).skip(1)) {
            Iterator<String> fileLines = lines.iterator();
            int lineCount = 0;
            int fraudCount = 0;
            BigDecimal totalAmount = BigDecimal.ZERO;
            while(fileLines.hasNext()) {
                lineCount += 1;
                String[] fields = fileLines.next().split(",");
                fraudCount += fields[9].equals("1") ? 1 : 0;
                totalAmount = totalAmount.add(new BigDecimal(fields[2]));
            }

            return new StringBuilder()
                    .append("Total of transactions: ")
                    .append(lineCount).append("\n")
                    .append("Total of frauds: ")
                    .append(fraudCount).append("\n")
                    .append("Total of amount: ")
                    .append(totalAmount);
        } catch (IOException e) {
            e.printStackTrace();
            return new StringBuilder()
                    .append("Error: File not found >> ")
                    .append(path);
        }
    }
}
