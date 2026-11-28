package hu.nje.dzs_busbusters.ui.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import hu.nje.dzs_busbusters.R;
import hu.nje.dzs_busbusters.data.ReservationService;

//Tervezett képernyők felugró üzenetei: sikeres foglalás, betelt járat, lemondás megerősítése, lemondás visszajelzése
public final class DialogHelper {

    private DialogHelper() {
    }

    // Foglalás eredményének megjelenítése (5. és 6. képernyő a terveken).
    public static void showReservationResult(Context context, ReservationService.Result result, String details) {
        if (result == ReservationService.Result.SUCCESS) {
            show(context, R.drawable.ic_check_circle, "Sikeres foglalás!",
                    "Sikeresen helyet foglaltál a kiválasztott járatra.", details);
        } else if (result == ReservationService.Result.FULL) {
            show(context, R.drawable.ic_error, "A járat betelt",
                    "Sajnos a járat időközben betelt. Kérjük, válassz másik járatot!", details);
        } else if (result == ReservationService.Result.ALREADY_RESERVED) {
            show(context, R.drawable.ic_error, "Már van foglalásod",
                    "Erre a járatra ezen a napon már foglaltál helyet.", details);
        } else {
            showNetworkError(context);
        }
    }

    //Hálózati hiba: Firebase nem érhető el (foglalás és lemondás csak online lehetséges)
    public static void showNetworkError(Context context) {
        show(context, R.drawable.ic_error, "Nincs kapcsolat",
                "Nem sikerült elérni a szervert. Ellenőrizd az internetkapcsolatot, és próbáld újra!", null);
    }

    //Lemondás előtti megerősítés (véletlen törlés ellen)
    public static void showCancelConfirmation(Context context, String details, Runnable onConfirm) {
        new MaterialAlertDialogBuilder(context)
                .setTitle("Foglalás lemondása")
                .setMessage("Biztosan lemondod ezt a foglalást?\n\n" + details)
                .setNegativeButton("Nem", null)
                .setPositiveButton("Igen, lemondom", (dialog, which) -> onConfirm.run())
                .show();
    }

    //Lemondás visszajelzése
    public static void showCancelled(Context context) {
        show(context, R.drawable.ic_check_circle, "Foglalás lemondva",
                "A foglalásod sikeresen törlésre került.", null);
    }

    //Közös dialógus: ikon + cím + üzenet + (opcionális) részletek + OK gomb
    private static void show(Context context, int iconRes, String title, String message, String details) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_result, null);

        ImageView iconImageView = view.findViewById(R.id.dialogIconImageView);
        TextView titleTextView = view.findViewById(R.id.dialogTitleTextView);
        TextView messageTextView = view.findViewById(R.id.dialogMessageTextView);
        TextView detailsTextView = view.findViewById(R.id.dialogDetailsTextView);

        iconImageView.setImageResource(iconRes);
        titleTextView.setText(title);
        messageTextView.setText(message);

        if (details == null) {
            detailsTextView.setVisibility(View.GONE);
        } else {
            detailsTextView.setText(details);
        }

        new MaterialAlertDialogBuilder(context)
                .setView(view)
                .setPositiveButton("OK", null)
                .show();
    }
}
