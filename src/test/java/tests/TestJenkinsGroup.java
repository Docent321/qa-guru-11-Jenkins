package tests;

import com.codeborne.selenide.Configuration;
import helpers.Attach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class TestJenkinsGroup {

    @BeforeAll
    static void beforeAll() {

        String user = System.getProperty("user");
        String password = System.getProperty("password");
        String browserSize = System.getProperty("browserSize");

        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserSize = browserSize;  //"1920x1080"
        Configuration.remote = "https://" + user + ":" + password + "@selenoid.autotests.cloud/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }


    @Test
    void successFillTest() {
        step("Open home page", () -> {
            open("/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        });

        step("Enter name", () -> {
            $("#firstName").setValue("Anton");
        });

        step("Enter lastname", () -> {
            $("#lastName").setValue("Donsk");
        });

        step("Enter email", () -> {
            $("#userEmail").setValue("Alex85@mmail.com");
        });

        step("Enter user number", () -> {
            $("#userNumber").setValue("1234567890");
        });

        step("Select gender", () -> {
            $("#gender-radio-1").doubleClick();
        });

        step("Select  birthdate", () -> {
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOptionByValue("4");
            $(".react-datepicker__year-select").selectOptionByValue("1980");
            $(".react-datepicker__day--025").click();
        });

        step("Enter subjects", () -> {
            $("#subjectsInput").setValue("Physics").pressEnter();
        });

        step("Enter hobbies", () -> {
            $("[for='hobbies-checkbox-1']").click();
        });


        step("Enter address", () -> {
            $("#currentAddress").setValue("Moskov");
        });

        step("Select state", () -> {
            $("#react-select-3-input").setValue("NCR").pressEnter();
        });

        step("Select city", () -> {
            $("#react-select-4-input").setValue("Delhi").pressEnter();
        });


        step("submit click", () -> {
            $("#submit").click();
        });

        step("Form check", () -> {
            $(".table-responsive").shouldHave(text("Anton Donsk"));
            $(".table-responsive").shouldHave(text("Alex85@mmail.com"));
            $(".table-responsive").shouldHave(text("1234567890"));
            $(".table-responsive").shouldHave(text("25 may,1980"));
            $(".table-responsive").shouldHave(text("Physics"));
            $(".table-responsive").shouldHave(text("Sports"));
//            $(".table-responsive").shouldHave(text("ava.jpg"));
            $(".table-responsive").shouldHave(text("Moskov"));
            $(".table-responsive").shouldHave(text("NCR Delhi"));
        });

    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        closeWebDriver();
    }
}
