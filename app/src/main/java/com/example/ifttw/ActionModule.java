package com.example.ifttw;

import android.content.Context;

public interface ActionModule {
    public void pushNotification(Context context, int id, String title, String detail);
}
