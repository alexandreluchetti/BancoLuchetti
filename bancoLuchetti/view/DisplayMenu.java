package br.com.alexandre.bancoLuchetti.view;

import br.com.alexandre.bancoLuchetti.Main;

/**
 *
 * @author Alexandre
 */
public class DisplayMenu {

    protected void displayMenu(Main main) {
        System.out.println("\n### Consultar ###");
        System.out.println("0 - Voltar");
        System.out.println("1 - Pessoa Física");
        System.out.println("2 - Pessoa Jurídica");
        System.out.print("Digite a opção desejada: ");
        int userEntry = main.getScanner().nextInt();

        switch (userEntry) {
            case 0:
                break;

            case 1:
                main.getControllerNaturalPersonAccount()
                        .displayNaturalPersonAccount(main.getControllerNaturalPersonAccount()
                                .getArrayListNaturalPersonAccount());
                break;

            case 2:
                main.getControllerLegalPersonAccount()
                        .displayLegalPersonAccount(main.getControllerLegalPersonAccount()
                                .getArrayListLegalPersonAccount());
                break;

            default:
                System.out.println("Você digitou um valor inválido! Tente novamente.");
                break;
        }
    }
}