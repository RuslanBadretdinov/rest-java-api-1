package ru.badretdinov.restapi.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class CommonModel {
    @JsonProperty(value = "timestamp", required = false, defaultValue = "0")
    public final long timestamp;

    @JsonCreator
    protected CommonModel(@JsonProperty("timestamp") final long timestamp) {
        this.timestamp = timestamp == 0 ? (System.currentTimeMillis() / 1000) : timestamp;
    }

    protected CommonModel() {
        this(0);
    }

    protected CommonModel(final String[] fields) throws IllegalArgumentException {
        if (fields != null && fields.length > 1) {
            try {
                this.timestamp = Long.parseLong(fields[0]);
            } catch (NumberFormatException exc) {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    //метод переводит данную модель в массив строк для преобразования в TSV
    public abstract String[] toFields();
}
