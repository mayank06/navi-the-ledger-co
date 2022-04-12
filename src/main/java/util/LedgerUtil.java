package util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import model.Loan;

public class LedgerUtil {

    public static Map<String, Map<String, Loan>> customerLoanMap = new HashMap<>();

    public static int getMonthsFromYears(int year) {
        return year * 12;
    }

    public static double calculateInterestAmount(BigDecimal principalAmount, Integer years, Double interestRate) {
        return (principalAmount.doubleValue() * years * interestRate) / 100;
    }

    public static BigDecimal calculateEMIAmount(BigDecimal principalAmount, Integer months) {
        return principalAmount.divide(BigDecimal.valueOf(months), 0, RoundingMode.CEILING); //.setScale(0, RoundingMode.CEILING)
    }

    public static Map<String, Map<String,Loan>> getUserLoanMapping() {
        return customerLoanMap;
    }
}
