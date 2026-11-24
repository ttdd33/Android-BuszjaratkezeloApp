package hu.nje.dzs_busbusters.data.remote;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// A Retrofit felépítése és a Firebase hívások 


public class FirebaseService {

public static final String BASE_URL = "https://dezs-busbusters-default-rtdb.europe-west1.firebasedatabase.app/";

    private final FirebaseClient client;

    public FirebaseService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        client = retrofit.create(FirebaseClient.class);
    }

    public Map<String, UserResponse> getUsers() throws IOException {
        return orEmpty(execute(client.getUsers()));
    }

    public Map<String, RouteResponse> getRoutes() throws IOException {
        return orEmpty(execute(client.getRoutes()));
    }

    public Map<String, ReservationResponse> getReservations() throws IOException {
        return orEmpty(execute(client.getReservations()));
    }

    // Új foglalás feltöltése; visszaadja a Firebase által generált kulcsot.
    public String addReservation(ReservationResponse reservation) throws IOException {
        PushResponse response = execute(client.addReservation(reservation));
        if (response == null || response.name == null) {
            throw new IOException("A Firebase nem adott vissza kulcsot");
        }
        return response.name;
    }

    public void deleteReservation(String reservationId) throws IOException {
        execute(client.deleteReservation(reservationId));
    }

    // Közös végrehajtás: elküldi a kérést, és sikertelen HTTP válasznál hibát dob.
    private <T> T execute(Call<T> call) throws IOException {
        Response<T> response = call.execute();
        if (!response.isSuccessful()) {
            throw new IOException("Firebase hiba: HTTP " + response.code());
        }
        return response.body();
    }

    // Üres Firebase ágra a válasz "null", ebből üres Map lesz, hogy ne kelljen mindenhol null-t vizsgálni.
    private static <V> Map<String, V> orEmpty(Map<String, V> map) {
        return map == null ? new HashMap<>() : map;
    }
}
