package hu.nje.dzs_busbusters.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

// Dátumkezelő segédmetódusok – korábban a RoutesFragment-ben és a RouteDetailsFragment-ben
// is szerepelt ugyanez a kód, most egy helyre került.
public final class DateUtils {

    private static final Locale HUNGARIAN = Locale.forLanguageTag("hu-HU");

    private static final DateTimeFormatter LONG_FORMAT =
            DateTimeFormatter.ofPattern("yyyy. MMMM d. (EEEE)", HUNGARIAN);

    private DateUtils() {
    }

    // "2026-10-14" -> "2026. október 14. (szerda)"
    public static String formatLong(String isoDate) {
        if (isoDate == null) {
            return "";
        }
        return LocalDate.parse(isoDate).format(LONG_FORMAT);
    }

    // A mai nap ISO formában – az aktuális / korábbi foglalások szétválasztásához.
    public static String today() {
        return LocalDate.now().toString();
    }
}
