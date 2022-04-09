package model;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class Loan {

    private BigDecimal principal;
    private BigDecimal interestAmount;
    private BigDecimal amount;
    private Integer emiMonths;
    private BigDecimal emiAmount;
    private List<Payment> payments;

    public Loan(@NonNull final BigDecimal principal, @NonNull final BigDecimal interestAmount, @NonNull final BigDecimal amount,
                @NonNull final Integer emiMonths, @NonNull final BigDecimal emiAmount, @NonNull List<Payment> payments) {
        this.principal = principal;
        this.interestAmount = interestAmount;
        this.amount = amount;
        this.emiMonths = emiMonths;
        this.emiAmount = emiAmount;
        this.payments = payments;
    }

    public void setPayments(@NonNull List<Payment> payments) {
        this.payments = payments;
    }
}
