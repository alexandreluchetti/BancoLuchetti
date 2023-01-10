package br.com.alexandre.bancoLuchetti.controller;

import br.com.alexandre.bancoLuchetti.Main;
import br.com.alexandre.bancoLuchetti.model.account.Account;
import br.com.alexandre.bancoLuchetti.model.account.NaturalPersonAccount;
import br.com.alexandre.bancoLuchetti.model.account.Operation;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

/**
 *
 * @author Alexandre Luchetti
 */
public class ControllerNaturalPersonAccount extends ControllerAccount{
    
    private ArrayList<NaturalPersonAccount> naturalPersonAccounts;

    public ControllerNaturalPersonAccount() {
        this.naturalPersonAccounts = new ArrayList<NaturalPersonAccount>();
    }
    
    public ArrayList<NaturalPersonAccount> getArrayListNaturalPersonAccount() {
        return naturalPersonAccounts;
    }
    
    @Override
    public void withdraw(Main main, Account naturalPersonAccount, double withdrawTax) {
        if (withdrawTax <= 0.00) {
            double value0Tax = naturalPersonAccount.getBalance();

            main.getScanner().nextLine(); //limpando buffer
            boolean validatePasswordNaturalPersonAccount = validatePassword(main, naturalPersonAccount);
            if (validatePasswordNaturalPersonAccount == false) {
                System.out.println("\n!!! Senha incorreta !!!");
                return;
            }

            naturalPersonAccount.setBalance(naturalPersonAccount.getBalance() - value0Tax);
            System.out.println("\nSaque de R$ " + value0Tax + " efetuado com sucesso!");
            return;
            
        } else {
            System.out.println("\nTaxa para saque: " + (withdrawTax * 100) + "%");
            System.out.print("Digite o valor que deseja sacar: R$ ");
            double value = main.getScanner().nextDouble();

            main.getScanner().nextLine(); //limpando buffer
            boolean validatePasswordNaturalPersonAccount = validatePassword(main, naturalPersonAccount);
            if (validatePasswordNaturalPersonAccount == false) {
                System.out.println("\n!!! Senha incorreta !!!");
                return;
            }

            if ((value * (withdrawTax + 1)) <= naturalPersonAccount.getBalance()) {
                System.out.println("\nTaxa cobrada " + (withdrawTax * 100) + "% = R$ " + (withdrawTax * value));
                System.out.println("Valor do saque = R$ " + value);
                System.out.println("Total = R$ " + (value * (withdrawTax + 1)));
                naturalPersonAccount.setBalance(naturalPersonAccount.getBalance() - value);
                System.out.println("Saque de R$ " + value + " efetuado com sucesso!");
                
                LocalDate now = LocalDate.now();
                Operation newWithdraw = registerOperation(now , "Saque", value, naturalPersonAccount.getBalance());
                saveOperation(naturalPersonAccount.getOperations(), newWithdraw);
            } else {
                System.out.println("\n!!! Saldo insuficiente !!!\n");
            }
        }
    }

    public int validateNaturalPersonAccountData(Main main, ArrayList<NaturalPersonAccount> arrayListNaturalPersonAccount) {
        int value = -1;

        main.getScanner().nextLine();
        System.out.print("CPF: ");
        String cpf = main.getScanner().nextLine();
        
        System.out.print("Agência: ");
        String agency = main.getScanner().nextLine();

        System.out.print("Número da conta: ");
        String number = main.getScanner().nextLine();

        for (int cont = 0; cont < arrayListNaturalPersonAccount.size(); cont++) {
            if (    cpf.equals(arrayListNaturalPersonAccount.get(cont).getPersonalData().getCpf()) && 
                    agency.equals(arrayListNaturalPersonAccount.get(cont).getAgency()) && 
                    number.equals(arrayListNaturalPersonAccount.get(cont).getNumber())) {

                value = cont;
            }
        }

        return value;
    }
    
