package hu.nje.dzs_busbusters.data.entities;

//Lekérdezés eredménye a "Saját foglalásaimhoz": foglalás adatai + hozzá tartozó járat adatai
public class ReservationWithRoute {

    private String reservationId;
    private String travelDate;
    private String routeId;
    private String routeName;
    private String departureTime;
    private String arrivalTime;
    private String plateNumber;

    //Setter metódusokkal objektum feltöltés (üres konstruktor után)
    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getTimeRange() {
        return departureTime + " – " + arrivalTime;
    }
}
