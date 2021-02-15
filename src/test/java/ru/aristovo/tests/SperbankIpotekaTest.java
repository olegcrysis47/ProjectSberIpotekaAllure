package ru.aristovo.tests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.aristovo.base.BaseTests;

import java.util.List;

public class SperbankIpotekaTest extends BaseTests {

    @Test
    public void testIpotekaSber() {

        // стартовая страница
        List<WebElement> menuSber = driver.findElements(By.xpath("//a[@class=' kitt-top-menu__link kitt-top-menu__link_first kitt-top-menu__link_icons kitt-top-menu__link_wide']"));
        for (WebElement w:menuSber) {
            if (w.getAttribute("aria-label").equalsIgnoreCase("ипотека")) {
                waitUtilElementToBeClickable(w);
                w.click();
                break;
            }
        }

        List<WebElement> subMenu = driver.findElements(By.xpath("//a[@class='kitt-top-menu__link kitt-top-menu__link_second']"));
        for (WebElement w:subMenu) {
            if (w.getText().equalsIgnoreCase("Ипотека на готовое жильё")) {
                waitUtilElementToBeClickable(w);
                w.click();
                break;
            }
        }

        WebElement ipoteka = driver.findElement(By.xpath("//h2[contains(.,'Рассчитайте ипотеку')]"));
        scrollToElementJs(ipoteka);

        driver.switchTo().frame(0);

        WebElement price = driver.findElement(By.xpath("//span[@class='_3akqIukcHrgIDOuebcl58f']//div[contains(.,'Стоимость недвижимости')]//input"));
        price.sendKeys(Keys.CONTROL + "A");
        price.sendKeys("5 180 000");
        waitThread(1000);

        WebElement firstPay = driver.findElement(By.xpath("//span[@class='_3akqIukcHrgIDOuebcl58f']//div[contains(.,'Первоначальный взнос')]//input"));
        firstPay.sendKeys(Keys.CONTROL + "A");
        firstPay.sendKeys("3 058 000");
        waitThread(1000);

        WebElement period = driver.findElement(By.xpath("//span[@class='_3akqIukcHrgIDOuebcl58f']//div[contains(.,'Срок кредита')]//input"));
        period.sendKeys(Keys.CONTROL + "A");
        period.sendKeys("30");
        waitThread(1000);

        //WebElement addServ = driver.findElement(By.xpath("//div[@class='_1CyiyN1qJdh_dWG5pvvrcw']"));

        driver.switchTo().defaultContent();
        scrollMy(0, 500);
        driver.switchTo().frame(0);

        WebElement domKlick = driver.findElement(By.xpath("//div[@data-e2e-id='mland-calculator/discount-item-1-switcher']//input"));
        domKlick.click();
        waitThread(1000);
        Assert.assertEquals("Переключение не сработало", "false", domKlick.getAttribute("aria-checked"));

        WebElement insurance = driver.findElement(By.xpath("//div[@data-e2e-id='mland-calculator/discount-item-2-switcher']//input"));
        insurance.click();
        waitThread(1000);
        Assert.assertEquals("Переключение не сработало", "false", insurance.getAttribute("aria-checked"));


        WebElement youngFamily = driver.findElement(By.xpath("//div[@data-e2e-id='mland-calculator/discount-item-6-switcher']//input"));
        Assert.assertEquals("Переключение не сработало", "true", youngFamily.getAttribute("aria-checked"));

        WebElement electRegist = driver.findElement(By.xpath("//div[@data-e2e-id='mland-calculator/discount-item-7-switcher']//input"));
        electRegist.click();
        waitThread(1000);
        Assert.assertEquals("Переключение не сработало", "false", electRegist.getAttribute("aria-checked"));

        driver.switchTo().defaultContent();
        scrollMy(0, -700);
        waitThread(1000);
        driver.switchTo().frame(0);

        WebElement monthPayment = driver.findElement(By.xpath("//span[@data-e2e-id='mland-calculator/medium-result-monthly-payment']//span"));
        Assert.assertEquals("Месячный платеж не верен", "16 922 ₽", monthPayment.getText());

        WebElement creditSum = driver.findElement(By.xpath("//span[@data-e2e-id='mland-calculator/medium-result-credit-sum']//span"));
        Assert.assertEquals("Сумма не верна", "2 122 000 ₽", creditSum.getText());

        WebElement reqIncome = driver.findElement(By.xpath("//span[@data-e2e-id='mland-calculator/medium-result-required-income']//span"));
        Assert.assertEquals("З/п не верен", "21 784 ₽", reqIncome.getText());

        WebElement creditRate = driver.findElement(By.xpath("//span[@data-e2e-id='mland-calculator/medium-result-credit-rate']//span"));
        Assert.assertEquals("Ставка не верна", "11%", creditRate.getText());




        waitThread(5000);



    }

    public void scrollMy(int x, int y) {
        ((JavascriptExecutor)driver).executeScript("window.scrollBy(" + x + ","
                + y + ");");
    }

    public void scrollWithOffset(WebElement element, int x, int y) {
        String code = "window.scroll(" + (element.getLocation().x + x) + ","
                + (element.getLocation().y + y) + ");";
        ((JavascriptExecutor) driver).executeScript(code, element, x, y);
    }

    private void scrollToElementJs(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void waitUtilElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private void waitUtilElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void waitUtilElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    void waitThread(int mSec) {
        try {
            Thread.sleep(mSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
