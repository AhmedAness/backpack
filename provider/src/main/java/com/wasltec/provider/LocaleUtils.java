package com.wasltec.provider;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;

import java.util.Locale;

public class LocaleUtils {
    public static void updateConfig(Context mContext, String sLocale) {
        Locale locale = new Locale(sLocale);
        Locale.setDefault(locale);
        Configuration config = mContext.getResources().getConfiguration();
        config.locale = locale;
        config.setLayoutDirection(locale);
        mContext.getResources().updateConfiguration(config,
                mContext.getResources().getDisplayMetrics());
    }
}