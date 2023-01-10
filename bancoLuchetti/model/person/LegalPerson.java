package br.com.alexandre.bancoLuchetti.model.person;

import java.time.LocalDate;

/**
 *
 * @author Alexandre
 */
public class LegalPerson {

    private String companyName;
    private String commercialName;
    private String cnpj;
    private LocalDate companyOpeningDate;

    public LegalPerson(String companyName, String commercialName, String cnpj, LocalDate companyOpeningDate) {
        this.companyName = companyName;
        this.commercialName = commercialName;
        this.cnpj = cnpj;
        this.companyOpeningDate = companyOpeningDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCommercialName() {
        return commercialName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public LocalDate getCompanyOpeningDate() {
        return companyOpeningDate;
    }
}