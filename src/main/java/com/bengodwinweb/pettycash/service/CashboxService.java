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
import com.bengodwinweb.pettycash.util.MoneyUtil;
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
        Box currentBox = new Box();
        Box changeBox = new Box();
        User user = userRepository.findByEmail(userEmail);

        Cashbox cashbox = new Cashbox()
                .setUser(user)
                .setCompany(newCashbox.getCompany())
                .setName(newCashbox.getName())
                .setTotal(MoneyUtil.doubleToCents(newCashbox.getTotal()))
                .setRemainingCash(MoneyUtil.doubleToCents(newCashbox.getRemainingCash()))
                .setCurrentBox(currentBox)
                .setChangeBox(changeBox)
                .setIdealBox(idealBox)
                .setLastUpdated(LocalDateTime.now());

        updateAllCashboxes(cashbox);

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

    public void resetCashbox(String cashboxId) throws NotFoundException {
        Cashbox cashbox = getFromId(cashboxId);
        cashbox.getTransactions().forEach(transaction -> transactionRepository.delete(transaction));
        cashbox.getTransactions().clear();
        cashbox.setRemainingCash(cashbox.getTotal());

        Box currentBox = cashbox.getCurrentBox();
        Box changeBox = cashbox.getChangeBox();

        currentBox.addChange(changeBox);
        changeBox.resetAll();

        boxRepository.save(currentBox);
        boxRepository.save(changeBox);

        cashboxRepository.save(cashbox.setLastUpdated(LocalDateTime.now()));
    }

    public void updateCashbox(CashboxDto cashboxDto) throws NotFoundException {
        Cashbox cashbox = getFromId(cashboxDto.getId())
                .setCompany(cashboxDto.getCompany())
                .setName(cashboxDto.getName())
                .setTotal(MoneyUtil.doubleToCents(cashboxDto.getTotal()))
                .setRemainingCash(MoneyUtil.doubleToCents(cashboxDto.getRemainingCash()));

        updateAllCashboxes(cashbox);
        cashboxRepository.save(cashbox.setLastUpdated(LocalDateTime.now()));
    }

    protected void updateAllCashboxes(Cashbox cashbox) {
        Box idealBox = cashbox.getIdealBox();
        Box currentBox = cashbox.getCurrentBox();
        Box changeBox = cashbox.getChangeBox();

        idealBox.updateBox(cashbox.getTotal());
        currentBox
                .setTwenties(idealBox.getTwenties())
                .setTens(idealBox.getTens())
                .setFives(idealBox.getFives())
                .setOnes(idealBox.getOnes())
                .setQrolls(idealBox.getQrolls())
                .setDrolls(idealBox.getDrolls())
                .setNrolls(idealBox.getNrolls())
                .setProlls(idealBox.getProlls())
                .setQuarters(idealBox.getQuarters())
                .setDimes(idealBox.getDimes())
                .setNickels(idealBox.getNickels())
                .setPennies(idealBox.getPennies())
                .decrementTo(cashbox.getRemainingCash());

        changeBox.makeChange(currentBox, idealBox, cashbox.getTotal() - cashbox.getRemainingCash());

        boxRepository.save(idealBox);
        boxRepository.save(currentBox);
        boxRepository.save(changeBox);
    }

    protected void updateCurrentAndChangeBoxes(Cashbox cashbox) {
        Box currentBox = cashbox.getCurrentBox();
        Box changeBox = cashbox.getChangeBox();
        Box idealBox = cashbox.getIdealBox();

        currentBox.decrementTo(cashbox.getRemainingCash());
        changeBox.makeChange(currentBox, idealBox, cashbox.getTotal() - cashbox.getRemainingCash());

        boxRepository.save(currentBox);
        boxRepository.save(changeBox);
    }

    public void updateCurrentAndChangeBoxes(String cashboxId) throws NotFoundException {
        updateCurrentAndChangeBoxes(getFromId(cashboxId));
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
