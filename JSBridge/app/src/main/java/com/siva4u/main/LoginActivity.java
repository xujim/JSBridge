package com.siva4u.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.siva4u.jsbridge.R;
import com.xujian.routeme.RouteMe;

public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent it = this.getIntent();
        String hello = it.getStringExtra("sayhello");
        String who = it.getStringExtra("who");
        String userName = it.getStringExtra("userName");

        Button mybutton = (Button)this.findViewById(R.id.mybutton);
        mybutton.setText(hello+who+userName);
        mybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouteMe.getInstance().routeMeTo("RouteMe://page.nx/home", new RouteMe.IRouteMeCompleteCallBack() {
                    @Override
                    public void onComplete(String targetType, Bundle params) {
                        try {
                            Class<?> clazz = LoginActivity.class.getClassLoader().loadClass(targetType);
                            Intent intent = new Intent(LoginActivity.this, clazz);
                            intent.putExtras(params);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            LoginActivity.this.startActivity(intent);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                });
            }
        });
    }

}
