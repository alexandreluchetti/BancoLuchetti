package br.com.alexandre.bancoLuchetti.controller;

import br.com.alexandre.bancoLuchetti.Main;
import br.com.alexandre.bancoLuchetti.model.account.Account;
import br.com.alexandre.bancoLuchetti.model.account.LegalPersonAccount;
import br.com.alexandre.bancoLuchetti.model.account.Operation;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Alexandre Luchetti
 */
public class ControllerLegalPersonAccount extends ControllerAccount{
    
    public ArrayList<LegalPersonAccount> legalPersonAccounts;

    public ControllerLegalPersonAccount() {
        this.legalPersonAccounts = new ArrayList<LegalPersonAccount>();
    }
    
    

    public ArrayList<LegalPersonAccount> getArrayListLegalPersonAccount() {
        return legalPersonAccounts;
    }
    
    @Override
    public void withdraw(Main main, Account legalPersonAccount, double withdrawTax) {
        if (withdrawTax <= 0.00) {
            double value0Tax = legalPersonAccount.getBalance();

            main.getScanner().nextLine(); //limpando buffer
            boolean validatePasswordLegalPersonAccount = validatePassword(main, legalPersonAccount);
            if (validatePasswordLegalPersonAccount == false) {
                System.out.println("\n!!! Senha incorreta !!!");
                return;
            }

            legalPersonAccount.setBalance(legalPersonAccount.getBalance() - value0Tax);
            System.out.println("\nSaque de R$ " + value0Tax + " efetuado com sucesso!");
            return;
            
        } else {
            System.out.println("\nTaxa para saque: " + (withdrawTax * 100) + "%");
            System.out.print("Digite o valor que deseja sacar: R$ ");
            double value = main.getScanner().nextDouble();

            main.getScanner().nextLine(); //limpando buffer
            boolean validatePasswordLegalPersonAccount = validatePassword(main, legalPersonAccount);
            if (validatePasswordLegalPersonAccount == false) {
                System.out.println("\n!!! Senha incorreta !!!");
                return;
            }

            if ((value * (withdrawTax + 1)) <= legalPersonAccount.getBalance()) {
                System.out.println("\nTaxa cobrada " + (withdrawTax * 100) + "% = R$ " + (withdrawTax * value));
                System.out.println("Valor do saque = R$ " + value);
                System.out.println("Total = R$ " + (value * (withdrawTax + 1)));
                legalPersonAccount.setBalance(legalPersonAccount.getBalance() - value);
                System.out.println("Saque de R$ " + value + " efetuado com sucesso!");
                
                LocalDate now = LocalDate.now();
                Operation newWithdraw = registerOperation(now , "Saque", value, legalPersonAccount.getBalance());
                saveOperation(legalPersonAccount.getOperations(), newWithdraw);
            } else {
                System.out.println("\n!!! Saldo insuficiente !!!\n");
            }
        }
    }
    
    public int validateLegalPersonAccountData(Main main, ArrayList<LegalPersonAccount> arrayListLegalPersonAccount) {
        int value = -1;

        System.out.print("CNPJ: ");
        String cnpj = main.getScanner().next();
        
        System.out.print("Agência: ");
        String agency = main.getScanner().next();

        System.out.print("Número da conta: ");
        String number = main.getScanner().next();

        for (int cont = 0; cont < arrayListLegalPersonAccount.size(); cont++) {
            if (    cnpj.equals(arrayListLegalPersonAccount.get(cont).getCompanyData().getCnpj()) &&
                    agency.equals(arrayListLegalPersonAccount.get(cont).getAgency()) &&
                    number.equals(arrayListLegalPersonAccount.get(cont).getNumber())) {

                value = cont;
            }
        }

        return value;
    }
    
