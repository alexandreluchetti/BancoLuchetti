package br.com.alexandre.bancoLuchetti.view;

import br.com.alexandre.bancoLuchetti.Main;

/**
 *
 * @author Alexandre Luchetti
 */
public class MainMenu {
    
    public static boolean mainMenu(Main main) {

        System.out.println("\n### Menu Principal ###");
        System.out.println("0 - Sair");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Consultar");
        System.out.println("3 - Acessar sua conta");
        System.out.println("4 - Excluir sua conta");
        System.out.print("Digite a opção desejada: ");
        int userEntrty = main.getScanner().nextInt();

        switch (userEntrty) {
            case 0:
                return false;

            case 1:
                main.getRegistrationMenu().registrationMenu(main);
                break;

            case 2:
                main.getDisplayMenu().displayMenu(main);
                break;
                
            case 3:
                main.getAccountAccessMenu().accountAccessMenu(main);
                break;
                
            case 4:
                main.getRemoveAccountMenu().removeAccountMenu(main);
                break;

            default:
                System.out.println("Você digitou um valor inválido! Tente novamente.");
                break;
        }
        
        return true;
    }
}