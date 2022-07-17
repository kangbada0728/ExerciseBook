package modernJavaInAction.quiz2_1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Apple {
    private Integer weight;
    private String color;

    public Apple(Integer weight) {
        this.weight = weight;
    }
}
