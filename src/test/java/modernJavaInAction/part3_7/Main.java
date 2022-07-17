package modernJavaInAction.part3_7;

import modernJavaInAction.quiz2_1.Apple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

public class Main {

    @Test
    @DisplayName("")
    public void test1() {
        Apple a1 = new Apple(1, "red");
        Apple a2 = new Apple(2, "green");
        Apple a3 = new Apple(3, "blue");

        List<Apple> list = new ArrayList<>(List.of(a2, a1, a3));
        list.sort(new AppleComparator());
        list.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });
        list.sort(comparing(apple -> apple.getWeight()));
        list.sort(comparing(Apple::getWeight));
    }

    class AppleComparator implements Comparator<Apple> {
        @Override
        public int compare(Apple o1, Apple o2) {
            return o1.getWeight().compareTo(o2.getWeight());
        }
    }
}
