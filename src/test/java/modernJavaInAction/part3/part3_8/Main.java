package modernJavaInAction.part3.part3_8;

import modernJavaInAction.part3.quiz2_1.Apple;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;

public class Main {

    @Test
    void test() {
        Apple a1 = new Apple(1, "red");
        Apple a2 = new Apple(2, "green");
        Apple a3 = new Apple(3, "blue");

        List<Apple> list = new ArrayList<>(List.of(a2, a1, a3));
        list.sort(comparing(Apple::getWeight).reversed());
        list.sort(comparing(Apple::getWeight)
                .reversed()
                .thenComparing(Apple::getColor));
    }

    @Test
    void test2() {
        Predicate<Apple> redApple = apple -> apple.getColor().equalsIgnoreCase("red");
        Predicate<Apple> notRedApple =  redApple.negate();
        Predicate<Apple> redAndHeavyApple = redApple.and(apple -> apple.getWeight() > 150);
        Predicate<Apple> redAndHeavyAppleOrGreen = redApple.and(apple -> apple.getWeight() > 150)
                .or(apple -> apple.getColor().equalsIgnoreCase("green"));
    }

    @Test
    void test3() {
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.andThen(g);
        int result = h.apply(1);
        System.out.println("result = " + result);

        Function<Integer, Integer> h2 = f.compose(g);
        int result2 = h2.apply(1);
        System.out.println("result2 = " + result2);

        Function<String, String> addHeader = Letter::addHeader;
        Function<String, String> transformationPipeline =
                addHeader.andThen(Letter::checkSpelling).andThen(Letter::addFooter);


    }


}