    public NaturalPersonAccount naturalPersonAccountRegistration
        (ArrayList<NaturalPersonAccount> naturalPersonAccounts, Main main) {
            
        System.out.println("\n### Cadastro de conta pessoa física (PF) ###");
        System.out.println("Dados do titular da conta\n");

        main.getScanner().nextLine(); //limpando buffer
        
        String cpf = null;
        boolean executing = false;
        do{
            System.out.print("CPF (somente números): ");
            cpf = main.getScanner().nextLine();
            executing = checkingCpf(cpf);
        }while(executing == false);

        System.out.print("Primeiro nome: ");
        String firstName = main.getScanner().nextLine();
        
        System.out.print("Sobrenome: ");
        String lastName = main.getScanner().nextLine();
        
        LocalDate dateOfBirth = dateOfBirth("Data de nascimento", main);
        
        int age = age(dateOfBirth);
        
        String gender = selectGender(main);

        main.getScanner().nextLine(); //limpando buffer
        System.out.print("Agência: ");
        String agency = main.getScanner().nextLine();

        System.out.print("Número da conta: ");
        String number = main.getScanner().nextLine();

        System.out.print("Saldo inicial: R$ ");
        double balance = main.getScanner().nextDouble();
        
        String password = registerPassword(main);
        
        NaturalPersonAccount naturalPersonAccount = new NaturalPersonAccount
        ("Conta Pessoa Física", agency, number, balance, 0.03, 0.05, password, new ArrayList<Operation>(), firstName, lastName, cpf, gender, dateOfBirth);
        
        naturalPersonAccount.getPersonalData().setFullName(firstName, lastName);
        naturalPersonAccount.getPersonalData().setAge(age);
        
        if(age(dateOfBirth) < 18){
            System.out.println("\n!!! Menores de 18 anos !!!");
            System.out.println("Necessário preencher as informações dos pais.");
            
            System.out.print("Nome do pai: ");
            String fathersName = main.getScanner().nextLine();
            
            System.out.print("CPF do pai: ");
            String fathersCpf = main.getScanner().nextLine();
            
            System.out.print("Nome da mãe: ");
            String mothersName = main.getScanner().nextLine();
            
            System.out.print("CPF da mãe: ");
            String mothersCpf = main.getScanner().nextLine();
            
            naturalPersonAccount.getPersonalData().setFathersName(fathersName);
            naturalPersonAccount.getPersonalData().setFathersCpf(fathersCpf);
            naturalPersonAccount.getPersonalData().setMothersName(mothersName);
            naturalPersonAccount.getPersonalData().setMothersCpf(mothersCpf);
        }
        
        return naturalPersonAccount;
    }
        
    private boolean checkingCpf(String cpf){
        for(int cont = 0; cont < naturalPersonAccounts.size(); cont++){
            if(cpf.equals(naturalPersonAccounts.get(cont).getPersonalData().getCpf())){
                System.out.println("!!! CPF já cadastrado !!!\n");
                return false;
            }
        }
        
        return true;
    }
    
    private int age(LocalDate dateOfBirth){
        LocalDate localDate = LocalDate.now();
        Period period = Period.between(dateOfBirth, localDate);
        
        return period.getYears();
    }
        
    private String selectGender(Main main) {
        String gender = null;
        System.out.println("Gênero ");

        boolean executing = false;
        while (executing == false) {
            System.out.println("\t1 - Masculino");
            System.out.println("\t2 - Feminino");
            System.out.print("Digite a opção desejada: ");
            int option = main.getScanner().nextInt();

            if (option == 1) {
                gender = "Masculino";
                executing = true;
            } else if (option == 2) {
                gender = "Feminino";
                executing = true;
            } else {
                System.out.println("!!! Opção inválida !!!\n");
                executing = false;
            }
        }
        
        return gender;
    }
    
    public void saveNaturalPersonAccount(ArrayList<NaturalPersonAccount> arrayList, Object object){
        arrayList.add((NaturalPersonAccount) object);
    }
    
