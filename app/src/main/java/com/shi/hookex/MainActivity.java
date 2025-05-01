package com.shi.hookex;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import java.util.ArrayList;
import java.util.List;
import android.content.Context; 
import android.content.pm.ApplicationInfo;  // For app label

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    // Mod information package, version, modinfo
    private static final ModInfo[] MOD_DATABASE = {
        new ModInfo("com.examplex.yo", "1.0", "Unlock Premium"),
        new ModInfo("com.niven.translator", "4.3.6", "Pro Features Unlocked"),
        new ModInfo("com.nexstreaming.app.kinemasterfree", "7.6.16.34690.GP", "Unlock Premium")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    LottieAnimationView lottieView = findViewById(R.id.lottieBackground);
    lottieView.setAnimation(R.raw.bg);
    lottieView.setRepeatCount(LottieDrawable.INFINITE);
    lottieView.playAnimation();

    recyclerView = findViewById(R.id.recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    
    List<AppInfo> appList = buildAppList();
    recyclerView.setAdapter(new AppListAdapter(appList));
}

    private List<AppInfo> buildAppList() {
        List<AppInfo> appList = new ArrayList<>();
        PackageManager pm = getPackageManager();

        for (ModInfo mod : MOD_DATABASE) {
            String packageName = mod.getPackageName();
            try {
                String appName = pm.getApplicationLabel(pm.getApplicationInfo(packageName, 0)).toString();
                String version = pm.getPackageInfo(packageName, 0).versionName;
                Drawable icon = pm.getApplicationIcon(packageName);
                
                appList.add(new AppInfo(
                    appName,
                    packageName,
                    version,
                    mod.getModVersion(),
                    mod.getModFeatures(),
                    true,
                    icon
                ));
            } catch (PackageManager.NameNotFoundException e) {
                appList.add(new AppInfo(
                    packageName,
                    packageName,
                    "Not installed",
                    mod.getModVersion(),
                    mod.getModFeatures(),
                    false,
                    getResources().getDrawable(R.drawable.ic_default_app)
                ));
            }
        }
        return appList;
    }

    // Helper class for mod information
    private static class ModInfo {
        private final String packageName;
        private final String modVersion;
        private final String modFeatures;

        public ModInfo(String packageName, String modVersion, String modFeatures) {
            this.packageName = packageName;
            this.modVersion = modVersion;
            this.modFeatures = modFeatures;
        }

        public String getPackageName() { return packageName; }
        public String getModVersion() { return modVersion; }
        public String getModFeatures() { return modFeatures; }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LottieAnimationView lottieView = findViewById(R.id.lottieBackground);
        if (lottieView != null) {
            lottieView.resumeAnimation();
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        LottieAnimationView lottieView = findViewById(R.id.lottieBackground);
        if (lottieView != null) {
            lottieView.pauseAnimation();
        }
    }
    
    @Override
    protected void onDestroy() {
        LottieAnimationView lottieView = findViewById(R.id.lottieBackground);
        if (lottieView != null) {
            lottieView.cancelAnimation();
        }
        if (recyclerView != null) {
            recyclerView.setAdapter(null);
        }
        super.onDestroy();
    }

}