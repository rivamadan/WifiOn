package com.riva.WifiOn;

import android.app.Application;

/**
 * Created by Riva on 1/30/2016.
 */
public class Global extends Application {

    private boolean isChecked;

    public void setChecked(boolean check) {
        isChecked = check;
    }

    public boolean isChecked() {
        return isChecked;
    }


}
