package modernJavaInAction.part5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    List<Dish> menu;

    @BeforeEach
    void beforeEach() {
        menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)
        );
    }

    @Test
    void test1() {
        List<Dish> specialMenu = Arrays.asList(
                new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER));

        specialMenu.stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .forEach(System.out::println);

        specialMenu.stream()
                .dropWhile(dish -> dish.getCalories() < 320)
                .forEach(System.out::println);

        specialMenu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .limit(3)
                .forEach(System.out::println);
    }

    @Test
    void test2() {
        menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .skip(2)
                .forEach(System.out::println);
    }

    @Test
    void test3() {
        menu.stream()
                .filter(dish -> !dish.isVegetarian())
                .limit(2)
                .forEach(System.out::println);
    }

    @Test
    void test4() {
        menu.stream()
                .map(Dish::getName)
                .forEach(System.out::println);

        menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .forEach(System.out::println);
    }

    @Test
    void test5() {
        String[] arrayOfWords = {"Goodbye", "World"};
        Stream<String> streamOfwords = Arrays.stream(arrayOfWords);

        List<String> words = Arrays.asList("Modern", "Java", "In", "Action");
        words.stream()
                .map(word -> word.split(""))
                .map(Arrays::stream)
                .distinct()
                .forEach(System.out::println);

        words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .forEach(System.out::println);

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        nums.stream()
                .map(n -> n * n)
                .forEach(System.out::println);

        List<Integer> n1 = Arrays.asList(1, 2, 3);
        List<Integer> n2 = Arrays.asList(3, 4);
        n1.stream()
                .flatMap(i -> n2.stream().map(j -> new int[]{i, j}))
                .forEach(n -> System.out.println(n[0] + " " + n[1]));
        System.out.println();
        n1.stream()
                .flatMap(i -> n2.stream().map(j -> new int[]{i, j}))
                .filter(arr -> (arr[0]+arr[1])%3==0)
                .forEach(n -> System.out.println(n[0] + " " + n[1]));
    }

    @Test
    void test6() {
        if (menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("The menu is (somewhat) vegetarian friendly!!");
        }
        if (menu.stream().allMatch(dish -> dish.getCalories() < 1000)) {
            System.out.println("The menu is (somewhat) vegetarian friendly!!");
        }
        if (menu.stream().noneMatch(dish -> dish.getCalories() >= 1000)) {
            System.out.println("The menu is (somewhat) vegetarian friendly!!");
        }
    }




}