    public LegalPersonAccount legalPersonAccountRegistration(ArrayList<LegalPersonAccount> legalPersonAccounts, Main main) {
        System.out.println("\n### Cadastro de conta pessoa jurídica (PJ) ###");
        System.out.println("Dados do titular da conta\n");

        main.getScanner().nextLine(); //limpando buffer
        
        String cnpj = null;
        boolean executing = false;
        do{
            System.out.print("CNPJ (somente números): ");
            cnpj = main.getScanner().nextLine();
            executing = checkingCnpj(cnpj);
        }while(executing == false);

        System.out.print("Razão social: ");
        String corporateName = main.getScanner().nextLine();

        System.out.print("Nome fantasia: ");
        String commercialName = main.getScanner().nextLine();
        
        LocalDate companyOpeningDate = dateOfBirth("Data de abertura", main);

        System.out.print("Agência: ");
        String agency = main.getScanner().nextLine();

        System.out.print("Número da conta: ");
        String number = main.getScanner().nextLine();

        System.out.print("Saldo inicial: R$ ");
        double balance = main.getScanner().nextDouble();
        
        System.out.print("Linha de crédito: R$ ");
        double creditLine = main.getScanner().nextDouble();
        
        String password = registerPassword(main);
        
        LegalPersonAccount legalPersonAccountnew = new  LegalPersonAccount
        ("Conta Pessoa Jurídica", agency, number, balance, 0.05, 0.08, password, new ArrayList<Operation>(), corporateName, commercialName, cnpj, companyOpeningDate);
        
        legalPersonAccountnew.setCreditLine(creditLine);
        
        return legalPersonAccountnew;
    }
    
    private boolean checkingCnpj(String cnpj){
        for(int cont = 0; cont < legalPersonAccounts.size(); cont++){
            if(cnpj.equals(legalPersonAccounts.get(cont).getCompanyData().getCnpj())){
                System.out.println("!!! CNPJ já cadastrado !!!\n");
                return false;
            }
        }
        
        return true;
    }
    
    public void saveLegalPersonAccount(ArrayList<LegalPersonAccount> arrayList, Object object){
        arrayList.add((LegalPersonAccount) object);
    }
    
    public void displayLegalPersonAccount(ArrayList<LegalPersonAccount> legalPersonAccounts) {
        if(legalPersonAccounts.size() <= 0){
            System.out.println("\n!!! Nenhuma conta cadastrada até o momento !!!");
            return;
        }
        
        System.out.println("\n### Consulta de conta pessoa jurídica (PJ) ###");

        for (int cont = 0; cont < legalPersonAccounts.size(); cont++) {
            System.out.println("\n\tRazão Social: " + legalPersonAccounts.get(cont).getCompanyData().getCompanyName());
            System.out.println("\tNome Fantasia: " + legalPersonAccounts.get(cont).getCompanyData().getCommercialName());
            System.out.println("\tData de abertura: " + legalPersonAccounts.get(cont).getCompanyData().getCompanyOpeningDate());
            System.out.println("\tAgência: " + legalPersonAccounts.get(cont).getAgency());
            System.out.println("\tNúmero da conta: " + legalPersonAccounts.get(cont).getNumber());
            System.out.println("\tLinha de crédito: " + legalPersonAccounts.get(cont).getCreditLine());
        }

        System.out.println("==========================================");
    }
    
    public static boolean accessingLegalPersonOperationsMenu(Main main) {
        System.out.println("\n### Insira os dados da sua conta ###");

        int validateDataLegalPersonAccount
                = main.getControllerLegalPersonAccount()
                        .validateLegalPersonAccountData(main, 
                                main.getControllerLegalPersonAccount()
                                         .getArrayListLegalPersonAccount());

        if (validateDataLegalPersonAccount < 0) {
            System.out.println("\n!!! Dados inválidos !!!");
            return false;
        }
        
        System.out.println("\nBem vindo "
                + main.getControllerLegalPersonAccount()
                        .getArrayListLegalPersonAccount()
                        .get(validateDataLegalPersonAccount)
                        .getCompanyData()
                        .getCommercialName());

        main.getScanner().nextLine(); //limpando buffer
        boolean validatePasswordLegalPersonAccount
                = main.getControllerLegalPersonAccount()
                        .validatePassword(main,
                                main.getControllerLegalPersonAccount()
                                        .getArrayListLegalPersonAccount()
                                        .get(validateDataLegalPersonAccount));

        if (validatePasswordLegalPersonAccount == false) {
            System.out.println("\n!!! Dados inválidos !!!");
            return false;
        }

        boolean executingLegalPersonAccount;
        do {           
            executingLegalPersonAccount = main.getControllerLegalPersonAccount()
                    .legalPersonAccountAccess(
                            main,
                            main.getControllerLegalPersonAccount()
                                    .getArrayListLegalPersonAccount()
                                    .get(validateDataLegalPersonAccount));
            
        } while (executingLegalPersonAccount);
        
        return true;
    }
    
