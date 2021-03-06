package modernJavaInAction.part6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class Main {

    List<Dish> menu;
    Map<String, List<String>> dishTags = new HashMap<>();

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

        dishTags.put("pork", Arrays.asList("greasy", "salty"));
        dishTags.put("beef", Arrays.asList("salty", "roasted"));
        dishTags.put("chicken", Arrays.asList("fried", "crisp"));
        dishTags.put("french fries", Arrays.asList("greasy", "fried"));
        dishTags.put("rice", Arrays.asList("light", "natural"));
        dishTags.put("season fruit", Arrays.asList("fresh", "natural"));
        dishTags.put("pizza", Arrays.asList("tasty", "salty"));
        dishTags.put("prawns", Arrays.asList("tasty", "roasted"));
        dishTags.put("salmon", Arrays.asList("delicious", "fresh"));
    }

    @Test
    @DisplayName("6.2 리듀싱과 요약")
    void test1() {
        Long howManyDishes = menu.stream().collect(Collectors.counting());
        long count = menu.stream().count();

        Comparator<Dish> dishCaloriesComparator = comparingInt(Dish::getCalories);
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
        Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));
        Map<CaloricLevel, List<Dish>> collect = menu.stream().collect(groupingBy(dish -> {
            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
            else return CaloricLevel.FAT;
        }));

        Map<Dish.Type, List<Dish>> collect1 = menu.stream()
                .filter(dish -> dish.getCalories() > 500)
                .collect(groupingBy(Dish::getType));
        Map<Dish.Type, List<Dish>> collect2 = menu.stream()
                .collect(groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList())));

        Map<Dish.Type, List<String>> dishNamesByType = menu.stream()
                .collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));

        Map<Dish.Type, Set<String>> dishNamesByType2 = menu.stream()
                .collect(groupingBy(Dish::getType, flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())));

        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = menu.stream()
                .collect(groupingBy(Dish::getType,
                        groupingBy(dish -> {
                            if (dish.getCalories() <= 400)
                                return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700)
                                return CaloricLevel.NORMAL;
                            else
                                return CaloricLevel.FAT;
                        })));

        Map<Dish.Type, Long> typesCount = menu.stream().collect(groupingBy(Dish::getType, counting()));
        Map<Dish.Type, Optional<Dish>> mostCaloricByType = menu.stream().collect(groupingBy(Dish::getType, maxBy(comparingInt(Dish::getCalories))));
        Map<Dish.Type, Dish> mostCaloricByType2 = menu.stream().collect(groupingBy(Dish::getType, collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));

        Map<Dish.Type, Integer> totalCaloriesByType = menu.stream().collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType = menu.stream().collect(
                groupingBy(Dish::getType, mapping(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }, toSet())));

        Map<Dish.Type, HashSet<CaloricLevel>> caloricLevelsByType2 = menu.stream().collect(
                groupingBy(Dish::getType, mapping(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }, toCollection(HashSet::new)))
        );

    }

    @Test
    @DisplayName("6.4 분할")
    void test3() {

        Map<Boolean, List<Dish>> partitionedMenu = menu.stream().collect(partitioningBy(Dish::isVegetarian));
        List<Dish> vegetarianDishes = partitionedMenu.get(true);
        Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType =
                menu.stream().collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
        Map<Boolean, Dish> mostCaloricPartitionedByVegetarian = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian, collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));
        Map<Boolean, Map<Boolean, List<Dish>>> collect =
                menu.stream().collect(partitioningBy(Dish::isVegetarian, partitioningBy(d -> d.getCalories() > 500)));
        Map<Boolean, Long> collect1 = menu.stream().collect(partitioningBy(Dish::isVegetarian, counting()));
    }

    public Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(partitioningBy(this::isPrime));
    }

    public boolean isPrime(int candidate) {
        return IntStream.range(2, candidate).noneMatch(i -> candidate % i == 0);
    }

    public boolean isPrime2(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot).noneMatch(i -> candidate % i == 0);
    }

    @Test
    @DisplayName("6.5 Collector 인터페이스")
    void test4() {
        // 생략
    }

}
