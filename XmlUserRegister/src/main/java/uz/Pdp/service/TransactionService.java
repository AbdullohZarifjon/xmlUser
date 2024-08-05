package uz.Pdp.service;

import uz.Pdp.controller.dto.P2PRequestDto;
import uz.Pdp.model.Card;
import uz.Pdp.model.Commission;
import uz.Pdp.model.Transaction;
import uz.Pdp.model.enums.Status;
import uz.Pdp.model.wrapper.Commissions;
import uz.Pdp.model.wrapper.TransactionWrapper;
import uz.Pdp.service.serviseUtil.XmlUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionService {
    private static final String PATH = "src/test/transactions.xml";
    private static final String PATH_COMMISSION = "src/test/commission.xml";
    private final CardService cardService;

    public TransactionService(CardService cardService) {
        this.cardService = cardService;
    }

    public void p2p(P2PRequestDto request) {
        int i = readNumber() + 1;
        Transaction transaction = Transaction.builder()
                .id(i)
                .date(new Date())
                .amount(request.getAmount())
                .toCard(request.getToCard())
                .fromCard(request.getFromCard())
                .build();
        writeNumber(i);
        if (request.getFromCard().equals(request.getToCard())) {
            transaction.setStatus(Status.ERROR_CARD_SAME);
            writeTransaction(transaction);
            return;
        }

        Card fromCard = getCard(request.getFromCard());
        Card toCard = getCard(request.getToCard());

        if (fromCard == null || toCard == null) {
            transaction.setStatus(Status.ERROR_CARD_NOT_FOUND);
            writeTransaction(transaction);
            return;
        }

        double commissionAmount = calculateCommission(request.getAmount(), getCommission(fromCard, toCard));
        if (fromCard.getBalance() < request.getAmount() + commissionAmount) {
            transaction.setStatus(Status.ERROR_CARD_MONEY_IS_NOT_ENOUGH);
            writeTransaction(transaction);
            return;
        }

        fromCard.setBalance(fromCard.getBalance() - request.getAmount() - commissionAmount);
        toCard.setBalance(toCard.getBalance() + request.getAmount());

        cardService.updateCardsBalance(fromCard, toCard);
        transaction.setStatus(Status.SUCCESS);
        transaction.setCommission(getCommission(fromCard, toCard).getPercent());
        transaction.setCommission_amount(commissionAmount);
        writeTransaction(transaction);
    }

    private double calculateCommission(double amount, Commission commission) {
        return commission.getPercent() / 100 * amount;
    }

    private int readNumber() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/test/IdTransactions.txt"))) {
            String line = br.readLine();
            return Integer.parseInt(line);
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void writeNumber(int number) {
        try (FileWriter fileWriter = new FileWriter("src/test/IdTransactions.txt", false)) {
            fileWriter.write(Integer.toString(number));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Commission getCommission(Card fromCard, Card toCard) {
        Commissions commissionWrapper = XmlUtil.read(Commissions.class, PATH_COMMISSION);

        for (Commission commission: commissionWrapper.getCommissions()) {
            if (commission.getFromType().equals(fromCard.getCardType().getVal()) &&
                    commission.getToType().equals(toCard.getCardType().getVal())) {
                return commission;
            }
        }

        throw new RuntimeException("commission not found please check the commission file");
    }

    private Card getCard(String cardNumber) {
        try {
            return cardService.getCardP2P(cardNumber);
        }catch (RuntimeException e) {
            return null;
        }
    }

    private void writeTransaction(Transaction transaction) {
        TransactionWrapper transactionWrapper = XmlUtil.read(TransactionWrapper.class, PATH);
        transactionWrapper.getTransactions().add(transaction);
        XmlUtil.write(transactionWrapper, PATH);
    }

    public List<Transaction> getHistory(String fromDate, String toDate) {
        TransactionWrapper transactions = XmlUtil.read(TransactionWrapper.class, PATH);
        SimpleDateFormat sdt = new SimpleDateFormat("dd.MM.yyyy");
        Date from = null;
        Date to = null;
        try {
            from = sdt.parse(fromDate);
            to = sdt.parse(toDate);
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<Transaction> transactions1 = new ArrayList<>();
        for (Transaction transaction: transactions.getTransactions()) {
            if (!transaction.getDate().before(from) & transaction.getDate().before(to)) {
                transactions1.add(transaction);
            }
        }
        return transactions1;
    }
 }
