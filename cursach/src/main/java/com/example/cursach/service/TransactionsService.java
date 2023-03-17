package com.example.cursach.service;

import com.example.cursach.entity.Transaction;
import com.example.cursach.exception.ParseCsvException;
import com.example.cursach.projection.TableProjection;
import com.example.cursach.repository.TransactionsRepository;
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
public class TransactionsService {
    private final TransactionsRepository transactionsRepository;

    @Autowired
    public TransactionsService(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public void saveTransaction(MultipartFile transactionFile) {
        transactionsRepository.deleteAll();

        List<Transaction> transactions = parseCsvFile(transactionFile);

        transactionsRepository.saveAll(transactions);
    }

    public List<TableProjection> getCountTableOfTransaction() {
        return transactionsRepository.getCountOfTransaction();
    }

    public List<TableProjection> getTop5Transaction(){

        return transactionsRepository.getTop5Transaction();
    }


    private List<Transaction> parseCsvFile(MultipartFile transactionFile) {
        List<Transaction> transactionList;
        try (Reader reader = new BufferedReader(new InputStreamReader(transactionFile.getInputStream()))) {
            CsvToBeanBuilder builder = new CsvToBeanBuilder(reader).withType(Transaction.class).withSeparator(',');

            transactionList = builder.build().parse();


        } catch (RuntimeException | IOException e) {
            throw new ParseCsvException("Something went wrong, check format and try parse again" +
                    "\nThe reason: " + e.getMessage());
        }

        return transactionList;
    }
}
