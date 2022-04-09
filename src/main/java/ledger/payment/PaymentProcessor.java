package ledger.payment;

import java.math.BigDecimal;
import lombok.NonNull;

public class PaymentProcessor {

    private final String bank;
    private final String borrower;
    private final BigDecimal lumpsum;
    private final Integer emiNumber;

    public PaymentProcessor(@NonNull final String bank, @NonNull final String borrower,
                            @NonNull final BigDecimal lumpsum, @NonNull final Integer emiNumber) {
        this.bank = bank;
        this.borrower = borrower;
        this.lumpsum = lumpsum;
        this.emiNumber = emiNumber;
    }

    protected void processPayment() {

    }
}
