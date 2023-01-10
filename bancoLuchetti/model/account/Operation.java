package br.com.alexandre.bancoLuchetti.model.account;

import java.time.LocalDate;

/**
 *
 * @author Alexandre Luchetti
 */
public abstract class Operation {
    
    private LocalDate localDate;
    private String operation;
    private double value;
    private String destinationAccount;
    private double partialBalance;

    public Operation(LocalDate localDate, String operation, double value, double partialBalance) {
        this.localDate = localDate;
        this.operation = operation;
        this.value = value;
        this.partialBalance = partialBalance;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public String getOperation() {
        return operation;
    }

    public double getValue() {
        return value;
    }

    public double getPartialBalance() {
        return partialBalance;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }
    
}
