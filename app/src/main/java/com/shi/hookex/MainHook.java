package com.shi.hookex;

import com.shi.hookex.Hook.HookShared;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import com.shi.hookex.Hook.Hook;

public class MainHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        switch (lpparam.packageName) {
            case "com.examplex.yo":
                Hook.example(lpparam);
                HookShared.hookXSharedPreferences(lpparam);
                break;
            case "com.niven.translator":
                Hook.Bubble(lpparam);
                break;
            case "com.examplex.yp":
                Hook.IGHook(lpparam);
                break;
                case "com.nexstreaming.app.kinemasterfree":
                Hook.KMHook(lpparam);
                Hook.KMhook(lpparam);
                break;
        }
    }
}