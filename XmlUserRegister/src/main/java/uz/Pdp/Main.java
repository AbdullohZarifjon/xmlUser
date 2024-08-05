package uz.Pdp;

import uz.Pdp.controller.dto.P2PRequestDto;
import uz.Pdp.model.Card;
import uz.Pdp.model.Transaction;
import uz.Pdp.model.User;
import uz.Pdp.model.enums.XmlJson;
import uz.Pdp.service.CardService;
import uz.Pdp.service.JsonOrXmlService;
import uz.Pdp.service.TransactionService;
import uz.Pdp.service.UserService;
import uz.Pdp.service.serviseUtil.XmlJsonUtil;
import uz.Pdp.service.serviseUtil.XmlUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static final Scanner scannerStr = new Scanner(System.in);
    private static final Scanner scannerInt = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final CardService cardService = new CardService();
    private static final TransactionService transactionService = new TransactionService(cardService);
    private static final JsonOrXmlService jsonOrXmlService = new JsonOrXmlService();

    public static void main(String[] args) throws ParseException {
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

        System.out.println("Enter your username: ");
        String surName = scannerStr.nextLine();

        System.out.println("Enter your age: ");
        int age = scannerInt.nextInt();

        System.out.println("Enter your password: ");
        String password = scannerStr.nextLine();
        User user = User.builder()
                .id(UUID.randomUUID())
                .name(name)
                .userName(surName)
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

    private static void signIn() throws ParseException {
        System.out.println("Enter your username: ");
        String username = scannerStr.nextLine();

        System.out.println("Enter your password: ");
        String password = scannerStr.nextLine();

        User user = userService.login(username, password);
        if (user != null) {
            System.out.println("Siz sahifaga muvaffaqiyyatli kirdingiz!");
            signIn2(user);
        } else {
            System.out.println("No such user exists!...");
        }
    }

    private static void signIn2(User user) throws ParseException {
        int stepCode = 100;
        while (stepCode != 0) {
            System.out.println("1. Add Card        2. Update password card     3. List Card");
            System.out.println("4. Transaction     5. Json or Xml              6. Delete Card");
            stepCode = scannerInt.nextInt();
            switch (stepCode) {
                case 1 -> {
                    addCard(user);
                } case 2 -> {
                    updateCard(user);
                } case 3 -> {
                    listCard(user);
                } case 4 -> {
                    transaction(user);
                } case 5 -> {
                    jsonXml(user);
                } case 6 -> {
                    deleteCard(user);
                }
            }
        }
    }

    private static void addCard(User user) {
        System.out.println("Enter the name of the card: ");
        String cardName = scannerStr.nextLine();

        System.out.println("Enter the card number: ");
        String cardNumber = scannerStr.nextLine();

        System.out.println("Enter balance: ");
        int balance = scannerInt.nextInt();

        System.out.println("Enter password: ");
        String password = scannerStr.nextLine();

        Card card = Card.builder()
                .id(UUID.randomUUID())
                .name(cardName)
                .cardNumber(cardNumber)
                .userId(user.getId())
                .balance(balance)
                .password(password)
                .build();
        cardService.addCard(card);
    }

    private static void updateCard(User user) {
        System.out.println("Enter the card number: ");
        String cardNumber = scannerStr.nextLine();

        System.out.println("Enter card password: ");
        String password = scannerStr.nextLine();

        System.out.println("Enter a new password for the card");
        String newPassword = scannerStr.nextLine();

        Card card = cardService.updateCard(cardNumber, password, newPassword, user.getId());
        System.out.println(card.getName() + " Card password has been changed");
    }

    private static void listCard(User user) {
        List<Card> cards = cardService.listCards(user.getId());
        if (cards.isEmpty()) {
            System.out.println("You don't have a card yet! ");
        }else {
            for (Card card: cards) {
                System.out.println(card);
            }
        }
    }

    private static void deleteCard(User user) {
        System.out.println("Enter the card number: ");
        String cardNumber = scannerStr.nextLine();

        System.out.println("Enter the card password: ");
        String cardPassword = scannerStr.nextLine();

        Card card = cardService.getCard(user.getId(), cardNumber, cardPassword);
        cardService.deleteCard(card);
        System.out.println("The card has been deleted!");
    }

    private static void jsonXml(User user) {
        int i = 0;
        if (XmlJsonUtil.readWords().equals(XmlJson.Xml.getVal())) {
            System.out.println("You are in an XML file. Do you want to convert to json format?");
            System.out.println("1. Yes      2. No");
            i = scannerInt.nextInt();
            switch (i) {
                case 1 -> {
                    jsonOrXmlService.xmlToJson();
                    System.out.println("The operation was successful");
                } case 2 -> {
                    System.out.println("You are in xml format");
                }
            }
        } else {
            System.out.println("You are in Json format, want to convert to Xml format?");
            System.out.println("1. Yes      2. No");
            i = scannerInt.nextInt();
            switch (i) {
                case 1 -> {
                    jsonOrXmlService.jsonToXml();
                    System.out.println("The operation was successful");
                } case 2 -> {
                    System.out.println("You are in Json format");
                }
            }
        }
    }

    private static void transaction(User user) {
        int stepCode = 100;
        while (stepCode != 0) {
            System.out.println("1. P2P     2. History     0. Exit");
            stepCode = scannerInt.nextInt();
            switch (stepCode) {
                case 1 -> {
                    p2p(user);
                } case 2 -> {
                    history(user);
                }
            }
        }
    }

    private static void p2p(User user) {
        P2PRequestDto request = new P2PRequestDto();
        System.out.println("enter fromCard: ");
        request.setFromCard(scannerStr.nextLine());

        Card card = cardService.getCardP2P(request.getFromCard());
        System.out.println(card);
        User user1 = userService.getUserCard(card.getUserId());
        System.out.println(user1);

        System.out.println("enter toCard: ");
        request.setToCard(scannerStr.nextLine());

        System.out.println("enter amount: ");
        request.setAmount(scannerInt.nextDouble());

        transactionService.p2p(request);
    }

    private static void history(User user) {
        System.out.println("Enter from date as dd.MM.yyyy");
        String fromDate = scannerStr.nextLine();
        System.out.println("Enter to date as dd.MM.yyyy");
        String toDate = scannerStr.nextLine();

        List<Transaction> transactions = transactionService.getHistory(fromDate, toDate);
        System.out.println(transactions);
    }
}