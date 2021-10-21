public class Passenger {

    int id;
    float boardingDuration;
    float landingDuration;

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
