package hu.nje.dzs_busbusters.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import hu.nje.dzs_busbusters.R;


public class routesFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_routes, container, false);
        //Dátum adatok lekérése
        Bundle bundle = getArguments();

        //adatcsomag létezésének ellenörzése
        if (bundle != null) {
            String selectedDate = bundle.getString("selectedDate");

            Toast.makeText(
                    requireContext(),
                    "Járatok dátuma: " + selectedDate,
                    Toast.LENGTH_SHORT
            ).show();
        }

        return view;
    }
}