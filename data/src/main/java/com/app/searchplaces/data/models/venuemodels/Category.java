
package com.app.searchplaces.data.models.venuemodels;

import com.google.gson.annotations.Expose;

public class Category {

    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private Icon icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
