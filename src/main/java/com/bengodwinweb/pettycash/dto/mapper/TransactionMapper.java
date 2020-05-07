package com.bengodwinweb.pettycash.dto.mapper;

import com.bengodwinweb.pettycash.dto.model.TransactionDto;
import com.bengodwinweb.pettycash.model.Transaction;
import com.bengodwinweb.pettycash.util.MoneyUtil;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public static TransactionDto toTransactionDto(Transaction transaction) {
        return new TransactionDto()
                .setId(transaction.getId())
                .setPaidTo(transaction.getPaidTo())
                .setExpenseType(transaction.getExpenseType())
                .setAmount(transaction.getAmount() / (double) MoneyUtil.CENTS_PER_DOLLAR)
                .setIndex(transaction.getIndex())
                .setAccount(transaction.getAccount())
                .setDescription(transaction.getDescription());
    }
}
