package com.siva4u.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.siva4u.jsbridge.JSBridge;
import com.siva4u.jsbridge.R;
import com.xujian.routeme.*;

import java.io.InputStream;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Uri path = Uri.parse("file:///android_asset/routemap.json");
//        String newPath = path.toString();
        try {
            InputStream is = this.getAssets().open("routemap.json");
            RouteMe.getInstance().loadConfigFrom(is);
        }catch (Exception ex){

        }

        Button mybutton = (Button)this.findViewById(R.id.helloweb);
        mybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouteMe.getInstance().routeTo("RouteMe://page.nx/home", new RouteMe.IRouteCompleteCallBack() {
                    @Override
                    public void onComplete(Intent intent) {
                        MainActivity.this.startActivity(intent);
                    }
                });

            }
        });

        Button myloginbutton = (Button)this.findViewById(R.id.login);
        myloginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouteMe.getInstance().routeMeTo("RouteMe://page.nx/login", new RouteMe.IRouteMeCompleteCallBack() {
                    @Override
                    public void onComplete(String targetType, Bundle bundle) {
                        try {
                            Class<?> clazz = MainActivity.class.getClassLoader().loadClass(targetType);
                            Intent intent = new Intent(MainActivity.this, clazz);
                            intent.putExtras(bundle);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            MainActivity.this.startActivity(intent);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                });

            }
        });
    }

}
