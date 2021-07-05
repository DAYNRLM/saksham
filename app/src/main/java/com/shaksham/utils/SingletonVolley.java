package com.shaksham.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonVolley extends Volley {

    private static SingletonVolley singletonVolley = null;
    private Context context;
    private RequestQueue requestQueue;

    public SingletonVolley(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized SingletonVolley getInstance(Context appContext) {

        if (singletonVolley == null) {
            singletonVolley = new SingletonVolley(appContext);
        }
        return singletonVolley;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}