    public void displayNaturalPersonAccount(ArrayList<NaturalPersonAccount> naturalPersonAccounts) { 
        if(naturalPersonAccounts.size() <= 0){
            System.out.println("\n!!! Nenhuma conta cadastrada até o momento !!!");
            return;
        }
        
        System.out.println("\n### Consulta de conta pessoa física (PF) ###");

        for (int cont = 0; cont < naturalPersonAccounts.size(); cont++) {
            System.out.println("\n\tNome completo: " + naturalPersonAccounts.get(cont).getPersonalData().getFullName());
            System.out.println("\tData de nascimento: " + naturalPersonAccounts.get(cont)
                    .getPersonalData().getDateOfBirth());
            System.out.println("\tIdade: " + naturalPersonAccounts.get(cont).getPersonalData().getAge() + " anos.");
            System.out.println("\tGênero: " + naturalPersonAccounts.get(cont).getPersonalData().getGender());
            System.out.println("\tAgência: " + naturalPersonAccounts.get(cont).getAgency());
            System.out.println("\tNúmero da conta: " + naturalPersonAccounts.get(cont).getNumber());
            
            if (naturalPersonAccounts.get(cont).getPersonalData().getAge() < 18) {
                System.out.println("\n\tInformações dos pais");
                System.out.println("\tNome do pai: " + naturalPersonAccounts.get(cont).getPersonalData().getFathersName());
                System.out.println("\tNome da mãe: " + naturalPersonAccounts.get(cont).getPersonalData().getMothersName());
            }
        }

        System.out.println("==========================================");
    }
    
    public static boolean accessingNaturalPersonAccount(Main main) {
        System.out.println("\n### Insira os dados da sua conta ###");

        int validateDataNaturalPersonAccount
                = main.getControllerNaturalPersonAccount()
                        .validateNaturalPersonAccountData(
                                main, main.
                                        getControllerNaturalPersonAccount().getArrayListNaturalPersonAccount());
        
        if (validateDataNaturalPersonAccount < 0) {
            System.out.println("\n!!! Dados inválidos !!!");
            return false;
        }

        System.out.println("\nBem vindo "
                + main.getControllerNaturalPersonAccount()
                        .getArrayListNaturalPersonAccount()
                        .get(validateDataNaturalPersonAccount)
                        .getPersonalData()
                        .getFirstName());

        boolean validatePasswordNaturalPersonAccount
                = main.getControllerNaturalPersonAccount()
                        .validatePassword(main,
                                main.getControllerNaturalPersonAccount()
                                        .getArrayListNaturalPersonAccount()
                                        .get(validateDataNaturalPersonAccount));

        if (validatePasswordNaturalPersonAccount == false) {
            System.out.println("\n!!! Dados inválidos !!!");
            return false;
        }

        boolean executingNaturalPersonAccount;
        do {            
            executingNaturalPersonAccount = main.getControllerNaturalPersonAccount()
                    .naturalPersonAccountOperationsMenu(
                            main,
                            main.getControllerNaturalPersonAccount()
                                    .getArrayListNaturalPersonAccount()
                                    .get(validateDataNaturalPersonAccount));
            
        } while (executingNaturalPersonAccount);
        
        return true;
    }
    
    protected int validateTransferToNaturalPersonAccount(Main main, double transferValue) {
        int destinationAccountIndex = main.getControllerNaturalPersonAccount()
                .validateNaturalPersonAccountData(
                        main, main.getControllerNaturalPersonAccount()
                                .getArrayListNaturalPersonAccount());

        if (destinationAccountIndex < 0) {
            return destinationAccountIndex;
        }

        main.getControllerNaturalPersonAccount()
                .getArrayListNaturalPersonAccount()
                .get(destinationAccountIndex)
                .setBalance(main.getControllerNaturalPersonAccount()
                        .getArrayListNaturalPersonAccount()
                        .get(destinationAccountIndex).getBalance() + transferValue);

        return destinationAccountIndex;
    }

