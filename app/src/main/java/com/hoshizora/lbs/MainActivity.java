package com.hoshizora.lbs;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView warnText;
    private Button search_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        warnText = findViewById(R.id.warn);
        Drawable[] warnImg = warnText.getCompoundDrawables();
        warnImg[3].setBounds(0,0,900,280);
        warnText.setCompoundDrawables(null,null,null,warnImg[3]);

        search_button = findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
