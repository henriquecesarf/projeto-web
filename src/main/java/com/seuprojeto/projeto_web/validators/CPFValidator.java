package com.seuprojeto.projeto_web.validators;

import java.util.InputMismatchException;

public class CPFValidator {

    public static boolean isValidCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;  
        }

        try {
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int firstVerifierDigit = 11 - (sum % 11);
            if (firstVerifierDigit >= 10) firstVerifierDigit = 0;

            if (firstVerifierDigit != Character.getNumericValue(cpf.charAt(9))) return false;

            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int secondVerifierDigit = 11 - (sum % 11);
            if (secondVerifierDigit >= 10) secondVerifierDigit = 0;

            return secondVerifierDigit == Character.getNumericValue(cpf.charAt(10));
        } catch (InputMismatchException e) {
            return false;
        }
    }
}
