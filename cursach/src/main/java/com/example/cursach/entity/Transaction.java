package com.example.cursach.entity;

import com.opencsv.bean.CsvBindByName;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CsvBindByName(column = "customer_id")
    private Long customerId;

    @CsvBindByName(column = "tr_datetime")
    private String trDatetime;

    @CsvBindByName(column = "mcc_code")
    private Long mccCode;

    @CsvBindByName(column = "tr_type")
    private Long trTypeCode;

    @CsvBindByName(column = "amount")
    private Double amount;

    @CsvBindByName(column = "term_id")
    private String terminalId;

    public Long getId() {
        return id;
    }

    public Transaction setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Transaction setCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getTrDatetime() {
        return trDatetime;
    }

    public Transaction setTrDatetime(String trDatetime) {
        this.trDatetime = trDatetime;
        return this;
    }

    public Long getMccCode() {
        return mccCode;
    }

    public Transaction setMccCode(Long mccCode) {
        this.mccCode = mccCode;
        return this;
    }

    public Long getTrTypeCode() {
        return trTypeCode;
    }

    public Transaction setTrTypeCode(Long trTypeCode) {
        this.trTypeCode = trTypeCode;
        return this;
    }

    public Double getAmount() {
        return amount;
    }

    public Transaction setAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public Transaction setTerminalId(String terminalId) {
        this.terminalId = terminalId;
        return this;
    }
}
