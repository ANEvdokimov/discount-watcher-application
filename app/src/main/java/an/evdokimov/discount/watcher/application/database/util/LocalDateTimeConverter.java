package an.evdokimov.discount.watcher.application.database.util;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;

public class LocalDateTimeConverter {
    @TypeConverter
    public static String toDateString(LocalDateTime dateTime) {
        return dateTime.toString();
    }

    @TypeConverter
    public static LocalDateTime toDateTime(String dateSting) {
        return LocalDateTime.parse(dateSting);
    }
}
