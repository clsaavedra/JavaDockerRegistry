package com.suse.docker.netapi.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.suse.docker.netapi.results.Catalog;
import com.suse.docker.netapi.results.Tags;

public class JsonParser<T> {

    public static final Gson GSON = new GsonBuilder()
            // null rejecting strict variants for primitives
            .registerTypeAdapter(String.class, Adapters.STRING)
            .registerTypeAdapter(Boolean.class, Adapters.BOOLEAN)
            .registerTypeAdapter(Integer.class, Adapters.INTEGER)
            .registerTypeAdapter(Long.class, Adapters.LONG)
            .registerTypeAdapter(Double.class, Adapters.DOUBLE)
            .create();

    public static final JsonParser<Catalog> CATALOG = new JsonParser<>(new TypeToken<Catalog>() {});
    public static final JsonParser<Tags> TAGS = new JsonParser<>(new TypeToken<Tags>() {});
    
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
