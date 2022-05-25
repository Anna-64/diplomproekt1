package ru.netology;


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardInfo;
import ru.netology.data.DbUtils;
import ru.netology.pages.CardPages;
import ru.netology.pages.Pages;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyCreditTest {

    private SelenideElement successfulOperation = $(withText("Операция одобрена Банком."));
    private SelenideElement errorOperation = $(withText("Ошибка! Банк отказал в проведении операции."));
    private ElementsCollection wrongFormat = $$(withText("Неверный формат"));
    private SelenideElement requiredField = $(withText("Поле обязательно для заполнения"));
    private SelenideElement cardExpired = $(withText("Истёк срок действия карты"));
    private SelenideElement invalidCardDate = $(withText("Неверно указан срок действия карты"));


    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
    }

    @AfterAll
    static void cleanUp() {
        DbUtils.clearDB();
    }

    @Test
    public void payApprovedCard() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.getApprovedCard());

        successfulOperation.shouldBe(visible, Duration.ofSeconds(10));
        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder + 1, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay + 1, actualNumberLinesPay);
    }

    @Test
    public void payDeclinedCard() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.getDeclinedCard());

        successfulOperation.shouldBe(visible, Duration.ofSeconds(10));
        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay + 1, actualNumberLinesPay);

    }

    @Test
    public void payCardWhichExpiresNextMonth() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.cardExpiresNextMonth());

        successfulOperation.shouldBe(visible, Duration.ofSeconds(10));
        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder + 1, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay + 1, actualNumberLinesPay);

    }

    @Test
    public void payWhichCardExpiresNextYear() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.cardExpiresNextYear());

        successfulOperation.shouldBe(visible, Duration.ofSeconds(10));
        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder + 1, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay + 1, actualNumberLinesPay);

    }

    @Test
    public void payCardWithEmptyFields() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.emptyFields());

        wrongFormat.shouldHaveSize(4);
        requiredField.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenEmptyFieldCardNumber() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.emptyFieldCardNumber());

        wrongFormat.shouldHaveSize(1);
        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payOtherCardNumber() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.otherCardNumber());

        errorOperation.shouldBe(visible, Duration.ofSeconds(10));

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenFifteenDigitCardNumber() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.fifteenDigitCardNumber());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenOneValueCardNumber() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.oneValueCardNumber());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenEmptyFieldName() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.emptyFieldName());

        requiredField.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenInvalidCharacters() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.invalidCharacters());

        requiredField.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenNameInCyrillic() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.nameInCyrillic());

        requiredField.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenNumberNameField() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.numberNameField());

        requiredField.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenInvalidNameField() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.invalidNameField());

        requiredField.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenOnlyFirstNameField() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.onlyFirstNameField());

        requiredField.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenEmptyFieldMonth() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.emptyFieldMonth());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payCardWhichExpiresLastMonth() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.cardExpiresLastMonth());

        invalidCardDate.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void checkWhenNullInFieldMonth() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.nullInFieldMonth());

        invalidCardDate.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void enteringLettersInTheMonthField() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.enteringLettersInTheMonthField());

        wrongFormat.shouldHaveSize(1);


        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void checkExpiredCardInTheCurrentYear() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.expiredCardInTheCurrentYear());

        invalidCardDate.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenInvalidFieldMonth1_9() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.invalidFieldMonth1_9());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void checkWhenInvalidFieldMonth13_99() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.invalidFieldMonth13_99());

        invalidCardDate.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenEmptyFieldYear() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.emptyFieldYear());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payCardWhichExpiredLastYear() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.cardExpiresLastYear());

        cardExpired.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void pqyWhenNullInFieldYear() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.nullInFieldYear());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void enteringLettersInTheFieldYear() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.enteringLettersInTheFieldYear());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payCardWhichInvalidYear() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.invalidYear());

        invalidCardDate.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payCardWhenEmptyFieldCvcCvv() {
        var titlePage = new Pages();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.emptyFieldCvcCvv());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payCardWhenNullFieldCvcCvv() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.nullFieldCvcCvv());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void twoCharactersFieldCvcCvv() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.twoCharactersFieldCvcCvv());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void enteringLettersInTheFieldCvcCvv() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.enteringLettersInTheFieldCvcCvv());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void threeNullFieldCvcCvv() {
        var titlePage = new Pages();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new CardPages();
        payment.paymentByCardOrCredit(CardInfo.threeNullFieldCvcCvv());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

}

