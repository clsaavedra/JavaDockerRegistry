package com.suse.docker.netapi.results;

import com.google.gson.annotations.SerializedName;

public class Return<T> {

    @SerializedName("return")
    private T result;

    /**
     * Returns the value of this result.
     *
     * @return The value of this result.
     */
    public T getResult() {
        return result;
    }

}
