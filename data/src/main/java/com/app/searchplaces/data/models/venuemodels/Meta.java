
package com.app.searchplaces.data.models.venuemodels;

import com.google.gson.annotations.Expose;

/**
 * Unique for server error responses
 */
public class Meta {

    @Expose
    private Integer code;
    @Expose
    private String requestId;
    @Expose
    private String errorType;
    @Expose
    private String errorDetail;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }
}
