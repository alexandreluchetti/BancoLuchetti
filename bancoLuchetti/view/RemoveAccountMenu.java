package br.com.alexandre.bancoLuchetti.view;

import br.com.alexandre.bancoLuchetti.Main;

/**
 *
 * @author Alexandre Luchetti
 */
public class RemoveAccountMenu {

    public boolean removeAccountMenu(Main main) {
        System.out.println("\n### Excluir Conta ###");
        System.out.println("0 - Voltar");
        System.out.println("1 - Pessoa Física");
        System.out.println("2 - Pessoa Jurídica");
        System.out.print("Digite a opção desejada: ");
        int userEntry = main.getScanner().nextInt();

        switch (userEntry) {
            case 0:
                return false;

            case 1:
                System.out.println("\nDigite os dados solicitados");
                
                boolean executingNaturalPersonAccount = main.getControllerNaturalPersonAccount().removeNaturalPersonAccount(main);
                if (executingNaturalPersonAccount == true) {
                    System.out.println("\n!!! Conta excluída com sucesso !!!");
                }
                break;

            case 2:
                System.out.println("\nDigite os dados solicitados");
                
                boolean executingLegalPersonAccount = main.getControllerLegalPersonAccount().removeLegalPersonAccount(main);
                if (executingLegalPersonAccount == true) {
                    System.out.println("\n!!! Conta excluída com sucesso !!!");
                }
                break;

            default:
                System.out.println("Você digitou um valor inválido! Tente novamente.");
                return false;
        }
        
        return true;
    }
}