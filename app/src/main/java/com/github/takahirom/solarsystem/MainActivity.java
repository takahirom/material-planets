package com.github.takahirom.solarsystem;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageView = (ImageView) findViewById(R.id.solar_system);

        final Animatable drawable = (Animatable) imageView.getDrawable();
        drawable.start();

        final Button button = (Button) findViewById(R.id.button_live_wallpaper);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
                startActivity(intent);
            }
        });
    }

}