    public boolean legalPersonAccountAccess(Main main, LegalPersonAccount legalPersonAccount) {        
        System.out.println("\n### Conta de " + legalPersonAccount.getCompanyData().getCommercialName() + " ###");
        System.out.println("O saldo da conta é de R$ " + legalPersonAccount.getBalance());
        
        System.out.println("\n0 - Sair da sua conta");
        System.out.println("1 - Extrato");
        System.out.println("2 - Sacar");
        System.out.println("3 - Depositar");
        System.out.println("4 - Transferir");
        System.out.print("Digite a opção desejada: ");
        int userEntrty = main.getScanner().nextInt();

        switch (userEntrty) {
            case 0:
                System.out.println("\n===================================");
                System.out.println("!!! VOCÊ SAIU DA SUA CONTA !!!");
                return false;
                
            case 1:
                statement(main, legalPersonAccount);
                break;
                
            case 2:
                withdraw(main, legalPersonAccount, legalPersonAccount.getWithdrawTax());
                break;

            case 3:                
                deposit(main, legalPersonAccount);
                break;
                
            case 4:
                transfer(main, legalPersonAccount, legalPersonAccount.getTransferTax());
                break;

            default:
                System.out.println("Você digitou um valor inválido! Tente novamente.");
                break;
        }
        
        return true;
    }
    
    protected int validateTransferToLegalPersonAccount(Main main, double transferValue) {
        int destinationAccountIndex = main.getControllerLegalPersonAccount()
                .validateLegalPersonAccountData(
                        main, main.getControllerLegalPersonAccount()
                                .getArrayListLegalPersonAccount());

        if (destinationAccountIndex < 0) {
            return destinationAccountIndex;
        }
        
        main.getControllerLegalPersonAccount()
                .getArrayListLegalPersonAccount()
                .get(destinationAccountIndex)
                .setBalance(main.getControllerLegalPersonAccount()
                        .getArrayListLegalPersonAccount()
                        .get(destinationAccountIndex)
                        .getBalance() + transferValue);

        return destinationAccountIndex;
    }
    
    public boolean removeLegalPersonAccount(Main main) {
        int validateDataLegalPersonAccount
                = main.getControllerLegalPersonAccount()
                        .validateLegalPersonAccountData(main,
                                main.getControllerLegalPersonAccount()
                                        .getArrayListLegalPersonAccount());
        if (validateDataLegalPersonAccount < 0) {
            System.out.println("\n!!! Dados inválidos !!!");
            return false;
        }
        
        double legalPersonAccountBalance = main.getControllerLegalPersonAccount()
                .getArrayListLegalPersonAccount()
                .get(validateDataLegalPersonAccount)
                .getBalance();
        
        if (legalPersonAccountBalance > 0) {
            System.out.println("\nSeu saldo é de R$ " + legalPersonAccountBalance);
            System.out.println("Você precisa sacar este valor antes de excluir a sua conta!\n");

            System.out.println("Deseja sacar esse valor sem taxa?");
            System.out.println("0 - Não");
            System.out.println("1 - Sim");
            System.out.print("Digite a opção desejada: ");
            int userEntry = main.getScanner().nextInt();

            switch (userEntry) {
                case 0:
                    return false;

                case 1:
                    withdraw(main,
                            main.getControllerLegalPersonAccount()
                                    .getArrayListLegalPersonAccount()
                                    .get(validateDataLegalPersonAccount), 0.00);
            }
        }

        System.out.println("\nTem certeza que deseja excluir a sua conta, " + 
                main.getControllerLegalPersonAccount()
                        .getArrayListLegalPersonAccount()
                        .get(validateDataLegalPersonAccount)
                        .getCompanyData()
                        .getCommercialName() + "?");
        System.out.println("0 - Não");
        System.out.println("1 - Sim");
        System.out.print("Digite a opção desejada: ");
        int userEntry = main.getScanner().nextInt();
        if (userEntry == 0) {
            return false;
        }
        
        main.getScanner().nextLine(); //limpando buffer
        boolean validatePasswordLegalPersonAccount
                = main.getControllerLegalPersonAccount()
                        .validatePassword(main,
                                main.getControllerLegalPersonAccount()
                                        .getArrayListLegalPersonAccount()
                                        .get(validateDataLegalPersonAccount));
        if (validatePasswordLegalPersonAccount == false) {
            System.out.println("\n!!! Dados inválidos !!!");
            return false;
        }

        main.getControllerLegalPersonAccount()
                .getArrayListLegalPersonAccount()
                .remove(validateDataLegalPersonAccount);

        return true;
    }
}