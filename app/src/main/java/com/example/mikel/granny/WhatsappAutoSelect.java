package com.example.mikel.granny;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by mikel on 2018/1/13.
 */

public class WhatsappAutoSelect extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        // Get intent, action and MIME type
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        String taskType = intent.getStringExtra("task");
        if(taskType.equals(" ")){
            onClickWhatsApp();
        }

    }

    public void onClickWhatsApp() {

        PackageManager pm=getPackageManager();
        try {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "YOUR TEXT HERE";

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            this.startActivity(Intent.createChooser(waIntent, "Share with"));
            this.finish();

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

    }


    public void openWhatsappContact1(String number) {
        Uri uri = Uri.parse("smsto:" + number);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);

//        i.setType("text/plain");
        String text = "YOUR TEXT HERE";
        i.putExtra(Intent.EXTRA_TEXT, text);
        i.setPackage("com.whatsapp");
        this.startActivity(Intent.createChooser(i, ""));
        this.finish();
    }


}
