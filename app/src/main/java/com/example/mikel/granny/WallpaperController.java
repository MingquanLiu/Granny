package com.example.mikel.granny;

import android.app.WallpaperManager;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by mikel on 2018/1/13.
 */

public class WallpaperController {
    private Context context;
    public WallpaperController(Context context){
        this.context = context;
    }

    public void changeWallPaper() throws IOException {
        WallpaperManager wpm = WallpaperManager.getInstance(context);
        Uri uri = Uri.parse("android.resource://com.example.mikel.granny/drawable/wp");
        wpm.setResource(R.drawable.wp);
    }
}
