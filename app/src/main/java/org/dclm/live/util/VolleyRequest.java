package org.dclm.live.util;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public abstract class VolleyRequest {

    private static RequestQueue volley;

    public static synchronized RequestQueue getVolley(Context context){
        if (volley == null){
            volley = Volley.newRequestQueue(context.getApplicationContext());
        }
        return volley;
    }
}
