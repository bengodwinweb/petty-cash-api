package com.bengodwinweb.pettycash.service;

import com.bengodwinweb.pettycash.dto.mapper.TransactionMapper;
import com.bengodwinweb.pettycash.dto.model.TransactionDto;
import com.bengodwinweb.pettycash.exception.NotFoundException;
import com.bengodwinweb.pettycash.model.Cashbox;
import com.bengodwinweb.pettycash.model.Transaction;
import com.bengodwinweb.pettycash.repository.CashboxRepository;
import com.bengodwinweb.pettycash.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CashboxRepository cashboxRepository;

    @Autowired
    private CashboxService cashboxService;

    public TransactionDto deleteTransactionById(String cashboxId, String transactionId) throws NotFoundException {
        Transaction transaction = getById(transactionId);
        transactionRepository.delete(transaction);

        Cashbox cashbox = cashboxService.getFromId(cashboxId);
        cashbox.getTransactions().remove(transaction);
        cashbox.setRemainingCash(cashbox.getRemainingCash() + transaction.getAmount());
        cashboxService.updateCurrentAndChangeBoxes(cashbox);
        cashboxRepository.save(cashbox);

        return TransactionMapper.toTransactionDto(transaction);
    }

    private Transaction getById(String id) throws NotFoundException {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isEmpty()) throw new NotFoundException("Transaction with id " + id + "not found");
        return transactionOptional.get();
    }
}
