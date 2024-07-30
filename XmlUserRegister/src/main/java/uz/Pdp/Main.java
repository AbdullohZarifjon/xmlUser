package uz.Pdp;

import uz.Pdp.model.User;
import uz.Pdp.service.UserService;

import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static final Scanner scannerStr = new Scanner(System.in);
    private static Scanner scannerInt = new Scanner(System.in);
    private static UserService userService = new UserService();
    public static void main(String[] args) {
        int stepCode = 100;
        while (stepCode != 0) {
            System.out.println("1. Register \t 2. SignIn      0. Exit");
            stepCode = scannerInt.nextInt();
            switch (stepCode) {
                case 1 -> {
                    register();
                } case 2 -> {
                    signIn();
                }
            }
        }
    }

    private static void register() {
        System.out.println("Enter your name: ");
        String name = scannerStr.nextLine();

        System.out.println("Enter your surname: ");
        String surName = scannerStr.nextLine();

        System.out.println("Enter your age: ");
        int age = scannerInt.nextInt();

        System.out.println("Enter your password: ");
        String password = scannerStr.nextLine();
        User user = User.builder()
                .id(UUID.randomUUID())
                .lastname(name)
                .firstName(surName)
                .password(password)
                .age(age)
                .build();
        User user1 = userService.register(user);
        if (user1 != null) {
            System.out.println("You have successfully registered");
        } else {
            System.out.println("The user is in the list! ");
        }
    }

    private static void signIn() {
        System.out.println("Enter your lastname: ");
        String lastname = scannerStr.nextLine();

        System.out.println("Enter your password: ");
        String password = scannerStr.nextLine();

        User user = userService.login(lastname, password);
        if (user != null) {
            System.out.println("Siz sahifaga muvaffaqiyyatli kirdingiz!");
            System.out.println("Kod o'z nihoyasiga yetdi!");
        } else {
            System.out.println("No such user exists!...");
        }
    }

}