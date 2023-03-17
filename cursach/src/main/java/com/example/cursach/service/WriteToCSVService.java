package com.example.cursach.service;

import com.example.cursach.projection.TableProjection;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class WriteToCSVService {
    private final String transactionPathCSV = "transactionFile.csv";

    public String write(List<TableProjection> tableProjectionList) {
        File file = new File(transactionPathCSV);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(String.format("%s,%s,%s\n", "transactionType", "description", "count"));
            for (TableProjection tableProjection : tableProjectionList) {
                writer.write(
                        String.format("%s,%s,%s\n",
                                tableProjection.getTrType(),
                                tableProjection.getDescription(),
                                tableProjection.getCount())
                );
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file.getAbsolutePath();
    }
}
