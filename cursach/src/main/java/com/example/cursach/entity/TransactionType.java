package com.example.cursach.entity;

import com.opencsv.bean.CsvBindByName;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CsvBindByName(column = "tr_type")
    private Long TypeCode;

    @CsvBindByName(column = "tr_description")
    private String description;

    public Long getId() {
        return id;
    }

    public TransactionType setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getTypeCode() {
        return TypeCode;
    }

    public TransactionType setTypeCode(Long typeCode) {
        TypeCode = typeCode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TransactionType setDescription(String description) {
        this.description = description;
        return this;
    }
}
