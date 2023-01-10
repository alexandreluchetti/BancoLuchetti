package br.com.alexandre.bancoLuchetti.model.account;

import br.com.alexandre.bancoLuchetti.model.person.NaturalPerson;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Alexandre
 */
public class NaturalPersonAccount extends Account {

    private NaturalPerson personalData;
    
    public NaturalPersonAccount() {
    }

    public NaturalPersonAccount(String accountType, String agency, String number, double balance, double withdrawTax, double transferTax, String password, ArrayList<Operation> operations, String firstName, String lastName, String cpf, String gender, LocalDate dateOfBirth) {
        super("Conta Pessoa Física", agency, number, balance, withdrawTax, transferTax, password, operations);
        this.personalData = new NaturalPerson(firstName, lastName, cpf, gender, dateOfBirth);
    }

    public NaturalPerson getPersonalData() {
        return personalData;
    }
    
}