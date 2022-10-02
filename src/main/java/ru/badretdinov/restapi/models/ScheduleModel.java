package ru.badretdinov.restapi.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class ScheduleModel extends CommonModel{
    // название фильма
    @JsonProperty(value = "movieTitle", required = true)
    public final String movieTitle;

    // Дата и время начала
    @JsonProperty(value = "dateAndTime", required = true)
    public final String dateAndTime;

    // Длительность сеанса в минутах
    @JsonProperty(value = "movieDuration", required = true)
    public final int movieDuration;

    // номер зала
    @JsonProperty(value = "auditorium", required = false, defaultValue = "1")
    public final int auditorium;

    @JsonCreator
    public ScheduleModel(
            @JsonProperty("timestamp") final long timestamp,
            @JsonProperty("movieTitle") final String movieTitle,
            @JsonProperty("dateAndTime") final String dateAndTime,
            @JsonProperty("movieDuration") final int movieDuration,
            @JsonProperty("auditorium") final int auditorium
    ) {
        super(timestamp);

        this.movieTitle = movieTitle;
        this.dateAndTime = dateAndTime;
        this.movieDuration = movieDuration;
        this.auditorium = auditorium;
    }

    public ScheduleModel(final String[] fields) throws IllegalArgumentException {
        super(fields);

        if (fields != null && fields.length == 5) {
            try {
                this.movieTitle = fields[1];
                this.dateAndTime = fields[2];
                this.movieDuration = Integer.parseInt(fields[3]);
                this.auditorium = Integer.parseInt(fields[4]);
            } catch (NumberFormatException exc) {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    // метод переводит данную модель в массив строк для преобразования в TSV
    @Override
    public String[] toFields() {
        return new String[] {
                String.valueOf(this.timestamp),
                this.movieTitle,
                this.dateAndTime,
                String.valueOf(this.movieDuration),
                String.valueOf(this.auditorium)
        };
    }
}
