package com.siva4u.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.siva4u.jsbridge.R;

public class OtherActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        Button mybutton = (Button)this.findViewById(R.id.mybutton);
        mybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                OtherActivity.this.finish();
                Intent webViewActivity = new Intent(OtherActivity.this, WebViewActivity.class);
                webViewActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                OtherActivity.this.startActivity(webViewActivity);

            }
        });
    }

}
