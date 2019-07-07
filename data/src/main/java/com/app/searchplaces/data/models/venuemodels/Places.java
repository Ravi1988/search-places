package com.app.searchplaces.data.models.venuemodels;

import com.google.gson.annotations.Expose;

public class Places extends BaseModel{
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
