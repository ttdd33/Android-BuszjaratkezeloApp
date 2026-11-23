package hu.nje.dzs_busbusters.data.remote;

//Foglalás adatai a Firebase JSON-ban
//Letöltéskor ide érkezik válasz, feltöltéskor (POST) ebből készül a kérés törzs
public class ReservationResponse {
    public String userId;
    public String routeId;
    public String travelDate;

    public ReservationResponse() {
    }

    public ReservationResponse(String userId, String routeId, String travelDate) {
        this.userId = userId;
        this.routeId = routeId;
        this.travelDate = travelDate;
    }
}
