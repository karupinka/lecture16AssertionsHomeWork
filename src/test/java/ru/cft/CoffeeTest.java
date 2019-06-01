package ru.cft;

import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CoffeeTest {
    private Coffee coffee;

    @Test
    public void createCoffee(){
        Coffee coffeeEx = new Coffee(CoffeeType.Latte, 7, 227);

        assertThat(new Coffee(CoffeeType.Latte, 7, 227)).isEqualToComparingFieldByField(coffeeEx);
    }

    @Test
    public void getCoffeeType(){
        CoffeeType coffeeTypeEx = CoffeeType.Espresso;
        coffee = new Coffee(CoffeeType.Espresso, 7, 227);

        assertThat(coffee.getType()).isEqualTo(coffeeTypeEx);

    }

    @Test
    public void getMilk(){
        coffee = new Coffee(CoffeeType.Espresso, 7, 227);

        assertThat(coffee.getMilk()).isEqualTo(227);

    }

    @Test
    public void getBeans(){
        coffee = new Coffee(CoffeeType.Espresso, 7, 227);

        assertThat(coffee.getBeans()).isNotEqualTo(34);

    }

    @Test
    public void coffeeString(){
        coffee = new Coffee(CoffeeType.Espresso, 7, 227);
        String str =  "Coffee{" +
                    "type=" + CoffeeType.Espresso;

        assertThat(coffee.toString()).contains(str);
        }

    @Test
    public void fromJson() throws URISyntaxException {
        Gson gson = new GsonBuilder().create();

        Path path = Paths.get(getClass().getClassLoader().getResource("1.json").toURI().normalize());
        List<String> json = new ArrayList<>();
        StringBuilder jsonStr = new StringBuilder();

        try {
            json = Files.readAllLines(path);

        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String str : json)
            jsonStr.append(str);

       Coffee coffeeEx = gson.fromJson(jsonStr.toString(), Coffee.class);

        assertThat(new Coffee(CoffeeType.Espresso, 10, 25)).isEqualToComparingFieldByField(coffeeEx);

    }

    @Test
    public  void checkTypeArrayCoffee() throws URISyntaxException {
        List<String> coffees = new ArrayList<>();
        coffees.add((new Coffee(CoffeeType.Espresso, 10, 10)).getType().toString());
        coffees.add((new Coffee(CoffeeType.Latte, 10, 10)).getType().toString());
        coffees.add((new Coffee(CoffeeType.FilterCoffee, 10, 10)).getType().toString());

        Path path = Paths.get(getClass().getClassLoader().getResource("typeCoffee.txt").toURI().normalize());
        List<String> coffeeExs = new ArrayList<>();

        try {
            coffeeExs = Files.readAllLines(path);

        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(coffees).containsAll(coffeeExs);

    }

    @AfterAll
    static void end(){
        System.out.println("EndofTests");
    }


    }



