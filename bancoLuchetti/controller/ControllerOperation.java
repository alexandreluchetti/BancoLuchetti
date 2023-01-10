package br.com.alexandre.bancoLuchetti.controller;

import br.com.alexandre.bancoLuchetti.model.account.Operation;
import java.time.LocalDate;

/**
 *
 * @author Alexandre Luchetti
 */
public class ControllerOperation extends Operation{
    
    public ControllerOperation(LocalDate localDate, String operation, double value, double partialBalance) {
        super(localDate, operation, value, partialBalance);
    }
    
}
