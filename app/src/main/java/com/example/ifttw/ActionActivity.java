package com.example.ifttw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActionActivity extends AppCompatActivity {
    private Bundle bundleSource = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        Button notification = (Button) findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchNotificationActivity(view);
            }
        });

        bundleSource = getIntent().getExtras();
    }

    public void launchNotificationActivity(View view) {
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtras(bundleSource);
        startActivity(intent);
    }
}
