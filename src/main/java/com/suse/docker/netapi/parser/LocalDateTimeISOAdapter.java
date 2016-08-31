package com.suse.docker.netapi.parser;

import java.io.IOException;
import java.time.LocalDateTime;

import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class LocalDateTimeISOAdapter extends TypeAdapter<LocalDateTime> {

    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime date) throws IOException {
        if (date == null) {
            throw new JsonParseException("null is not a valid value for LocalDateTime");
        } else {
            jsonWriter.value(date.toString());
        }
    }

    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            throw new JsonParseException("null is not a valid value for LocalDateTime");
        }
        String dateStr = jsonReader.nextString();
        return LocalDateTime.parse(dateStr);
    }


}
