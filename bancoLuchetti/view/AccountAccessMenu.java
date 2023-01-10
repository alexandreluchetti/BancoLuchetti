package br.com.alexandre.bancoLuchetti.view;

import br.com.alexandre.bancoLuchetti.Main;

/**
 *
 * @author Alexandre Luchetti
 */
public class AccountAccessMenu {

    protected static boolean accountAccessMenu(Main main) {

        System.out.println("\n### Menu Conta ###");
        System.out.println("0 - Sair");
        System.out.println("1 - Acessar conta PF");
        System.out.println("2 - Acessar conta PJ");
        System.out.print("Digite a opção desejada: ");
        int userEntrty = main.getScanner().nextInt();

        switch (userEntrty) {
            case 0:
                return false;

            case 1:
                main.getControllerNaturalPersonAccount().accessingNaturalPersonAccount(main);
                break;

            case 2:
                main.getControllerLegalPersonAccount().accessingLegalPersonOperationsMenu(main);
                break;

            default:
                System.out.println("Você digitou um valor inválido! Tente novamente.");
                break;
        }
        return true;
    }
}
