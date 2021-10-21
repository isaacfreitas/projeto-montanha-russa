public class Passenger {

    public enum State {
        RIDING,
        BOARDING,
        LANDING,
        SLEEPING,
        UNAVAILABLE;

        public String toString() {
            switch (this) {
                case RIDING:
                    return "Apreciando";
                case BOARDING:
                    return "Embarcando";
                case LANDING:
                    return "Desembarcando";
                case SLEEPING:
                    return "Dormindo";
                case UNAVAILABLE:
                    return "Indisponível";
            }
            return null;
        }
    }

    int id;
    float boardingDuration;
    float landingDuration;
    State state = State.UNAVAILABLE;

    Passenger(int id, float boardingDuration, float landingDuration) {
        this.id = id;
        this.boardingDuration = boardingDuration;
        this.landingDuration = landingDuration;
    }

    @Override
    public String toString() {
        return "Passageiro " + id;
    }
}
