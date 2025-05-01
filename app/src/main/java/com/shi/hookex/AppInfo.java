package com.shi.hookex;

import android.graphics.drawable.Drawable;

public class AppInfo {
    private String name;
    private String packageName;
    private String version;
    private String modVersion;
    private String modFeatures;
    private boolean isInstalled;
    private Drawable icon;
    
    public AppInfo(String name, String packageName, String version, 
                  String modVersion, String modFeatures, 
                  boolean isInstalled, Drawable icon) {
        this.name = name;
        this.packageName = packageName;
        this.version = version;
        this.modVersion = modVersion;
        this.modFeatures = modFeatures;
        this.isInstalled = isInstalled;
        this.icon = icon;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getVersion() {
        return version;
    }
    
    public String getModVersion() {
        return modVersion;
    }
    
    public String getModFeature() {
        return modFeatures;
    }

    public boolean isInstalled() {
        return isInstalled;
    }

    public Drawable getIcon() {
        return icon;
    }
}