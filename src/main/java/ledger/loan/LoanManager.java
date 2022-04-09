package ledger.loan;

import command.CommandExecution;
import java.math.BigDecimal;
import lombok.NonNull;

public class LoanManager implements CommandExecution {

    private LoanProcessor loanProcessor;
    private final String[] inputs;

    public LoanManager(@NonNull final String[] inputs) {
        this.inputs = inputs;
    }

    @Override
    public void execute() {
        // Format - LOAN BANK_NAME BORROWER_NAME PRINCIPAL NO_OF_YEARS RATE_OF_INTEREST
        String bank = inputs[1];
        String borrower = inputs[2];
        BigDecimal principal = BigDecimal.valueOf(Long.parseLong(inputs[3]));
        Integer noOfYears = Integer.valueOf(inputs[4]);
        double roi = Double.valueOf(inputs[5]);
        executeLoan(bank, borrower, principal, noOfYears, roi);
    }

    private void executeLoan(String bank, String borrower, BigDecimal principal, Integer noOfYears, double roi) {
        loanProcessor = new LoanProcessor(bank, borrower, principal, noOfYears, roi);
        loanProcessor.processLoan();
    }
}
