package com.app.searchplaces.events;

/**
 * Used to provide unique identifier for {@class} {@link MessageEvent} class
 * <p> Define all the event ID's here only. Make sure you increment new ID by previous ID plus 1.
 */
public class EventConstant {
    public static final int INFO_SNACK_BAR = 0;
    public static final int SNACKBAR_WITH_RETRY = INFO_SNACK_BAR + 1;
    public static final int SHOW_HIDE_LOADER = SNACKBAR_WITH_RETRY + 1;
    public static final int VENUE_ITEM = SHOW_HIDE_LOADER + 1;
}
