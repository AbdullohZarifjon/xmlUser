package uz.Pdp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.reflect.TypeToken;
import uz.Pdp.model.Card;
import uz.Pdp.model.enums.XmlJson;
import uz.Pdp.model.wrapper.Cards;
import uz.Pdp.service.serviseUtil.JsonUtil;
import uz.Pdp.service.serviseUtil.RecognizeTypeUtil;
import uz.Pdp.service.serviseUtil.XmlJsonUtil;
import uz.Pdp.service.serviseUtil.XmlUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardService {
    public String path() {
        File file = new File("src/test/Cards.xml");
        File file1 = new File("src/test/Cards.json");
        if (file.canRead()) {
            return "src/test/Cards.xml";
        } else if (file1.canRead()) {
            return "src/test/Cards.json";
        } throw new RuntimeException("The file does not exist!");
    }

    public void addCard(Card card) {
        card.setCardType(RecognizeTypeUtil.recognize(card.getCardNumber()));
        Cards cardL = readCards();
        if (hasCard(cardL.getCards(), card.getCardNumber())) {
            throw new RuntimeException("Card is already exists");
        }
        cardL.getCards().add(card);
        write(cardL, path());
    }

    private boolean hasCard(List<Card> cardList, String cardNumber) {
        for (Card card : cardList) {
            if (card.getCardNumber().equals(cardNumber)) {
                return true;
            }
        }
        return false;
    }

    public Card updateCard(String cardNumber, String oldPassword, String newPassword, UUID userId) {
        Cards cardList = readCards();
        for (Card card : cardList.getCards()) {
            if (card.getCardNumber().equals(cardNumber) && card.getPassword().equals(oldPassword) && card.getUserId().equals(userId)) {
                card.setPassword(newPassword);
                write(cardList, path());
                return card;
            }
        }
        throw new RuntimeException("You do not have such a card!");
    }

    public void deleteCard(Card card) {
        Cards cardList = readCards();
        cardList.getCards().remove(card);
        write(cardList, path());
    }

    public Card getCard(UUID userId, String cardNumber, String password) {
        Cards cardList = readCards();
        for (Card card : cardList.getCards()) {
            if (card.getUserId().equals(userId) &&
                    card.getCardNumber().equals(cardNumber) &&
                    card.getPassword().equals(password)) {
                return card;
            }
        }
        throw new RuntimeException("You do not have such a card! ");
    }

    public List<Card> listCards(UUID userId) {
        Cards cardList = readCards();

        List<Card> list = new ArrayList<>();
        for (Card card : cardList.getCards()) {
            if (card.getUserId().equals(userId)) {
                list.add(card);
            }
        }
        return list;
    }

    public Card getCardP2P(String cardNumber) {
        Cards cardList = readCards();
        for (Card card : cardList.getCards()) {
            if (card.getCardNumber().equals(cardNumber)) {
                return card;
            }
        }
        throw new RuntimeException("Bunday karta mavjud emas!");
    }

    public void updateCardsBalance(Card fromCard, Card toCard) {
        Cards cardList = readCards();
        for (int i = 0; i < cardList.getCards().size(); i++) {
            if (cardList.getCards().get(i).getCardNumber().equals(fromCard.getCardNumber())){
                cardList.getCards().set(i, fromCard);
            }
            if (cardList.getCards().get(i).getCardNumber().equals(toCard.getCardNumber())){
                cardList.getCards().set(i, toCard);
            }
        }
        write(cardList, path());
    }

    public Cards readCards() {
        return XmlJsonUtil.read(Cards.class, path());
    }

    public void write(Cards cards, String path) {
        XmlJsonUtil.write(cards, path);
    }
}
