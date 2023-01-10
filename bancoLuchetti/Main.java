package br.com.alexandre.bancoLuchetti;

import br.com.alexandre.bancoLuchetti.model.account.LegalPersonAccount;
import br.com.alexandre.bancoLuchetti.model.account.NaturalPersonAccount;
import br.com.alexandre.bancoLuchetti.controller.ControllerLegalPersonAccount;
import br.com.alexandre.bancoLuchetti.controller.ControllerNaturalPersonAccount;
import br.com.alexandre.bancoLuchetti.view.AccountAccessMenu;
import br.com.alexandre.bancoLuchetti.view.DisplayMenu;
import br.com.alexandre.bancoLuchetti.view.MainMenu;
import br.com.alexandre.bancoLuchetti.view.RegistrationMenu;
import br.com.alexandre.bancoLuchetti.view.RemoveAccountMenu;
import java.util.Scanner;

/**
 *
 * @author Alexandre
 */
public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        
        System.out.println("========== Banco LUCHETTI ==========");
        
        boolean executing;
        do{
            executing = MainMenu.mainMenu(main);
        } while (executing);
        
        System.out.println("\n============================================");
        System.out.println("Obrigado por ser parceiro do Banco LUCHETTI");
    }

    private MainMenu mainMenu;
    private RegistrationMenu registrationMenu;
    private DisplayMenu displayMenu;
    private AccountAccessMenu accountAccessMenu;
    private RemoveAccountMenu removeAccountMenu;
    private NaturalPersonAccount naturalPersonAccount;
    private ControllerNaturalPersonAccount controllerNaturalPersonAccount;
    private LegalPersonAccount legalPersonAccount;
    private ControllerLegalPersonAccount controllerLegalPersonAccount;
    private Scanner scanner;

    private Main() {
        this.mainMenu = new MainMenu();
        this.registrationMenu = new RegistrationMenu();
        this.displayMenu = new DisplayMenu();
        this.accountAccessMenu = new AccountAccessMenu();
        this.removeAccountMenu = new RemoveAccountMenu();
        this.naturalPersonAccount = new NaturalPersonAccount();
        this.controllerNaturalPersonAccount = new ControllerNaturalPersonAccount();
        this.controllerLegalPersonAccount = new ControllerLegalPersonAccount();
        this.legalPersonAccount = new LegalPersonAccount();
        this.scanner = new Scanner(System.in);
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public RegistrationMenu getRegistrationMenu() {
        return registrationMenu;
    }

    public DisplayMenu getDisplayMenu() {
        return displayMenu;
    }

    public AccountAccessMenu getAccountAccessMenu() {
        return accountAccessMenu;
    }

    public RemoveAccountMenu getRemoveAccountMenu() {
        return removeAccountMenu;
    }

    public NaturalPersonAccount getNaturalPersonAccount() {
        return naturalPersonAccount;
    }

    public ControllerNaturalPersonAccount getControllerNaturalPersonAccount() {
        return controllerNaturalPersonAccount;
    }

    public LegalPersonAccount getLegalPersonAccount() {
        return legalPersonAccount;
    }

    public ControllerLegalPersonAccount getControllerLegalPersonAccount() {
        return controllerLegalPersonAccount;
    }

    public Scanner getScanner() {
        return scanner;
    }
}
