package hu.nje.dzs_busbusters.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.TextView;
import  java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import hu.nje.dzs_busbusters.R;

//routeadapter osztály routesFgramentel való összekapcsolása

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;
import java.util.ArrayList;

public class routesFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_routes, container, false);
        //Dátum adatok lekérése

        TextView routesDateTextView =
                view.findViewById(R.id.routesDateTextView);
        //átveszi a naptárból küldött adatokat
        Bundle bundle = getArguments();

        if (bundle != null) {
            //kiolvassa a kiválasztott dátumot
            String selectedDate = bundle.getString("selectedDate");

            if (selectedDate != null) {
                //szövegből dátum konverzió
                LocalDate date = LocalDate.parse(selectedDate);
                //magyar formátum
                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern(
                                "yyyy. MMMM d. (EEEE)",
                                new Locale("hu", "HU")
                        );
                //kiíratás
                routesDateTextView.setText(date.format(formatter));
            }
        }


        RecyclerView routesRecyclerView =
                view.findViewById(R.id.routesRecyclerView);

// A járatok egymás alatt jelenjenek meg
        routesRecyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

// Tesztjáratok létrehozása
        List<route> routes = new ArrayList<>();

        routes.add(new route(
                "Kecskemét → Üzem",
                "05:20 – 05:50",
                45,
                32
        ));

        routes.add(new route(
                "Hetényegyháza → Üzem",
                "06:10 – 06:40",
                30,
                18
        ));

        routes.add(new route(
                "Széchenyiváros → Üzem",
                "07:00 – 07:30",
                50,
                46
        ));

// Adapter létrehozása
        String selectedDate = getArguments() != null
                ? getArguments().getString("selectedDate")
                : null;

        routeAdapter adapter = new routeAdapter(routes, selectedDate);

// Adapter hozzárendelése a RecyclerView-hoz
        routesRecyclerView.setAdapter(adapter);


        return view;
    }
}