public class Wagon {

    int chairCount;
    float transitDuration;

    Wagon(int chairCount, float transitDuration) {
        this.chairCount = chairCount;
        this.transitDuration = transitDuration;
    }

    @Override
    public String toString() {
        return "Vagão";
    }
}
