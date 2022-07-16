package modernJavaInAction.quiz2_1;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        List<Apple> inventory = new ArrayList<>();
        prettyPrintApple(inventory, new AppleFancyFormatter());

    }

    public static void prettyPrintApple(List<Apple> inventory, AppleFormatter af) {
        for (Apple apple : inventory) {
            System.out.println(af.accept(apple));
        }
    }
}
