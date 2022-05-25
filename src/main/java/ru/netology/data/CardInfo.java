package ru.netology.data;

import lombok.*;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import static ru.netology.data.DataHelper.*;


@Data
@RequiredArgsConstructor
@Value
public class CardInfo {
    private String cardNumber;
    private String name;
    private String codeCvcCvv;
    private String month;
    private String year;


    public static CardInfo getApprovedCard() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo(declinedCard().cardNumber,
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo emptyFields() {
        return new CardInfo("", "", "", "", "");
    }

//    карта
    public static CardInfo emptyFieldCardNumber() {
        return new CardInfo("",
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo otherCardNumber() {
        return new CardInfo(DataHelper.getOtherCardNumber(),
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo fifteenDigitCardNumber() {
        return new CardInfo(DataHelper.getFifteenDigitCardNumber(),
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo oneValueCardNumber() {
        return new CardInfo("1",
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                getThisMonth(),
                getThisYear());
    }


//    владелец
    public static CardInfo emptyFieldName() {
        return new CardInfo(approvedCard().cardNumber,
                "",
                DataHelper.getCodeCvcCvv(),
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo invalidCharacters() {
        return new CardInfo(approvedCard().cardNumber,
                "?.:%№!",
                DataHelper.getCodeCvcCvv(),
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo nameInCyrillic() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("ru"),
                DataHelper.getCodeCvcCvv(),
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo numberNameField() {
        return new CardInfo(approvedCard().cardNumber,
                "12345",
                DataHelper.getCodeCvcCvv(),
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo invalidNameField() {
        return new CardInfo(approvedCard().cardNumber,
                DataHelper.getRandomInvalidName("EN"),
                DataHelper.getCodeCvcCvv(),
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo onlyFirstNameField() {
        return new CardInfo(approvedCard().cardNumber,
                DataHelper.getFirstName("EN"),
                DataHelper.getCodeCvcCvv(),
                getThisMonth(),
                getThisYear());
    }


    //   месяц
    public static CardInfo emptyFieldMonth() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                "",
                getThisYear());
    }

    public static CardInfo cardExpiresNextMonth() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                DataHelper.getNextMonth(),
                getThisYear());
    }

    public static CardInfo cardExpiresLastMonth() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                DataHelper.getLastMonth(),
                getThisYear());
    }

    public static CardInfo nullInFieldMonth() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                "00",
                getThisYear());
    }

    public static CardInfo enteringLettersInTheMonthField() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                "kf",
                getThisYear());
    }

    public static CardInfo expiredCardInTheCurrentYear() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                DataHelper.getLastMonth(),
                getThisYear());
    }

    public static CardInfo invalidFieldMonth1_9() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                DataHelper.getRandomInvalidMonth1_9(),
                getThisYear());
    }

    public static CardInfo invalidFieldMonth13_99() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                DataHelper.getRandomInvalidMonth13_99(),
                getThisYear());
    }


// год
    public static CardInfo emptyFieldYear() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                getThisMonth(),
                "");
    }

    public static CardInfo cardExpiresNextYear() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                getThisMonth(),
                DataHelper.getNextYear());
    }

    public static CardInfo cardExpiresLastYear() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                getThisMonth(),
                DataHelper.getLastYear());
    }

    public static CardInfo nullInFieldYear() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                getThisMonth(),
                "00");
    }

    public static CardInfo enteringLettersInTheFieldYear() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                getThisMonth(),
                "fg");
    }


    public static CardInfo invalidYear() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataHelper.getCodeCvcCvv(),
                getThisMonth(),
                DataHelper.getFutureYear());
    }


// CVC

    public static CardInfo emptyFieldCvcCvv() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                "",
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo nullFieldCvcCvv() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                "0",
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo threeNullFieldCvcCvv() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                "000",
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo twoCharactersFieldCvcCvv() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                "12",
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo enteringLettersInTheFieldCvcCvv() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                "kr",
                getThisMonth(),
                getThisYear());
    }


    @Value
    public static class CardNumber {
        private String cardNumber;
        private String status;
    }

    public static CardNumber approvedCard() {

        return new CardNumber("1111_2222_3333_4444", "APPROVED");
    }

    public static CardNumber declinedCard() {

        return new CardNumber("5555_6666_7777_8888", "DECLINED");

    }
}
