package org.bamappli.telfonibackendspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class TelfoniBackendSpringApplication {

    private static void loadEnvironment() {
        try (FileInputStream fis = new FileInputStream(".env")){
            Properties prop = new Properties();
            prop.load(fis);
            prop.forEach((key, value)->{
                System.setProperty(key.toString(), value.toString());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        loadEnvironment();
        SpringApplication.run(TelfoniBackendSpringApplication.class, args);
    }

}
