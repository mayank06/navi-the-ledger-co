package ledger.balance;

import lombok.NonNull;

public class BalanceProcessor {

    private final String bank;
    private final String borrower;
    private final Integer emiNumber;

    public BalanceProcessor(@NonNull final String bank, @NonNull final String borrower, @NonNull final Integer emiNumber) {
        this.bank = bank;
        this.borrower = borrower;
        this.emiNumber = emiNumber;
    }

    protected void processBalance() {

    }
}
