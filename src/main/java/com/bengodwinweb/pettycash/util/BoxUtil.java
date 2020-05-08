package com.bengodwinweb.pettycash.util;

import com.bengodwinweb.pettycash.dto.model.BoxDto;
import com.bengodwinweb.pettycash.model.Box;

public class BoxUtil {

    public static int calculateBoxDtoTotal(BoxDto boxDto) throws NullPointerException {
        Box box = new Box()
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
                .setPennies(boxDto.getPennies());

        return box.getBoxTotal();
    }
}
