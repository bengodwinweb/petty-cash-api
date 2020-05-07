package com.bengodwinweb.pettycash.service;

import com.bengodwinweb.pettycash.dto.mapper.BoxMapper;
import com.bengodwinweb.pettycash.dto.mapper.CashboxMapper;
import com.bengodwinweb.pettycash.dto.mapper.TransactionMapper;
import com.bengodwinweb.pettycash.dto.model.BoxDto;
import com.bengodwinweb.pettycash.dto.model.CashboxDto;
import com.bengodwinweb.pettycash.dto.model.TransactionDto;
import com.bengodwinweb.pettycash.exception.NotFoundException;
import com.bengodwinweb.pettycash.model.Box;
import com.bengodwinweb.pettycash.model.Cashbox;
import com.bengodwinweb.pettycash.model.Transaction;
import com.bengodwinweb.pettycash.model.User;
import com.bengodwinweb.pettycash.repository.BoxRepository;
import com.bengodwinweb.pettycash.repository.CashboxRepository;
import com.bengodwinweb.pettycash.repository.TransactionRepository;
import com.bengodwinweb.pettycash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CashboxService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CashboxRepository cashboxRepository;

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public CashboxDto createCashbox(CashboxDto newCashbox, String userEmail) {
        Box idealBox = Box.createDefaultBox();
        idealBox.updateBox((int) (newCashbox.getTotal() * 100));
        boxRepository.save(idealBox);

        Box currentBox = new Box()
                .setTwenties(idealBox.getTwenties())
                .setTens(idealBox.getTens())
                .setFives(idealBox.getFives())
                .setOnes(idealBox.getOnes())
                .setQuarters(idealBox.getQuarters())
                .setDimes(idealBox.getDimes())
                .setNickels(idealBox.getNickels())
                .setPennies(idealBox.getPennies());
        currentBox.updateBox((int) (newCashbox.getRemainingCash() * 100));
        boxRepository.save(currentBox);

        Box changeBox = new Box();
        changeBox.updateBox(idealBox.getBoxTotal() - currentBox.getBoxTotal());
        boxRepository.save(changeBox);

        User user = userRepository.findByEmail(userEmail);

        Cashbox cashbox = new Cashbox()
                .setUser(user)
                .setCompany(newCashbox.getCompany())
                .setName(newCashbox.getName())
                .setTotal((int) (newCashbox.getTotal() * 100))
                .setRemainingCash((int) (newCashbox.getRemainingCash()) * 100)
                .setCurrentBox(currentBox)
                .setChangeBox(changeBox)
                .setIdealBox(idealBox)
                .setLastUpdated(LocalDateTime.now());

        return CashboxMapper.toCashboxDto(cashboxRepository.save(cashbox));
    }

    public List<CashboxDto> getCashboxesByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        List<Cashbox> cashboxes = cashboxRepository.findByUser(user);
        return cashboxes.stream().sorted((Comparator.comparing(Cashbox::getLastUpdated))).map(CashboxMapper::toCashboxDto).collect(Collectors.toList());
    }

    public CashboxDto getCashboxById(String id) throws NotFoundException {
        return CashboxMapper.toCashboxDto(getFromId(id));
    }

    public CashboxDto deleteCashboxById(String id) throws NotFoundException {
        Cashbox cashbox = getFromId(id);
        cashboxRepository.delete(cashbox);
        return CashboxMapper.toCashboxDto(cashbox);
    }

    public CashboxDto resetCashbox(CashboxDto cashboxDto) throws NotFoundException {
        Cashbox cashbox = getFromId(cashboxDto.getId());
        cashbox.getTransactions().forEach(transaction -> transactionRepository.delete(transaction));
        cashbox.getTransactions().clear();
        cashbox.setRemainingCash(cashbox.getTotal());
        cashbox.setLastUpdated(LocalDateTime.now());
        updateBoxes(cashbox);
        return CashboxMapper.toCashboxDto(cashboxRepository.save(cashbox));
    }

    public void updateCashbox(CashboxDto cashboxDto) throws NotFoundException {
        Cashbox cashbox = getFromId(cashboxDto.getId())
                .setCompany(cashboxDto.getCompany())
                .setName(cashboxDto.getName())
                .setLastUpdated(LocalDateTime.now());

        int newTotal = (int) (cashboxDto.getTotal() * 100);
        if (newTotal != cashbox.getTotal()) {
            Box idealBox = cashbox.getIdealBox();
            idealBox.updateBox(newTotal);
            Box currentBox = cashbox.getCurrentBox();
            Box changeBox = cashbox.getChangeBox();
            changeBox.makeChange(currentBox, idealBox, newTotal);

            boxRepository.save(idealBox);
            boxRepository.save(changeBox);
            cashbox.setTotal(newTotal);
        }

        int remainingCash = (int) (cashboxDto.getRemainingCash() * 100);
        if (remainingCash != cashbox.getRemainingCash()) {
            cashbox.setRemainingCash(remainingCash);
            updateBoxes(cashbox);
        }

        cashboxRepository.save(cashbox);
    }

    public void updateBoxes(String cashboxId) throws NotFoundException {
        cashboxRepository.save(updateBoxes(getFromId(cashboxId)));
    }

    protected Cashbox updateBoxes(Cashbox cashbox) {
        Box currentBox = cashbox.getCurrentBox();
        Box changeBox = cashbox.getChangeBox();
        Box idealBox = cashbox.getIdealBox();

        currentBox.updateBox(cashbox.getRemainingCash());
        changeBox.makeChange(currentBox, idealBox, cashbox.getTotal() - cashbox.getRemainingCash());

        boxRepository.save(currentBox);
        boxRepository.save(changeBox);

        return cashboxRepository.save(cashbox.setLastUpdated(LocalDateTime.now()));
    }

    protected Cashbox getFromId(String id) throws NotFoundException {
        Optional<Cashbox> cashboxOptional = cashboxRepository.findById(id);
        if (cashboxOptional.isEmpty()) throw new NotFoundException("Cashbox with id " + id + " not found");
        return cashboxOptional.get();
    }

    public boolean userOwnsCashbox(String cashboxId, String userEmail) throws NotFoundException {
        Cashbox cashbox = getFromId(cashboxId);
        return cashbox.getUser().getEmail().equals(userEmail);
    }

    public BoxDto getCurrentBox(String cashboxId) throws NotFoundException {
        return BoxMapper.toBoxDto(getFromId(cashboxId).getCurrentBox());
    }

    public BoxDto getChangeBox(String cashboxId) throws NotFoundException {
        return BoxMapper.toBoxDto(getFromId(cashboxId).getChangeBox());
    }

    public BoxDto getIdealBox(String cashboxId) throws NotFoundException {
        return BoxMapper.toBoxDto(getFromId(cashboxId).getIdealBox());
    }

    public List<TransactionDto> getTransactions(String cashboxId) throws NotFoundException {
        return getFromId(cashboxId).getTransactions().stream().sorted(Comparator.comparing(Transaction::getCreated)).map(TransactionMapper::toTransactionDto).collect(Collectors.toList());
    }
}
