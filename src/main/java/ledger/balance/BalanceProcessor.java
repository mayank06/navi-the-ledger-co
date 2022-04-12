package ledger.balance;

import static util.LedgerUtil.getUserLoanMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import model.Loan;
import model.Payment;

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
        Map<String, Loan> userLoanLedger = getUserLoanMapping().get(borrower);
        Loan borrowerLoanMap = userLoanLedger.get(bank);
        List<Payment> payments = borrowerLoanMap.getPayments();
        payments = updatePaymentsOfBorrower(payments, emiNumber, borrowerLoanMap.getEmiAmount(), borrowerLoanMap.getAmount());
        borrowerLoanMap.setPayments(payments);
        userLoanLedger.put(borrower, borrowerLoanMap);
        getUserLoanMapping().get(borrower).put(bank, borrowerLoanMap);
        displayBalance(payments, emiNumber, bank, borrower);
    }

    private void displayBalance(List<Payment> payments, Integer emiNumber, String bank, String borrower) {
        Payment payment = payments.get(emiNumber);
        System.out.println(bank + " " + borrower + " " + payment.getAmountPaid() + " " + payment.getBalanceEmi());
    }

    private List<Payment> updatePaymentsOfBorrower(List<Payment> payments, Integer emiNumber, BigDecimal emiAmount, BigDecimal amount) {
        for (int i = payments.size(); i <= emiNumber; i++) {
            Payment lastPayment = payments.get(i - 1);
            payments.add(calculatePayment(lastPayment, emiAmount, amount));
        }
        return payments;
    }

    private Payment calculatePayment(Payment lastPayment, BigDecimal emiAmount, BigDecimal amount) {
        BigDecimal totalAmountPaid = lastPayment.getAmountPaid().add(emiAmount);
        if (totalAmountPaid.compareTo(amount) > 0) {
            totalAmountPaid = amount;
        }
        return new Payment(totalAmountPaid, lastPayment.getBalanceEmi() - 1);
    }

}
