
package com.app.searchplaces.data.models.venuemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Icon {

    private static final int DEFAULT_RESOLUTION = 100;
    @SerializedName("prefix")
    @Expose
    private String prefix;
    @SerializedName("suffix")
    @Expose
    private String suffix;
    //UI variable - depends on view size
    private int resolution = DEFAULT_RESOLUTION;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * @return returns the complete image url with resolution
     */
    public String getIconUrl(){
        return getPrefix()+ getResolution() + getSuffix();
    }

    public int getResolution() {
        return resolution;
    }

    /**
     * @param resolution sets the resolution. Must be from the list (36, 100, 300, or 500).
     *                   Default is 100.
     */
    public void setResolution(int resolution) {
        if(resolution == 36 || resolution == 100 || resolution == 300 || resolution == 500) {
            this.resolution = resolution;
        }
    }
}
