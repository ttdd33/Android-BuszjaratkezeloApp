package hu.nje.dzs_busbusters.data.remote;

// Egy járat adatai úgy, ahogy a Firebase JSON-ban vannak.
public class RouteResponse {
    public String name;
    public String departureTime;
    public String arrivalTime;
    public String plateNumber;
    public int capacity;
    public int otherReserved;
    public String stops;
}
