package ledger.payment;

import static util.LedgerUtil.getUserLoanMapping;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import model.Loan;
import model.Payment;

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
        Map<String, Loan> borrowerLoanLedger = getUserLoanMapping().get(borrower);
        Loan borrowerLoan = borrowerLoanLedger.get(bank);
        List<Payment> payments = borrowerLoan.getPayments();
        payments = updatePayments(payments, lumpsum, emiNumber, borrowerLoan.getEmiAmount(), borrowerLoan.getAmount());
        borrowerLoan.setPayments(payments);
        borrowerLoanLedger.put(bank, borrowerLoan);
        getUserLoanMapping().get(borrower).put(bank, borrowerLoan);
    }

    private List<Payment> updatePayments(List<Payment> payments, BigDecimal lumpsum, Integer emiNumber, BigDecimal emiAmount, BigDecimal amount) {
        if (emiNumber == 0) {
            payments = processFirstPayment(payments, amount, lumpsum, emiAmount);
        } else {
            for (int i = payments.size(); i < emiNumber; i++) {
                Payment prevPayment = payments.get(i - 1);
                payments.add(processMidPayment(prevPayment, emiAmount, amount));
            }
            if (payments.size() == emiNumber) {
                Payment prevPayment = payments.get(payments.size() - 1);
                payments.add(processFinalPayment(prevPayment, emiAmount, lumpsum, amount));
            }
        }
        return payments;
    }

    private Payment processFinalPayment(Payment prevPayment, BigDecimal emiAmount, BigDecimal lumpsum, BigDecimal amount) {
        BigDecimal emiAmountPaid = prevPayment.getAmountPaid().add(emiAmount).add(lumpsum);
        if (emiAmountPaid.compareTo(amount) > 0) {
            emiAmountPaid = amount;
        }
        BigDecimal balanceAmount = amount.subtract(emiAmountPaid).setScale(0, RoundingMode.CEILING);
        Integer balanceEmiCount = balanceAmount.divide(emiAmount, 0, RoundingMode.CEILING).intValue(); //setScale(0, RoundingMode.CEILING)
        return new Payment(emiAmountPaid, balanceEmiCount);
    }

    private Payment processMidPayment(Payment prevPayment, BigDecimal emiAmount, BigDecimal amount) {
        BigDecimal paymentAmount = prevPayment.getAmountPaid().add(emiAmount);
        if (paymentAmount.compareTo(amount) > 0) {
            paymentAmount = amount;
        }
        return new Payment(paymentAmount, prevPayment.getBalanceEmi() - 1);
    }

    private List<Payment> processFirstPayment(List<Payment> payments, BigDecimal amount, BigDecimal lumpsum, BigDecimal emiAmount) {
        BigDecimal balanceAmount = amount.subtract(lumpsum);
        Integer balanceEmiCount = balanceAmount.divide(emiAmount, 0, RoundingMode.CEILING).intValue(); // .setScale(0, RoundingMode.CEILING)
        Payment payment = new Payment(lumpsum, balanceEmiCount);
        payments.remove(payments.get(0));
        payments.add(payment);
        return payments;
    }
}
