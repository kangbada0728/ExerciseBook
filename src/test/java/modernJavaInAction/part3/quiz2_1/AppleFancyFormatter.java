package modernJavaInAction.part3.quiz2_1;

public class AppleFancyFormatter implements AppleFormatter {

    @Override
    public String accept(Apple a) {
        return "Weight : " + a.getWeight() +
                "Color : " + a.getColor();
    }
}
