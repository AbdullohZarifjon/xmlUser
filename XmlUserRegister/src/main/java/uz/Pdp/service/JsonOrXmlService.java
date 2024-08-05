package uz.Pdp.service;

import uz.Pdp.model.wrapper.Cards;
import uz.Pdp.model.wrapper.Users;
import uz.Pdp.service.serviseUtil.XmlJsonUtil;

import java.io.File;

public class JsonOrXmlService {
    private static final UserService userServise = new UserService();
    private static final CardService cardService = new CardService();
    public void xmlToJson() {
        Users users = userServise.readUsers();
        Cards cards = cardService.readCards();

        String userPath = userServise.path();
        String cardPath = cardService.path();

        File userFile = new File(userPath);
        File cardFile = new File(cardPath);

        String newUserPath = userPath.substring(0, userPath.length()-3) + "json";
        String newCardPath = cardPath.substring(0, cardPath.length()-3) + "json";

        File newUserFile = new File(newUserPath);
        File newCardFile = new File(newCardPath);

        userFile.renameTo(newUserFile);
        cardFile.renameTo(newCardFile);

        XmlJsonUtil.writeToFile("Json");
        userServise.write(users, newUserPath);
        cardService.write(cards, newCardPath);
    }

    public void jsonToXml() {
        Users users = userServise.readUsers();
        Cards cards = cardService.readCards();

        String userPath = userServise.path();
        String cardPath = cardService.path();

        File userFile = new File(userPath);
        File cardFile = new File(cardPath);

        String newUserPath = userPath.substring(0, userPath.length()-4) + "xml";
        String newCardPath = cardPath.substring(0, cardPath.length()-4) + "xml";

        File newUserFile = new File(newUserPath);
        File newCardFile = new File(newCardPath);

        userFile.renameTo(newUserFile);
        cardFile.renameTo(newCardFile);

        XmlJsonUtil.writeToFile("Xml");
        userServise.write(users, newUserPath);
        cardService.write(cards, newCardPath);
    }

}
