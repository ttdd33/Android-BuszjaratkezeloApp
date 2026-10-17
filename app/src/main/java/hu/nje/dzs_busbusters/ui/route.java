package hu.nje.dzs_busbusters.ui;
public class route {

    private String name;
    private String time;
    private int capacity;
    private int reserved;

    public route(String name, String time, int capacity, int reserved) {
        this.name = name;
        this.time = time;
        this.capacity = capacity;
        this.reserved = reserved;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getReserved() {
        return reserved;
    }
}