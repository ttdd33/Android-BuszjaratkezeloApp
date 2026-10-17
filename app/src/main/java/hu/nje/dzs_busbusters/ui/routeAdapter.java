package hu.nje.dzs_busbusters.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hu.nje.dzs_busbusters.R;

import android.os.Bundle;
import androidx.navigation.Navigation;



public class routeAdapter extends RecyclerView.Adapter<routeAdapter.RouteViewHolder> {

    private List<route> routes;
    private String selectedDate;

    public routeAdapter(List<route> routes, String selectedDate) {
        this.routes = routes;
        this.selectedDate = selectedDate;
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_route, parent, false);

        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RouteViewHolder holder, int position) {

        route route = routes.get(position);

        // Járat adatainak megjelenítése
        holder.nameTextView.setText(route.getName());
        holder.timeTextView.setText(route.getTime());

        holder.capacityTextView.setText(
                route.getCapacity() + " férőhely     "
                        + route.getReserved() + " foglalt"
        );

        // Részletek gomb működése
        holder.detailsButton.setOnClickListener(v -> {

            Bundle bundle = new Bundle();

            bundle.putString("routeName", route.getName());
            bundle.putString("routeTime", route.getTime());
            bundle.putInt("routeCapacity", route.getCapacity());
            bundle.putInt("routeReserved", route.getReserved());

            // A naptárban kiválasztott dátum átadása
            bundle.putString("selectedDate", selectedDate);

            Navigation.findNavController(v).navigate(
                    R.id.routeDetailsFragment,
                    bundle
            );
        });
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

        public static class RouteViewHolder
                extends RecyclerView.ViewHolder {

            TextView nameTextView;
            TextView timeTextView;
            TextView capacityTextView;

            Button detailsButton;

            public RouteViewHolder(@NonNull View itemView) {
                super(itemView);

                nameTextView = itemView.findViewById(R.id.routeNameTextView);
                timeTextView = itemView.findViewById(R.id.routeTimeTextView);
                capacityTextView = itemView.findViewById(R.id.routeCapacityTextView);

                detailsButton = itemView.findViewById(R.id.detailsButton);
            }
        }
    }

