package com.app.searchplaces.events;

/**
 * Event class used to pass events between components
 */
public class MessageEvent {

    public final Object message;
    public final int eventID;

    /**
     *
     * @param eventID Unique id for the event object, Should be from {@class} {@link EventConstant} class
     * @param message message to be delivered to subscriber, Can be of any type.
     */
    public MessageEvent(int eventID,Object message) {
        this.message = message;
        this.eventID = eventID;
    }
}