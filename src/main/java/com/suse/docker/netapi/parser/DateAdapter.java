package com.suse.docker.netapi.parser;

import java.io.IOException;
import java.util.Date;

import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class DateAdapter extends TypeAdapter<Date> {

    @Override
    public void write(JsonWriter jsonWriter, Date date) throws IOException {
        throw new UnsupportedOperationException("Writing JSON not supported.");
    }

    @Override
    public Date read(JsonReader jsonReader) throws IOException {
        try {
            double dateMilliseconds = jsonReader.nextDouble() * 1000;
            return new Date((long) dateMilliseconds);
        } catch (NumberFormatException | IllegalStateException e) {
            throw new JsonParseException(e);
        }
    }

}
