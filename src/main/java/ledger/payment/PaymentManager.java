package ledger.payment;

import command.CommandExecution;
import java.math.BigDecimal;
import lombok.NonNull;

public class PaymentManager implements CommandExecution {

    private final String[] inputs;
    private PaymentProcessor paymentProcessor;

    public PaymentManager(@NonNull final String[] inputs) {
        this.inputs = inputs;
    }

    @Override
    public void execute() {
        // Format - PAYMENT BANK_NAME BORROWER_NAME LUMP_SUM_AMOUNT EMI_NO
        String bank = inputs[1];
        String borrower = inputs[2];
        BigDecimal lumpsum = BigDecimal.valueOf(Double.valueOf(inputs[3]));
        Integer emiNumber = Integer.valueOf(inputs[4]);
        executePayment(bank, borrower, lumpsum, emiNumber);
    }

    private void executePayment(String bank, String borrower, BigDecimal lumpsum, Integer emiNumber) {
        paymentProcessor = new PaymentProcessor(bank, borrower, lumpsum, emiNumber);
        paymentProcessor.processPayment();
    }
}
