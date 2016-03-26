package com.github.takahirom.solarsystem;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.License;
import de.psdev.licensesdialog.licenses.MITLicense;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_license) {
            final Notices notices = new Notices();
            notices.addNotice(new Notice("LicensesDialog", "http://psdev.de", "Copyright 2013 Philip Schiffer <admin@psdev.de>", new ApacheSoftwareLicense20()));

            notices.addNotice(new Notice("MikeOrtiz/TouchImageView", "https://github.com/MikeOrtiz/TouchImageView", "Copyright (c) 2012 Michael Ortiz", new MITLicense()));
            new LicensesDialog.Builder(this)
                    .setNotices(notices)
                    .build()
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
