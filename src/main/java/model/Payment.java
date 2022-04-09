package model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class Payment {

    private BigDecimal amountPaid;
    private Integer balanceEmi;

    public Payment(@NonNull final BigDecimal amountPaid, @NonNull final Integer balanceEmi) {
        this.amountPaid = amountPaid;
        this.balanceEmi = balanceEmi;
    }
}
