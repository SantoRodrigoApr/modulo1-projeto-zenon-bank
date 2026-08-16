package br.com.zenon;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class TransactionReport {

    public static StringBuilder generateTotalsReport(Path path, Language language) {
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

            Locale localeUS = Locale.US;
            Locale localeBR = Locale.of("pt", "BR");

            ResourceBundle messageUS = ResourceBundle.getBundle("mensagens", localeUS);
            ResourceBundle messageBR = ResourceBundle.getBundle("mensagens", localeBR);

            if (language == Language.EN_US) {
                return new StringBuilder()
                        .append(messageUS.getString("report.total.transactions"))
                        .append(lineCount).append("\n")
                        .append(messageUS.getString("report.total.frauds"))
                        .append(fraudCount).append("\n")
                        .append(messageUS.getString("report.total.amount"))
                        .append(NumberFormat.getCurrencyInstance(localeUS).format(totalAmount));
            } else if(language == Language.PT_BR) {
                return new StringBuilder()
                        .append(messageBR.getString("report.total.transactions"))
                        .append(lineCount).append("\n")
                        .append(messageBR.getString("report.total.frauds"))
                        .append(fraudCount).append("\n")
                        .append(messageBR.getString("report.total.amount"))
                        .append(NumberFormat.getCurrencyInstance(localeBR).format(totalAmount));
            }



        } catch (IOException e) {
            e.printStackTrace();
            return new StringBuilder()
                    .append("Error: File not found >> ")
                    .append(path);
        }
        return null;
    }
}
