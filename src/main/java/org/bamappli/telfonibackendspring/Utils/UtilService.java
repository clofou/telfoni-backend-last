package org.bamappli.telfonibackendspring.Utils;


import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilService {

    public static boolean isValidEmail(String email) {
        final String EMAIL_PATTERN =
                "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if (email == null) {
            return true;
        }
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }

    public static boolean isValidPhone(String phone) {
        final String PHONE_PATTERN = "^[+]?[0-9]{10,13}$";
        final Pattern pattern = Pattern.compile(PHONE_PATTERN);
        if (phone == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }


    public static boolean isValidPassword(String password) {
        return password == null || password.length() < 6;
    }
    
    public static String generateRandomPseudo() {
        String[] pseudoList = {"Nyetim", "Bravo", "Kpous", "DELTA", "Kpou", "Kpoum", "Kpouma", "Kpoumata", "Kpassima"};
        Random random = new Random();
        int randomIndex = random.nextInt(pseudoList.length);
        return pseudoList[randomIndex];
    }

    public static void checkEmailAndPassword(String email, String password){
        if (isValidEmail(email)) {
            throw new IllegalArgumentException("Email invalide");
        }
        if (isValidPassword(password)){
            throw new IllegalArgumentException("Le Mot de passe doit comporter plus de 6 caracteres");
        }
    }

    public static String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Générer un code à 6 chiffres
        return String.valueOf(code);
    }

    
}
