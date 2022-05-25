package ru.netology.pages;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.CardInfo;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import java.util.concurrent.locks.Condition;

public class CardPages {

    private SelenideElement heading = $$(".heading").findBy(Condition.text("Оплата по карте"));
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement nameField = $("#root > div > form > fieldset > div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private SelenideElement codeField = $("[placeholder='999']");
    private SelenideElement buttonContinue = $$(".button .button__content .button__text").last();


    public PaymentWithCardPage() {
        heading.shouldBe(Condition.visible);
    }

    public void paymentByCardOrCredit(CardInfo data) {
        cardNumberField.setValue(data.getCardNumber());
        monthField.setValue(data.getMonth());
        yearField.setValue(data.getYear());
        nameField.setValue(data.getName());
        codeField.setValue(data.getCodeCvcCvv());
        buttonContinue.click();

    }


}
