package com.elisha.superformula;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import processing.android.PFragment;
import processing.android.CompatUtils;
import processing.core.PApplet;
public class MainActivity extends AppCompatActivity {
    private PApplet sketch;
    @Override
    @SuppressWarnings("ConstantConditions")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean wallpaper = true; //TODO: (if you need) change this to make the app show the processing but not ask to make it the wallpaper
        if (wallpaper) {
            Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(this, Wallpaper.class));
            startActivity(intent);
        }
        else {
            FrameLayout frame = new FrameLayout(this);
            frame.setId(CompatUtils.getUniqueViewId());
            setContentView(frame, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            sketch = new Sketch();
            PFragment fragment = new PFragment(sketch);
            fragment.setView(frame, this);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (sketch != null) {
            sketch.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (sketch != null) {
            sketch.onNewIntent(intent);
        }
        System.exit(0);
    }
}