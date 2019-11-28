package org.nativescript.robotcontrol.toaster;

import android.content.Context;
import android.widget.Toast;

public class Toaster {
    public void showToast(Context context, String text, String StrDuration) {
        int duration;
        switch (StrDuration) {
            case "short":
                duration = Toast.LENGTH_SHORT;
                break;
            case "long":
                duration = Toast.LENGTH_LONG;
                break;
        }
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}