    public boolean naturalPersonAccountOperationsMenu(Main main, NaturalPersonAccount naturalPersonAccount) {        
        System.out.println("\n### Conta de " + naturalPersonAccount.getPersonalData().getFirstName() + " ###");
        System.out.println("O saldo da conta é de R$ " + naturalPersonAccount.getBalance());
        
        System.out.println("\n0 - Sair da sua conta");
        System.out.println("1 - Extrato");
        System.out.println("2 - Sacar");
        System.out.println("3 - Depositar");
        System.out.println("4 - Transferir");
//        System.out.println("5 - Exluir conta");
        System.out.print("Digite a opção desejada: ");
        int userEntrty = main.getScanner().nextInt();

        switch (userEntrty) {
            case 0:
                System.out.println("\n===================================");
                System.out.println("!!! VOCÊ SAIU DA SUA CONTA !!!");
                return false;
                
            case 1:
                statement(main, naturalPersonAccount);
                break;    
                
            case 2:
                withdraw(main, naturalPersonAccount, naturalPersonAccount.getWithdrawTax());
                break;

            case 3:
                deposit(main, naturalPersonAccount);
                break;
                
            case 4:
                transfer(main, naturalPersonAccount, naturalPersonAccount.getTransferTax());
                break;

            default:
                System.out.println("Você digitou um valor inválido! Tente novamente.");
                break;
        }
        
        return true;
    }
    
    public boolean removeNaturalPersonAccount(Main main) {        
        int validateDataNaturalPersonAccount
                = main.getControllerNaturalPersonAccount()
                        .validateNaturalPersonAccountData(
                                main, main.
                                        getControllerNaturalPersonAccount().getArrayListNaturalPersonAccount());
        if (validateDataNaturalPersonAccount < 0) {
            System.out.println("\n!!! Dados inválidos !!!");
            return false;
        }
        
        double naturalPersonAccountBalance = main.getControllerNaturalPersonAccount()
                .getArrayListNaturalPersonAccount()
                .get(validateDataNaturalPersonAccount)
                .getBalance();
        
        if(naturalPersonAccountBalance > 0){
            System.out.println("\nSeu saldo é de R$ " + naturalPersonAccountBalance);
            System.out.println("Você precisa sacar este valor antes de excluir a sua conta!\n");
            
            System.out.println("Deseja sacar esse valor sem taxa?");
            System.out.println("0 - Não");
            System.out.println("1 - Sim");
            System.out.print("Digite a opção desejada: ");
            int userEntry = main.getScanner().nextInt();
            
            switch(userEntry){
                case 0:
                    return false;
                    
                case 1:
                    withdraw(main, 
                            main.getControllerNaturalPersonAccount()
                            .getArrayListNaturalPersonAccount()
                            .get(validateDataNaturalPersonAccount), 0.00);
            }
        }

        System.out.println("\nTem certeza que deseja excluir a sua conta, " + 
                main.getControllerNaturalPersonAccount()
                        .getArrayListNaturalPersonAccount()
                        .get(validateDataNaturalPersonAccount)
                        .getPersonalData()
                        .getFirstName() + "?");
        System.out.println("0 - Não");
        System.out.println("1 - Sim");
        System.out.print("Digite a opção desejada: ");
        int userEntry = main.getScanner().nextInt();
        if (userEntry == 0) {
            return false;
        }

        main.getScanner().nextLine(); //limpando buffer
        boolean validatePasswordNaturalPersonAccount
                = main.getControllerNaturalPersonAccount()
                        .validatePassword(main,
                                main.getControllerNaturalPersonAccount()
                                        .getArrayListNaturalPersonAccount()
                                        .get(validateDataNaturalPersonAccount));
        if (validatePasswordNaturalPersonAccount == false) {
            System.out.println("\n!!! Dados inválidos !!!");
            return false;
        }

        main.getControllerNaturalPersonAccount()
                .getArrayListNaturalPersonAccount()
                .remove(validateDataNaturalPersonAccount);

        return true;
    }
}