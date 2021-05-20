import lombok.Getter;

public class CookInfo {
    @Getter private final int id;
    @Getter private final int workingTime;

    CookInfo(int id, int workingTime) {
        this.id = id;
        this.workingTime = workingTime;
    }
}
