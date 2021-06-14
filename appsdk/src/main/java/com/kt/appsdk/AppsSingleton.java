package com.kt.appsdk;

import android.app.Application;
import android.content.Context;

public class AppsSingleton extends Application {
    private static AppsSingleton instance = null;
    private static Context context;

    /**
     * To initialize the class. It must be called before call the method getInstance()
     *
     * @param ctx The Context used
     */
    public static void initialize(Context ctx) {
        context = ctx;
    }

    /**
     * Check if the class has been initialized
     *
     * @return true  if the class has been initialized
     * false Otherwise
     */
    public static boolean hasBeenInitialized() {
        return context != null;

    }

    /**
     * The private constructor.
     */
    private AppsSingleton() {
        // Use context to initialize the variables.
    }

    public static synchronized AppsSingleton getInstance() {
        if (context == null) {
            throw new IllegalArgumentException("Unable to get the instance. This class must be initialized before");
        }

        if (instance == null) {
            instance = new AppsSingleton();
        }

        return instance;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Clone is not allowed.");
    }
}
