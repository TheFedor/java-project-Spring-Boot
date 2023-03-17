package com.example.cursach.service;

import com.example.cursach.entity.TransactionType;
import com.example.cursach.exception.ParseCsvException;
import com.example.cursach.repository.TransactionTypeRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class TransactionTypeService {
    private final TransactionTypeRepository transactionTypeRepository;

    @Autowired
    public TransactionTypeService(TransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
    }

    public void saveTransactionType(MultipartFile transactionType) {
        transactionTypeRepository.deleteAll();
        transactionTypeRepository.saveAll(parseCsvFile(transactionType));
    }

    private List<TransactionType> parseCsvFile(MultipartFile transactionTypeFile) {
        List<TransactionType> transactionTypeList;
        try (Reader reader = new BufferedReader(new InputStreamReader(transactionTypeFile.getInputStream()))) {
            CsvToBeanBuilder builder = new CsvToBeanBuilder(reader).withType(TransactionType.class).withSeparator(';');

            transactionTypeList = builder.build().parse();


        } catch (RuntimeException | IOException e) {
            throw new ParseCsvException("Something went wrong, check format and try parse again" +
                    "\nThe reason: " + e.getMessage());
        }

        return transactionTypeList;
    }
}
