package kamrah.tattooedbellydancer.com.mobilebelly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Splash screen for logo and loading
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Creates a new intent to start Main Activity after loading
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
