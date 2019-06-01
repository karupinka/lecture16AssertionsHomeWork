package ru.cft;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.*;
import ru.cft.Cafe;
import ru.cft.Coffee;
import ru.cft.CoffeeType;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JunitTest {
    private static Cafe cafe;

    @BeforeEach
    void  start() {
        cafe = new Cafe();
    }
    @BeforeAll
    static  void startHello(){
        System.out.println("Hello)");
    }


    @Tag("restock")
    @Test
    public  void restockBeansAndMilk(){
        cafe.restockBeans(200);
        cafe.restockMilk(600);
        assertAll("Restock milk and coffee",
                ()-> assertEquals(200, cafe.getBeansInStock()),
                ()->assertEquals(600, cafe.getMilkInStock()));
    }

    @Order(1)
    @Tag("brew")
    @Test
    public void brew(){
        cafe.restockBeans(200);
        cafe.restockMilk(600);
        assertEquals(227, cafe.brew(CoffeeType.Latte).getMilk());
    }

    @Tag("brew")
    @Test
    public void brewWithoutMilkAndDeans(){
        assertThrows(IllegalStateException.class,
                ()->cafe.brew(CoffeeType.Espresso)
                );
    }

    @Tag("brew")
    @Test
    public void brewWithStrengthMoreThanOne() {
        Coffee coffeeFilterCoffee = new Coffee(CoffeeType.FilterCoffee, 20, 0);
        cafe.restockBeans(20);
        assertThat(cafe.brew(CoffeeType.FilterCoffee, 2)).isEqualToComparingFieldByField(coffeeFilterCoffee);
    }

    @Ignore
    @Test
    public void brewWithStrengthOne() {
        cafe.restockBeans(20);
        assertFalse((cafe.brew(CoffeeType.FilterCoffee, 1).equals(null)));
    }

    @Tag("brew")
    @Test
    public void brewWithStrengthLessThanOne(){
        cafe.restockBeans(200);
        cafe.restockMilk(600);
        assertThrows(IllegalArgumentException.class,
                ()->cafe.brew(CoffeeType.Espresso, 0));
    }

    @Tag("brew")
    @Test
    public void brewWithStrengthLessThanOneByAssertJ(){
        cafe.restockBeans(200);
        cafe.restockMilk(600);
        assertThatIllegalArgumentException().as("IllegalArgumentException").isThrownBy(
                ()->cafe.brew(CoffeeType.Espresso, 0));

    }

    @Tag("restock")
    @Test
    public void restockBeansAndMilkWithBelowZeroArgs(){
        assertAll("Rocket milk and coffee with < 0",
                ()->assertThrows(IllegalArgumentException.class,
                        ()->cafe.restockMilk(-1)),
                ()->assertThrows(IllegalArgumentException.class,
                        ()->cafe.restockBeans(-1)));
    }

    @Tag("restock")
    @Test
    public void restockBeansAndMilkOneGram(){
        cafe.restockBeans(1);
        cafe.restockMilk(1);
        assertAll("Restock milk and coffee",
                ()-> assertEquals(1, cafe.getBeansInStock()),
                ()->assertEquals(1, cafe.getMilkInStock()));
    }

    @Tag("brew")
    @Test
    public void brewCoffeeReturnCoffee() {
        cafe.restockBeans(20);
        assertTrue((cafe.brew(CoffeeType.FilterCoffee, 1).getClass().equals(Coffee.class)));
    }

    @Tag("brew")
    @Test
    public void brewCoffeeReturnObject() {
        cafe.restockBeans(20);
        assertNotNull(cafe.brew(CoffeeType.FilterCoffee));
    }

    @Tag("brew")
    @Test
    public void brewCoffeeReturnDifferentObjects() {
        cafe.restockBeans(20);
        Coffee coffee = cafe.brew(CoffeeType.FilterCoffee);

        assertNotSame(coffee, cafe.brew(CoffeeType.FilterCoffee));

    }

    @Tag("brew")
    @Test
    public void TimeOutOfBean() {
        assertTimeout(Duration.ofMillis(100),
                ()-> {for(int i = 0; i <100; i++)cafe.restockBeans(1);});
    }


}
