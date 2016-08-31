package com.suse.docker.netapi.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.suse.docker.netapi.datatypes.Token;
import com.suse.docker.netapi.results.Return;

public class JsonParser<T> {

    public static final Gson GSON = new GsonBuilder()
            // null rejecting strict variants for primitives
            .registerTypeAdapter(String.class, Adapters.STRING)
            .registerTypeAdapter(Boolean.class, Adapters.BOOLEAN)
            .registerTypeAdapter(Integer.class, Adapters.INTEGER)
            .registerTypeAdapter(Long.class, Adapters.LONG)
            .registerTypeAdapter(Double.class, Adapters.DOUBLE)
            .registerTypeAdapter(Date.class, new DateAdapter().nullSafe())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeISOAdapter())
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeISOAdapter())
            .create();

    public static final JsonParser<Return<String>> STRING =
            new JsonParser<>(new TypeToken<Return<String>>(){});
    public static final JsonParser<Return<List<Token>>> TOKEN =
            new JsonParser<>(new TypeToken<Return<List<Token>>>(){});
    public static final JsonParser<Return<List<Map<String, Map<String, Object>>>>> RETMAPS =
            new JsonParser<>(
            new TypeToken<Return<List<Map<String, Map<String, Object>>>>>(){});
    public static final JsonParser<Return<List<Map<String, Object>>>> RUN_RESULTS =
            new JsonParser<>(new TypeToken<Return<List<Map<String, Object>>>>(){});
    public static final JsonParser<Map<String, Object>> MAP =
            new JsonParser<>(new TypeToken<Map<String, Object>>(){});

    private final TypeToken<T> type;
    private final Gson gson;

    /**
     * Created a new JsonParser for the given type.
     *
     * @param type A TypeToken describing the type this parser produces.
     */
    public JsonParser(TypeToken<T> type) {
        this(type, GSON);
    }

    /**
     * Created a new JsonParser for the given type.
     *
     * @param type A TypeToken describing the type this parser produces.
     * @param gson Gson instance to use for parsing.
     */
    public JsonParser(TypeToken<T> type, Gson gson) {
        this.type = type;
        this.gson = gson;
    }

    /**
     * Parses a Json response that has a direct representation as a Java class.
     * @param inputStream result stream to parse.
     * @return The parsed value.
     */
    public T parse(InputStream inputStream) {
        Reader inputStreamReader = new InputStreamReader(inputStream);
        Reader streamReader = new BufferedReader(inputStreamReader);

        // Parse result type from the returned JSON
        return gson.fromJson(streamReader, type.getType());
    }

    /**
     * Parse JSON given as string.
     * @param jsonString JSON input given as string
     * @return The parsed object
     */
    public T parse(String jsonString) {
        return gson.fromJson(jsonString, type.getType());
    }
}
