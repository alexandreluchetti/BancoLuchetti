package br.com.alexandre.bancoLuchetti.model.account;

import java.util.ArrayList;

/**
 *
 * @author Alexandre
 */
public abstract class Account {

    private String accountType;
    private String agency;
    private String number;
    private double balance;
    private double withdrawTax;
    private double transferTax;
    private String password;
    private ArrayList<Operation> operations;

    public Account() {
    }

    public Account(String accountType, String agency, String number, double balance, double withdrawTax, double transferTax, String password, ArrayList<Operation> operations) {
        this.accountType = accountType;
        this.agency = agency;
        this.number = number;
        this.balance = balance;
        this.withdrawTax = withdrawTax;
        this.transferTax = transferTax;
        this.password = password;
        this.operations = new ArrayList<Operation>();
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setWithdrawTax(double withdrawTax) {
        this.withdrawTax = withdrawTax;
    }

    public void setTransferTax(double transferTax) {
        this.transferTax = transferTax;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getAgency() {
        return agency;
    }

    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }
    
    public double getWithdrawTax() {
        return withdrawTax;
    }

    public double getTransferTax() {
        return transferTax;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Operation> getOperations() {
        return operations;
    }
}