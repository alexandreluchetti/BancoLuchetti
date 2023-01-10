package br.com.alexandre.bancoLuchetti.model.account;

import br.com.alexandre.bancoLuchetti.model.person.LegalPerson;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Alexandre
 */
public class LegalPersonAccount extends Account {

    private LegalPerson companyData;
    private double creditLine;

    public LegalPersonAccount() {
    }
    
    public LegalPersonAccount(String accountType, String agency, String number, double balance, double withdrawTax, double transferTax, String password, ArrayList<Operation> operations, String corporateName, String commercialName, String cnpj, LocalDate companyOpeningDate) {
        super("Conta Pessoa Jurídica", agency, number, balance, withdrawTax, transferTax, password, operations);
        this.companyData = new LegalPerson(corporateName, commercialName, cnpj, companyOpeningDate);
        this.creditLine = 0;
    }

    public void setCreditLine(double creditLine) {
        this.creditLine = creditLine;
    }

    public LegalPerson getCompanyData() {
        return companyData;
    }

    public double getCreditLine() {
        return creditLine;
    }
}