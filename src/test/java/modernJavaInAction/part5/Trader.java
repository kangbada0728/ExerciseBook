package modernJavaInAction.part5;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Trader {

    private final String name;
    private final String city;

    @Override
    public String toString() {
        return "Trader: " + this.name + " in " + this.city;
    }
}
