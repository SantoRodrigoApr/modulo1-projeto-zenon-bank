package br.com.zenon;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Stream;

public class ReportMain {

    static void main() {
        Path path = new TransactionIngestor().getPaySimPath();

        StringBuilder result =  TransactionReport.generateTotalsReport(path);

        System.out.println(result);

    }

}
