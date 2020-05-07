package com.bengodwinweb.pettycash.dto.mapper;

import com.bengodwinweb.pettycash.dto.model.CashboxDto;
import com.bengodwinweb.pettycash.model.Cashbox;
import org.springframework.stereotype.Component;

@Component
public class CashboxMapper {

    public static CashboxDto toCashboxDto(Cashbox cashbox) {
        return new CashboxDto()
                .setId(cashbox.getId())
                .setCompany(cashbox.getCompany())
                .setName(cashbox.getName())
                .setTotal(cashbox.getTotal() / 100d)
                .setRemainingCash(cashbox.getRemainingCash() / 100d);
    }
}
