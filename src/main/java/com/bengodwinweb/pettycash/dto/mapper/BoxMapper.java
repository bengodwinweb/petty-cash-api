package com.bengodwinweb.pettycash.dto.mapper;

import com.bengodwinweb.pettycash.dto.model.BoxDto;
import com.bengodwinweb.pettycash.model.Box;
import com.bengodwinweb.pettycash.util.MoneyUtil;
import org.springframework.stereotype.Component;

@Component
public class BoxMapper {

    public static BoxDto toBoxDto(Box box) {
        return new BoxDto()
                .setId(box.getId())
                .setTwenties(box.getTwenties())
                .setTwentiesValue(MoneyUtil.centsToDouble(box.getTwentiesValue()))
                .setTens(box.getTens())
                .setTensValue(MoneyUtil.centsToDouble(box.getTensValue()))
                .setFives(box.getFives())
                .setFivesValue(MoneyUtil.centsToDouble(box.getFivesValue()))
                .setOnes(box.getOnes())
                .setOnesValue(MoneyUtil.centsToDouble(box.getOnesValue()))
                .setQrolls(box.getQrolls())
                .setQrollsValue(MoneyUtil.centsToDouble(box.getQrollsValue()))
                .setDrolls(box.getDrolls())
                .setDrollsValue(MoneyUtil.centsToDouble(box.getDrollsValue()))
                .setNrolls(box.getNrolls())
                .setNrollsValue(MoneyUtil.centsToDouble(box.getNrollsValue()))
                .setProlls(box.getProlls())
                .setProllsValue(MoneyUtil.centsToDouble(box.getProllsValue()))
                .setQuarters(box.getQuarters())
                .setQuartersValue(MoneyUtil.centsToDouble(box.getQuartersValue()))
                .setDimes(box.getDimes())
                .setDimesValue(MoneyUtil.centsToDouble(box.getDimesValue()))
                .setNickels(box.getNickels())
                .setNickelsValue(MoneyUtil.centsToDouble(box.getNickelsValue()))
                .setPennies(box.getPennies())
                .setPenniesValue(MoneyUtil.centsToDouble(box.getPenniesValue()))
                .setBoxTotal(box.getBoxTotal() / (double) MoneyUtil.CENTS_PER_DOLLAR);
    }
}
