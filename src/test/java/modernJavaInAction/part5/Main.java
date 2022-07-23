package modernJavaInAction.part5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

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
                .filter(arr -> (arr[0] + arr[1]) % 3 == 0)
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

    @Test
    void test7() {
        Optional<Dish> any = menu.stream()
                .filter(Dish::isVegetarian)
                .findAny();
        menu.stream()
                .filter(Dish::isVegetarian)
                .findAny()
                .ifPresent(dish -> System.out.println("dish = " + dish));

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> first = nums.stream()
                .map(n -> n * n)
                .filter(n -> n % 3 == 0)
                .findFirst();
    }

    @Test
    void test8() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int sum = numbers.stream().reduce(0, (a, b) -> a + b);
        int sum2 = numbers.stream().reduce(0, Integer::sum);
        int mul = numbers.stream().reduce(1, (a, b) -> a * b);
        Optional<Integer> reduce = numbers.stream().reduce(Integer::sum);
    }

    @Test
    void test9() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        numbers.stream().reduce(Integer::max);
        numbers.stream().reduce(Integer::min);

        int reduce = menu.stream()
                .map(dish -> 1)
                .reduce(0, (a, b) -> a + b);
        long count = menu.stream().count();
    }

    @Test
    void test10() {

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        List<Transaction> collect = transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(comparing(t -> t.getTrader().getName()))
                .collect(toList());

        List<String> collect1 = transactions.stream()
                .map(t -> t.getTrader().getCity())
                .distinct()
                .collect(toList());

        List<Transaction> cambridge = transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .sorted(comparing(t -> t.getTrader().getName()))
                .collect(toList());

        List<String> collect2 = transactions.stream()
                .map(t -> t.getTrader().getName())
                .sorted()
                .collect(toList());

        boolean milan = transactions.stream()
                .anyMatch(t -> t.getTrader().getCity().equals("Milan"));

        transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .forEach(System.out::println);

        transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);

    }

    @Test
    void test11() {
        int sum = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();

        IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
        Stream<Integer> boxed = intStream.boxed();

        OptionalInt max = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();
        int i = max.orElse(1);
    }

    @Test
    void test12() {
        IntStream evenNumbers = IntStream.rangeClosed(1, 100).filter(n -> n % 2 == 0);
        System.out.println(evenNumbers.count());

        IntStream intStream = IntStream.rangeClosed(1, 100);
//        System.out.println(intStream.count());
        intStream.forEach(System.out::println);

    }





























}
