import lombok.Getter;

public class DeliverymanInfo {
    @Getter private final int id;
    @Getter private final int workingTime;
    @Getter private final int storageSize;

    DeliverymanInfo(int id, int workingTime, int backSize) {
        this.id = id;
        this.workingTime = workingTime;
        this.storageSize = backSize;
    }
}
