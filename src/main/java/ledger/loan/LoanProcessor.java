package ledger.loan;

import java.math.BigDecimal;
import lombok.NonNull;

public class LoanProcessor {

    private final String bank;
    private final String borrower;
    private final BigDecimal principal;
    private final Integer loanYears;
    private final double rateOfInterest;

    public LoanProcessor(@NonNull final String bank, @NonNull final String borrower, @NonNull final BigDecimal principal,
                         @NonNull final Integer loanYears, @NonNull final double rateOfInterest) {
        this.bank = bank;
        this.borrower = borrower;
        this.principal = principal;
        this.loanYears = loanYears;
        this.rateOfInterest = rateOfInterest;
    }

    protected void processLoan() {

    }

}
