package com.app.searchplaces.data.models.places;

import com.google.gson.annotations.Expose;

/**
 * Base class for all model classes. Every model class must extend this.
 * Responsible to hold error response meta data
 *
 */
public class BaseModel {

    @Expose
    private Meta meta;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
