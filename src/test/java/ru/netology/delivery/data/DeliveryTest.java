package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        SelenideElement form = $("[.form]");
        form.$("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $$(".menu-item__control").last().click();
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(firstMeetingDate);
        form.$("[data-test-id=name] input").setValue(DataGenerator.generateName());
        form.$("[data-test-id=phone] input").setValue("+7" + DataGenerator.generatePhone());
        form.$(".checkbox__box").click();
        form.$(".button__text").click();
        $(withText("Успешно!")).should(Condition.visible);
        $(".notification__content").should(Condition.text("Встреча успешно запланирована на " + firstMeetingDate)).should(Condition.visible);

        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(secondMeetingDate);
        form.$("[role=button] .button__content").click();

        $$(".button__text").find(Condition.exactText("Перепланировать")).click();
        $(withText("Успешно!")).should(Condition.visible);
        $(".notification__content").should(Condition.text("Встреча успешно запланирована на " + secondMeetingDate)).should(Condition.visible);
    }
}





