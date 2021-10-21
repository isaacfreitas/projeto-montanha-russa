public class Wagon {

    public enum State {
        RUNNING,
        SLEEPING,
        UNAVAILABLE;

        public String toString() {
            switch (this) {
                case RUNNING:
                    return "Executando";
                case SLEEPING:
                    return "Dormindo";
                case UNAVAILABLE:
                    return "Indisponível";
            }
            return null;
        }
    }

    int chairCount;
    float transitDuration;
    State state = State.UNAVAILABLE;

    Wagon(int chairCount, float transitDuration) {
        this.chairCount = chairCount;
        this.transitDuration = transitDuration;
    }

    @Override
    public String toString() {
        return "Vagão";
    }
}
