package ledger.loan;

import static util.LedgerUtil.getMonthsFromYears;
import static util.LedgerUtil.calculateEMIAmount;
import static util.LedgerUtil.calculateInterestAmount;
import static util.LedgerUtil.getUserLoanMapping;
import static util.LedgerUtil.customerLoanMap;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import model.Loan;
import model.Payment;

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
        List<Payment> payments = initializePayment();
        Integer totalMonths = getMonthsFromYears(loanYears);
        BigDecimal interestAmount = BigDecimal.valueOf(calculateInterestAmount(principal, loanYears, rateOfInterest))
                .setScale(2, RoundingMode.CEILING);
        BigDecimal totalAmount = principal.add(interestAmount);
        BigDecimal emiAmount = calculateEMIAmount(totalAmount, totalMonths);
        Loan loan = new Loan(principal, interestAmount, totalAmount, totalMonths, emiAmount, payments);
        updateUserLoanLedger(loan);
    }

    private List<Payment> initializePayment() {
        List<Payment> payments = new ArrayList<>();
        Payment payment = new Payment(new BigDecimal(0).setScale(0, RoundingMode.CEILING), getMonthsFromYears(loanYears));
        payments.add(payment);
        return payments;
    }

    private void updateUserLoanLedger(Loan loan) {
        if (getUserLoanMapping().containsKey(borrower)) {
            Map<String, Loan> userLoanLedger = getUserLoanMapping().get(borrower);
            userLoanLedger.put(bank, loan);
            getUserLoanMapping().put(borrower, userLoanLedger);
        } else {
            Map<String, Loan> loanMap = new HashMap<>();
            loanMap.put(bank, loan);
            customerLoanMap.put(borrower, loanMap);
        }
    }
}
