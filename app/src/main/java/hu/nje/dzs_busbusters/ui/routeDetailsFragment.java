package hu.nje.dzs_busbusters.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import hu.nje.dzs_busbusters.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link routeDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class routeDetailsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =   inflater.inflate(R.layout.fragment_route_details, container, false);

        TextView detailRouteName = view.findViewById(R.id.detailRouteName);
        TextView detailTime = view.findViewById(R.id.detailTime);
        TextView detailCapacity = view.findViewById(R.id.detailCapacity);
        TextView detailReserved = view.findViewById(R.id.detailReserved);
        TextView detailDate = view.findViewById(R.id.detailDate);

// A járatok listájából átadott adatok
        Bundle bundle = getArguments();

        if (bundle != null) {

            String routeName = bundle.getString("routeName");
            String routeTime = bundle.getString("routeTime");

            int capacity = bundle.getInt("routeCapacity");
            int reserved = bundle.getInt("routeReserved");

            detailRouteName.setText(routeName);
            detailTime.setText(routeTime);
            detailCapacity.setText(capacity + " fő");
            detailReserved.setText(reserved + " fő");

            // A dátumot akkor jelenítjük meg,
            // ha a navigáció során átadtuk.
            String selectedDate = bundle.getString("selectedDate");

            if (selectedDate != null) {

                LocalDate date = LocalDate.parse(selectedDate);

                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern(
                                "yyyy. MMMM d. (EEEE)",
                                new Locale("hu", "HU")
                        );

                detailDate.setText(date.format(formatter));
            }
        }

        return view;
    }
}