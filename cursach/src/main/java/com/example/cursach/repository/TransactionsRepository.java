package com.example.cursach.repository;

import com.example.cursach.entity.Transaction;
import com.example.cursach.projection.TableProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {
    @Query(nativeQuery = true, value = "" +
            "SELECT tr2.trType, transaction_type.description, tr2.count " +
            "FROM transaction_type " +
            "         INNER JOIN (SELECT tr_type_code as trType, count(tr_type_code) as count " +
            "                    FROM transaction as tr " +
            "                    group by tr_type_code) as tr2 ON transaction_type.type_code = tr2.trType" +
            ""
    )
    List<TableProjection> getCountOfTransaction();

    @Query(nativeQuery = true, value = "" +
            "SELECT tr2.trType, transaction_type.description, tr2.count " +
            "FROM transaction_type " +
            "         INNER JOIN (SELECT tr_type_code as trType, count(tr_type_code) as count " +
            "                    FROM transaction as tr " +
            "                    group by tr_type_code) as tr2 ON transaction_type.type_code = tr2.trType " +
            "ORDER BY tr2.count desc " +
            "LIMIT 5" +
            ""
    )
    List<TableProjection> getTop5Transaction();
}
