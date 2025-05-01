package com.shi.hookex;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.shi.hookex.AppInfo;
import java.util.List;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {
    private final List<AppInfo> apps;

    public AppListAdapter(List<AppInfo> apps) {
        this.apps = apps;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView name, pkg, version, status, modVersion, modFeature;

        public ViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.app_icon);
            name = view.findViewById(R.id.app_name);
            pkg = view.findViewById(R.id.app_package);
            version = view.findViewById(R.id.app_version);
            status = view.findViewById(R.id.app_status);
            modVersion = view.findViewById(R.id.mod_version);
            modFeature = view.findViewById(R.id.mod_features);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_app, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppInfo app = apps.get(position);
        if (app.getIcon() != null) {
            holder.icon.setImageDrawable(app.getIcon());
        } else {
            holder.icon.setImageResource(R.drawable.ic_default_app);
        }
        holder.name.setText(app.getName());
        holder.pkg.setText(app.getPackageName());
        holder.version.setText(app.getVersion());
        
        holder.status.setText(app.isInstalled() ? "✓ Installed" : "✗ Not installed");
        holder.modFeature.setText("Mod: " + app.getModFeature());
        holder.modVersion.setText(app.getModVersion());
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }
}