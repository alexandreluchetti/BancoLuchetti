package br.com.alexandre.bancoLuchetti.controller;

import br.com.alexandre.bancoLuchetti.Main;
import br.com.alexandre.bancoLuchetti.model.account.Account;
import br.com.alexandre.bancoLuchetti.model.account.Operation;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 *
 * @author Alexandre Luchetti
 */
public abstract class ControllerAccount{

    public ControllerAccount() {
    }
    
    protected abstract void withdraw(Main main, Account account, double withdrawTax);
    
    protected LocalDate dateOfBirth(String message, Main main) {
        System.out.print(message + " (dd/mm/aaaa): ");
        String stringDate = main.getScanner().nextLine();
        
        LocalDate dateOfBirth = LocalDate.of(Integer.parseInt(stringDate.split("/")[2]),
                Integer.parseInt(stringDate.split("/")[1]),
                Integer.parseInt(stringDate.split("/")[0]));
        
        return dateOfBirth;
    }
    
    protected void statement(Main main, Account account){
        if(account.getOperations().size() <= 0){
            System.out.println("\n!!! Sem extrato até o momento !!!");
            return;
        }
        
        System.out.println("");
        
        main.getScanner().nextLine(); //limpando buffer
        boolean validateAccount = false;
        validateAccount = validatePassword(main, account);
        
        if(validateAccount == false){
            System.out.println("\n!!! Senha inválida !!!");
            return;
        }
        
        System.out.println("\n### Estrato Bancário ###");
        
        for(int counter =  0; counter < account.getOperations().size(); counter++){
            System.out.println("\nData: " + account.getOperations().get(counter).getLocalDate());
            
            if(account.getOperations().get(counter).getOperation().equals("Deposito")){
                System.out.println("Operação: " + account.getOperations().get(counter).getOperation());
                System.out.println("Valor: R$ " + account.getOperations().get(counter).getValue() + " (+)");
            } else if(account.getOperations().get(counter).getOperation().equals("Saque")){
                System.out.println("Operação: " + account.getOperations().get(counter).getOperation());
                System.out.println("Valor: R$ " + account.getOperations().get(counter).getValue() + " (-)");
            } else if(account.getOperations().get(counter).getOperation().equals("Transferência")){
                System.out.println("Operação: " + account.getOperations().get(counter).getOperation() + " para a conta de " + account.getOperations().get(counter).getDestinationAccount());
                System.out.println("Valor: R$ " + account.getOperations().get(counter).getValue() + " (-)");
            }
            
            System.out.println("Saldo parcial: R$ " + account.getOperations().get(counter).getPartialBalance());
        }
        System.out.println("\nSaldo atual: R$ " + account.getBalance());
        System.out.println("==========================================");
    }
        
    protected void deposit(Main main, Account account) {
        System.out.print("\nDigite o valor que deseja depositar: R$ ");
        double value = main.getScanner().nextDouble();
        if (value < 0) {
            System.out.println("\n!!! Impossível depositar valor 0 ou negativo !!!");
            return;
        }

        main.getScanner().nextLine(); //limpando buffer
        boolean validatePassword = validatePassword(main, account);
        if (validatePassword == false) {
            System.out.println("\n!!! Senha incorreta !!!");
            return;
        }

        account.setBalance(account.getBalance() + value);
        
        LocalDate now = LocalDate.now();
        Operation newDeposit = registerOperation(now , "Deposito", value, account.getBalance());
        saveOperation(account.getOperations(), newDeposit);
        
        System.out.println("\nDepósito de R$ " + value
                + " na conta de nº " + account.getNumber() + " efetuado com sucesso!");
    }

    public boolean validatePassword(Main main, Account account){
        System.out.print("Digite a sua senha: ");
        String password = main.getScanner().nextLine();
        
        if(password.equals(account.getPassword())){
            return true;
        }

        return false;
    }
    
    protected String registerPassword(Main main) {
        System.out.println("\nCadastro de senha bancária");

        String password = "";
        boolean executing = false;
        
        main.getScanner().nextLine();
        while (executing == false) {
            System.out.print("Digite a senha que deseja cadastrar: ");
            String password1 = main.getScanner().nextLine();

            System.out.print("Repita a senha: ");
            String password2 = main.getScanner().nextLine();
            
            if (password1.equals(password2)) {
                password = password1;
                executing = true;
            } else {
                System.out.println("!!! As senhas digitadas são diferentes !!!\n");
            }
        }
        
        return password;
    }
    
