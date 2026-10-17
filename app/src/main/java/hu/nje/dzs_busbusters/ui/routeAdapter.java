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

public class routeAdapter extends RecyclerView.Adapter<routeAdapter.RouteViewHolder> {

    private List<route> routes;

    public routeAdapter(List<route> routes) {
        this.routes = routes;
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

        holder.nameTextView.setText(route.getName());
        holder.timeTextView.setText(route.getTime());

        holder.capacityTextView.setText(
                route.getCapacity() + " férőhely     "
                        + route.getReserved() + " foglalt"
        );
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    public static class RouteViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView timeTextView;
        TextView capacityTextView;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.routeNameTextView);
            timeTextView = itemView.findViewById(R.id.routeTimeTextView);
            capacityTextView = itemView.findViewById(R.id.routeCapacityTextView);
        }
    }
}
