package com.shi.hookex.Hook;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookShared {
    public static void hookXSharedPreferences(XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            // Hook getInt on the real implementation
            XposedHelpers.findAndHookMethod(
                "android.app.SharedPreferencesImpl",
                lpparam.classLoader,
                "getInt",
                String.class, int.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        if ("coin_count".equals(param.args[0])) {
                            XposedBridge.log("[Hook] getInt coin_count -> 9999");
                            param.setResult(9999);
                        }
                    }
                }
            );

            // Hook putInt to override any attempt to set coins
            XposedHelpers.findAndHookMethod(
                "android.app.SharedPreferencesImpl$EditorImpl",
                lpparam.classLoader,
                "putInt",
                String.class, int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        if ("coin_count".equals(param.args[0])) {
                            XposedBridge.log("[Hook] putInt coin_count: " + param.args[1] + " -> 9999");
                            param.args[1] = 9999;
                        }
                    }
                }
            );

            // Optional: force contains to always say coin exists
            XposedHelpers.findAndHookMethod(
                "android.app.SharedPreferencesImpl",
                lpparam.classLoader,
                "contains",
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        if ("coin_count".equals(param.args[0])) {
                            XposedBridge.log("[Hook] contains coin_count -> true");
                            param.setResult(true);
                        }
                    }
                }
            );

        } catch (Throwable t) {
            XposedBridge.log("XSharedPreferences hook failed: " + t.getMessage());
        }
    }
}