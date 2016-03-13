package diveengine2d.components.ui;

import diveengine2d.DiveScript;

import java.lang.reflect.Method;

/**
 * Created by Marcus on 3/10/2016.
 */
public class Button extends DiveScript {

    private static final int NONE = 0;
    private static final int HOVER = 0;
    private static final int DOWN = 0;

    public String eventClass = null;
    public String eventName = null;
    private Method event;
    private boolean linked = false;
    private DiveScript eventListener;
    private int state = NONE;


    public void create() {
        try {
            Class<?> eventListenerClass = Class.forName(eventClass);
            eventListener = entity.getComponent((Class<? extends DiveScript>)eventListenerClass);
            event = eventListenerClass.getMethod(eventName);
            linked = true;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {

        }
    }

    public void update() {

    }
}
