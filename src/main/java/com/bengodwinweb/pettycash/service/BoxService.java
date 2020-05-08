package com.bengodwinweb.pettycash.service;

import com.bengodwinweb.pettycash.dto.model.BoxDto;
import com.bengodwinweb.pettycash.exception.NotFoundException;
import com.bengodwinweb.pettycash.exception.SingleValidationException;
import com.bengodwinweb.pettycash.model.Box;
import com.bengodwinweb.pettycash.repository.BoxRepository;
import com.bengodwinweb.pettycash.repository.CashboxRepository;
import com.bengodwinweb.pettycash.util.BoxUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoxService {

    @Autowired
    private CashboxRepository cashboxRepository;

    @Autowired
    private BoxRepository boxRepository;

    public void updateBox(BoxDto boxDto) throws NotFoundException, SingleValidationException {
        if (getById(boxDto.getId()).getBoxTotal() != BoxUtil.calculateBoxDtoTotal(boxDto)) {
            throw new SingleValidationException("Updated box total must match previous total");
        }
        boxRepository.save(getById(boxDto.getId())
                .setTwenties(boxDto.getTwenties())
                .setTens(boxDto.getTens())
                .setFives(boxDto.getFives())
                .setOnes(boxDto.getOnes())
                .setQrolls(boxDto.getQrolls())
                .setDrolls(boxDto.getDrolls())
                .setNrolls(boxDto.getNrolls())
                .setProlls(boxDto.getProlls())
                .setQuarters(boxDto.getQuarters())
                .setDimes(boxDto.getDimes())
                .setNickels(boxDto.getNickels())
                .setPennies(boxDto.getPennies()));
    }

    private Box getById(String id) throws NotFoundException {
        Optional<Box> box = boxRepository.findById(id);
        if (box.isEmpty()) throw new NotFoundException("box with id " + id + " not found");
        return box.get();
    }
}
