package ledger.balance;

import command.CommandExecution;
import lombok.NonNull;

public class BalanceManager implements CommandExecution {

    private final String[] inputs;
    private BalanceProcessor balanceProcessor;

    public BalanceManager(@NonNull final String[] inputs) {
        this.inputs = inputs;
    }

    @Override
    public void execute() {
        // Input format - BALANCE BANK_NAME BORROWER_NAME EMI_NO
        String bank = inputs[1];
        String borrower = inputs[2];
        Integer emiNumber = Integer.valueOf(inputs[3]);
        executeBalance(bank, borrower, emiNumber);
    }

    private void executeBalance(String bank, String borrower, Integer emiNumber) {
        balanceProcessor = new BalanceProcessor(bank, borrower, emiNumber);
        // display balance for the borrower
        balanceProcessor.processBalance();
    }
}
