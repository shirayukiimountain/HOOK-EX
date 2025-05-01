package com.shi.hookex.Hook;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import android.widget.Toast;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import android.content.Context;
import android.app.Activity;

public class Hook {
    public static void Bubble(XC_LoadPackage.LoadPackageParam lpparam) {
        XposedHelpers.findAndHookMethod(
            "com.niven.translate.data.vo.billing.BillingStatus",
            lpparam.classLoader,
            "isPro",
            XC_MethodReplacement.returnConstant(true)
        );
        XposedHelpers.findAndHookMethod(
            "com.niven.translate.data.vo.billing.BillingStatus",
            lpparam.classLoader,
            "isProLifelong",
            XC_MethodReplacement.returnConstant(true)
        );
    }
    public static void example(XC_LoadPackage.LoadPackageParam lpparam) {
    try {
        // Show toast when hook starts
        XposedHelpers.findAndHookMethod(
            "com.examplex.yo.MainActivity",
            lpparam.classLoader,
            "isPremium",
            new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    // Show toast on UI thread
                    //XposedBridge.log("Hooking isPremium()");
                    showToast(lpparam, "✓ Premium unlocked!");
                }
                
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    param.setResult(true); // Force return true
                }
            }
        );
    } catch (Throwable t) {
        //XposedBridge.log("Hook failed: " + t);
        showToast(lpparam, "⚠ Hook failed!");
    }
}
    
    public static void IGHook(XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            XposedHelpers.findAndHookMethod(
                "com.examplex.yp.MainActivity",
                lpparam.classLoader,
                "isPremium",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        showToast(lpparam, "✓ Hook active2!");
                        //Log.d("TAG", "A0B called with args: " + param.args[0] + ", " + param.args[1]);
                    }
    
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                       param.setResult(true);
                    }
                }
            );
        } catch (Throwable t) {
            //Log.e("TAG", "IG hook failed", t);
            showToast(lpparam, "⚠ failed!");
        }
    }
    
    public static void KMHook(XC_LoadPackage.LoadPackageParam lpparam) {
    try {
        Class<?> subscribeResponseDto = XposedHelpers.findClass(
            "com.kinemaster.module.network.communication.account.dto.SubscribeResponseDto",
            lpparam.classLoader
        );

        // Hook all constructors
        XposedBridge.hookAllConstructors(subscribeResponseDto, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object instance = param.thisObject;
                
                XposedHelpers.setBooleanField(instance, "isSubscribed", true);
                XposedHelpers.setBooleanField(instance, "isAutoRenew", false);
                XposedHelpers.setObjectField(instance, "platform", "Premium");
                XposedHelpers.setObjectField(instance, "licenseType", "Lifetime");
                XposedHelpers.setLongField(instance, "expiresDate", 2147483647000L);
                XposedHelpers.setLongField(instance, "startDate", System.currentTimeMillis());
                
                // Show toast using the safest method
            }
        });

    } catch (Throwable t) {
        //XposedBridge.log("Failed to hook SubscribeResponseDto: " + t);
    }
}
    
    public static void KMhook(XC_LoadPackage.LoadPackageParam lpparam) {
    try {
        // Check SharedPreferences for first launch
        Context appContext = getApplicationContext(lpparam);
        SharedPreferences prefs = appContext.getSharedPreferences("km_hook", Context.MODE_PRIVATE);
        boolean isFirstLaunch = prefs.getBoolean("first_launch", true);

        XposedHelpers.findAndHookMethod(
            "com.kinemaster.app.screen.home.HomeActivity",
            lpparam.classLoader,
            "onCreate",
            Bundle.class,
            new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    if (isFirstLaunch) {
                        Activity activity = (Activity) param.thisObject;
                        activity.runOnUiThread(() -> {
                            Toast.makeText(
                                activity,
                                "✓ Premium Unlocked!",
                                Toast.LENGTH_LONG
                            ).show();
                            
                            // Mark as launched
                            prefs.edit().putBoolean("first_launch", false).apply();
                        });
                    }
                }
            }
        );
    } catch (Throwable t) {
        XposedBridge.log("HomeActivity hook error: " + t);
    }
}

private static Context getApplicationContext(XC_LoadPackage.LoadPackageParam lpparam) {
    try {
        Class<?> activityThread = XposedHelpers.findClass("android.app.ActivityThread", lpparam.classLoader);
        Object at = XposedHelpers.callStaticMethod(activityThread, "currentActivityThread");
        return (Context) XposedHelpers.callMethod(at, "getApplication");
    } catch (Throwable t) {
        return null;
    }
}

private static void showToast(XC_LoadPackage.LoadPackageParam lpparam, String message) {
        XposedHelpers.findAndHookMethod(
            "android.app.Activity", lpparam.classLoader, "onResume",
            new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    ((Activity)param.thisObject).runOnUiThread(() -> 
                        Toast.makeText(
                            (Context)param.thisObject, 
                            message, 
                            Toast.LENGTH_SHORT
                        ).show()
                    );
                }
            }
        );
    }
}