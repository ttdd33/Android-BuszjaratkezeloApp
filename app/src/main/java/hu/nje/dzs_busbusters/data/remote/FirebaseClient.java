package hu.nje.dzs_busbusters.data.remote;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

// A Firebase Realtime Database REST végpontjai Retrofit interfészként
.
// A Firebase-ben minden adat-ág elérhető a "<ág neve>.json" címen.
public interface FirebaseClient {

    @GET("users.json")
    Call<Map<String, UserResponse>> getUsers();

    @GET("routes.json")
    Call<Map<String, RouteResponse>> getRoutes();

    @GET("reservations.json")
    Call<Map<String, ReservationResponse>> getReservations();

    @POST("reservations.json")
    Call<PushResponse> addReservation(@Body ReservationResponse reservation);

    @DELETE("reservations/{id}.json")
    Call<Void> deleteReservation(@Path("id") String reservationId);
}
