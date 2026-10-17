package hu.nje.dzs_busbusters.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import hu.nje.dzs_busbusters.R;

import androidx.navigation.fragment.NavHostFragment;

public class calendarFragment extends Fragment {

    private LocalDate selectedDate;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_calendar, container, false);

        CalendarView calendarView =
                view.findViewById(R.id.calendarView);

        TextView selectedDateTextView =
                view.findViewById(R.id.selectedDateTextView);

        Button continueButton =
                view.findViewById(R.id.continueButton);

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy. MM. dd.");

        selectedDate = LocalDate.now();

        selectedDateTextView.setText(
                "Kiválasztott dátum: "
                        + selectedDate.format(formatter));

        calendarView.setOnDateChangeListener(
                (calendar, year, month, dayOfMonth) -> {

                    selectedDate = LocalDate.of(
                            year, month + 1, dayOfMonth);

                    selectedDateTextView.setText(
                            "Kiválasztott dátum: "
                                    + selectedDate.format(formatter));
                });
//naptár felület tovább gomb funkció
        continueButton.setOnClickListener(v -> {
            //tárolja a kiválasztott dátumot
            Bundle bundle = new Bundle();
            //szövegként menti a dátumot
            bundle.putString(
                    "selectedDate",
                    selectedDate.toString()
            );
            //routes fragmentnek átadja a dátumot
            NavHostFragment.findNavController(this)
                    .navigate(R.id.routesFragment, bundle);

        });

        return view;
    }
}