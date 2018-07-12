package br.com.customsearchable.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.os.Process;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by edgar on 6/27/15.
 */
public class ManifestParser {
    // Constants
    public static final String SEARCH_INTENT_FILTER = "android.intent.action.SEARCH";

    public static String getAppPackage (Context context) {
        return context.getApplicationContext().getPackageName();
    }

    public static String getSearchableActivity (Context context) {
        String packageName = context.getApplicationContext().getPackageName();

        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent(SEARCH_INTENT_FILTER);
        List<ResolveInfo> resolvedInfo = pm.queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER);

        for (ResolveInfo info: resolvedInfo) {
            if (info.activityInfo != null
                    && info.activityInfo.packageName.equalsIgnoreCase(packageName)) {
                return info.activityInfo.name;
            }
        }

        return null;
    }

    public static Map<String, String> getProviderNameAndAuthority (Context context) {
        String packageName = context.getApplicationContext().getPackageName();

        Map<String, String> out = new HashMap<>();

        PackageManager pm = context.getPackageManager();
        List<ProviderInfo> providers = pm.queryContentProviders(packageName, Process.myUid(), 0);

        for (ProviderInfo info: providers) {
            out.put(info.name, info.authority);
        }

        return out;
    }
}
