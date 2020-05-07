package com.bengodwinweb.pettycash.dto.response;

import com.bengodwinweb.pettycash.dto.model.BoxDto;
import com.bengodwinweb.pettycash.dto.model.CashboxDto;
import com.bengodwinweb.pettycash.dto.model.TransactionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class CashboxResponse {

    private CashboxDto cashbox;
    private BoxDto currentBox;
    private BoxDto changeBox;
    private BoxDto idealBox;
    private List<TransactionDto> transactions;
}