    protected void transfer(Main main, Account sourceAccount, double transferTax) {
        System.out.println("\n### Transferência entre contas ###");
        System.out.println("!!! Não é permitido transferir menos de 100 reais !!!");
        System.out.println("Taxa de transferência de " + (transferTax * 100) + "%");

        System.out.print("\nDigite o valor da transferência: R$ ");
        double transferValue = main.getScanner().nextDouble();

        if (transferValue < 100) {
            System.out.println("!!! Não é permitido transferir menos de 100 reais !!!");
            return;
        } else if (sourceAccount.getBalance() < (transferValue + (transferValue * transferTax) )) {
            System.out.println("\n!!! Saldo insuficiente !!!");
            return;
        }

        System.out.println("\nPara qual tipo de conta você deseja transferir?");
        System.out.println("1 - Conta Pessoa Física");
        System.out.println("2 - Conta Pessoa Jurídica");
        System.out.print("Digite a opção desejada: ");
        int optionAccountType = main.getScanner().nextInt();
        int destinationAccountIndex = -1;
        String destinationAccountName;

        System.out.println("\n### Insira os dados da conta de DESTINO ###");
        switch (optionAccountType) {
            case 1:
                destinationAccountIndex = main.getControllerNaturalPersonAccount()
                        .validateTransferToNaturalPersonAccount(main, transferValue);
                if(destinationAccountIndex < 0){
                    System.out.println("!!! Dados inválidos !!!");
                    return;
                } else if(main.getControllerNaturalPersonAccount()
                        .getArrayListNaturalPersonAccount()
                        .get(destinationAccountIndex) == sourceAccount){
                    System.out.println("\n!!! Você não pode transferir para a sua própria conta !!!");
                    return;
                }
                
                destinationAccountName = main.getControllerNaturalPersonAccount()
                        .getArrayListNaturalPersonAccount()
                        .get(destinationAccountIndex)
                        .getPersonalData()
                        .getFirstName();
                break;
                
            case 2:
                destinationAccountIndex = main.getControllerLegalPersonAccount()
                        .validateTransferToLegalPersonAccount(main, transferValue);
                if(destinationAccountIndex < 0){
                    System.out.println("\n!!! Dados inválidos !!!");
                    return;
                } else if(main.getControllerLegalPersonAccount()
                        .getArrayListLegalPersonAccount()
                        .get(destinationAccountIndex) == sourceAccount){
                    System.out.println("\n!!! Você não pode transferir para a sua própria conta !!!");
                    return;
                }
                
                destinationAccountName = main.getControllerLegalPersonAccount()
                        .getArrayListLegalPersonAccount()
                        .get(destinationAccountIndex)
                        .getCompanyData()
                        .getCompanyName();
                break;
                
            default:
                return;
        }
        
        System.out.println("\nTransferência para a conta de " + destinationAccountName + ", correto?");
        System.out.println("0 - Não");
        System.out.println("1 - Sim");
        System.out.print("Digite a opção desejada: ");
        int optionAccountName = main.getScanner().nextInt();
        
        if(optionAccountName == 1){
            main.getScanner().nextLine();
            boolean validatePasswordSourceAccount = validatePassword(main, sourceAccount);

            if (validatePasswordSourceAccount == false) {
                System.out.println("\n!!! Senha incorreta !!!");
                return;
            }

            sourceAccount.setBalance(sourceAccount.getBalance() - (transferValue + (transferValue * transferTax)));

            LocalDate now = LocalDate.now();
            Operation newTransfer = registerOperation(now , "Transferência", transferValue, sourceAccount.getBalance());
            newTransfer.setDestinationAccount(destinationAccountName);
            saveOperation(sourceAccount.getOperations(), newTransfer);

            System.out.println("\nValor da taxa: R$ " + (transferValue * transferTax));
            System.out.println("Transferência de R$ " + transferValue + " para a conta de " + destinationAccountName + " realizada com sucesso!");
        } else {
            return;
        }        
    }
    
    protected Operation registerOperation(LocalDate localDate, String operation, double value, double partialBalance){
        ControllerOperation newOperation = new ControllerOperation(localDate, operation, value, partialBalance);
        return newOperation;
    }
    
    protected  ArrayList<Operation> saveOperation(ArrayList<Operation> operations, Operation operation){
        operations.add(operation);
        return operations;
    }
}