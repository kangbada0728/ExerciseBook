package modernJavaInAction.part6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

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
    @DisplayName("6.2 리듀싱과 요약")
    void test1() {
        Long howManyDishes = menu.stream().collect(Collectors.counting());
        long count = menu.stream().count();

        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> mostCalorieDish = menu.stream().collect(Collectors.maxBy(dishCaloriesComparator));
        Optional<Dish> max = menu.stream().max(dishCaloriesComparator);

        int totalCalories = menu.stream().collect(Collectors.summingInt(Dish::getCalories));
        int sum = menu.stream().mapToInt(Dish::getCalories).sum();

        double avgCalories = menu.stream().collect(Collectors.averagingInt(Dish::getCalories));

        IntSummaryStatistics menuStatistics = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println("menuStatistics = " + menuStatistics);

        String shortMenu = menu.stream().map(Dish::getName).collect(Collectors.joining());
        String collect = menu.stream().map(Dish::getName).collect(Collectors.joining(", "));

        int collect1 = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (i, j) -> i + j));
        int collect2 = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, Integer::sum));
        Long collect3 = menu.stream().collect(Collectors.reducing(0L, e -> 1L, Long::sum));
    }

    @Test
    @DisplayName("6.3 그룹화")
    void test2() {

    }
}
