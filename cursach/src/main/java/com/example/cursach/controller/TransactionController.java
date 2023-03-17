package com.example.cursach.controller;

import com.example.cursach.projection.TableProjection;
import com.example.cursach.service.TransactionTypeService;
import com.example.cursach.service.TransactionsService;
import com.example.cursach.service.WriteToCSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/")
public class TransactionController {
    private final TransactionsService transactionsService;
    private final TransactionTypeService transactionTypeService;
    private final WriteToCSVService writeToCSVService;

    @Autowired
    public TransactionController(
            TransactionsService transactionsService,
            TransactionTypeService transactionTypeService,
            WriteToCSVService writeToCSVService
    ) {
        this.transactionsService = transactionsService;
        this.transactionTypeService = transactionTypeService;
        this.writeToCSVService = writeToCSVService;
    }

    @GetMapping()
    public String greeting() {

        return "index";
    }

    @PostMapping("upload")
    public ModelAndView saveFiles(@RequestParam("transaction") MultipartFile transaction,
                                  @RequestParam("transactionType") MultipartFile transactionType) {
        transactionsService.saveTransaction(transaction);
        transactionTypeService.saveTransactionType(transactionType);

        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.addObject("transactions", transactionsService.getCountTableOfTransaction());

        modelAndView.addObject("topTransactions", transactionsService.getTop5Transaction());

        return modelAndView;
    }

    @GetMapping("/download/countOfTransaction")
    public ResponseEntity<InputStreamResource> getCountsFile() throws FileNotFoundException {
        List<TableProjection> countTableOfTransaction = transactionsService.getCountTableOfTransaction();

        File file = new File(writeToCSVService.write(countTableOfTransaction));
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);
    }

    @GetMapping("/download/top5File")
    public ResponseEntity<InputStreamResource> getTop5File() throws FileNotFoundException {
        List<TableProjection> top5Transaction = transactionsService.getTop5Transaction();

        File file = new File(writeToCSVService.write(top5Transaction));
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);
    }

}
