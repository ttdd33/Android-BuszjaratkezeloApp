package hu.nje.dzs_busbusters.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import hu.nje.dzs_busbusters.R;


public class myReservationsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_reservations, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button actualReservationButton = view.findViewById(R.id.actualReservationButton);
        Button previousReservationButton = view.findViewById(R.id.previousReservationButton);

        setButtonState(actualReservationButton, previousReservationButton);

        actualReservationButton.setOnClickListener(v -> {
            setButtonState(actualReservationButton, previousReservationButton);
            //ide: aktuális adatok megjelenítése
        });

        previousReservationButton.setOnClickListener(v -> {
            setButtonState(previousReservationButton, actualReservationButton);
            //ide: aktuális adatok megjelenítése
        });
    }

    private void setButtonState(Button activeButton, Button inactiveButton) {
        activeButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue));
        activeButton.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));

        inactiveButton.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.darker_gray));
        inactiveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue));
    }
}