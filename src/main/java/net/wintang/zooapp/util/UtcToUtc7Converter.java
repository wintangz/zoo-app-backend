package net.wintang.zooapp.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Converter(autoApply = true)
public class UtcToUtc7Converter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        ZoneId utc7Zone = ZoneId.of("UTC+7");
        ZoneId utcZone = ZoneId.of("UTC");

        return Timestamp.valueOf(localDateTime.atZone(utc7Zone)
                .withZoneSameInstant(utcZone)
                .toLocalDateTime());
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }

        LocalDateTime utcDateTime = timestamp.toLocalDateTime();
        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId utc7Zone = ZoneId.of("UTC+7");

        return utcDateTime.atZone(utcZone)
                .withZoneSameInstant(utc7Zone)
                .toLocalDateTime();
    }
}