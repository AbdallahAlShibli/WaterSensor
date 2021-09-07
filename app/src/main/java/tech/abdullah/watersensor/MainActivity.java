package tech.abdullah.watersensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button waterRate, electricRate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        waterRate = findViewById(R.id.waterrate);
        electricRate = findViewById(R.id.electricrate);

        waterRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WaterRateActivity.class));
            }
        });


        electricRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "الخدمة غير مفعلة حاليا!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}