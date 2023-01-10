package br.com.alexandre.bancoLuchetti.view;

import br.com.alexandre.bancoLuchetti.Main;
import br.com.alexandre.bancoLuchetti.model.account.LegalPersonAccount;
import br.com.alexandre.bancoLuchetti.model.account.NaturalPersonAccount;

/**
 *
 * @author Alexandre
 */
public class RegistrationMenu {

    protected void registrationMenu(Main main) {
        System.out.println("\n### Cadastrar Conta ###");
        System.out.println("0 - Voltar");
        System.out.println("1 - Pessoa Física");
        System.out.println("2 - Pessoa Jurídica");
        System.out.print("Digite a opção desejada: ");
        int userEntry = main.getScanner().nextInt();

        switch (userEntry) {
            case 0:
                break;

            case 1:
                NaturalPersonAccount naturalPersonAccount = main.getControllerNaturalPersonAccount()
                        .naturalPersonAccountRegistration(main.getControllerNaturalPersonAccount()
                                .getArrayListNaturalPersonAccount(), main);
                
                main.getControllerNaturalPersonAccount().saveNaturalPersonAccount(
                        main.getControllerNaturalPersonAccount()
                                .getArrayListNaturalPersonAccount(), naturalPersonAccount);
                
                System.out.println("\nA conta de " + naturalPersonAccount
                        .getPersonalData()
                        .getFirstName() + " foi cadastrada com sucesso!");
                break;

            case 2:
                LegalPersonAccount legalPersonAccount = main.getControllerLegalPersonAccount()
                        .legalPersonAccountRegistration(main.getControllerLegalPersonAccount()
                                .getArrayListLegalPersonAccount(), main);
                
                main.getControllerLegalPersonAccount().saveLegalPersonAccount(
                        main.getControllerLegalPersonAccount()
                                .getArrayListLegalPersonAccount(), legalPersonAccount);
                
                System.out.println("\nA conta da empresa " + legalPersonAccount
                        .getCompanyData()
                        .getCompanyName() + " foi cadastrada com sucesso!");
                break;

            default:
                System.out.println("Você digitou um valor inválido! Tente novamente.");
                break;
        }
    }
}