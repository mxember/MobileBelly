package kamrah.tattooedbellydancer.com.mobilebelly;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Main Activity creates for layer generator app
 */
public class MainActivity extends AppCompatActivity {

    //Member variables, textViews for holding labels and generated layers
    private TextView upperBodyMovementResult;
    private TextView hipsMovementResult;
    private TextView travelMovementResult;
    private TextView upperBodySpeedResult;
    private TextView hipsSpeedResult;
    private TextView travelMovementSpeedResult;



    //Button for generating layers
    private Button generateLayersButton;

    //onCreate inflates layers and sets up TextViews and Buttons
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //When FAB is pressed, launch SavedLayers activity to see Saved Layers
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Layer Saved", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                launchSavedLayers();


            }
        });


        //Text Views to show results of random generation of layers
        upperBodyMovementResult = (TextView) findViewById(R.id.upperBodyMovementResult);
        hipsMovementResult = (TextView) findViewById(R.id.hipMovementResult);
        travelMovementResult = (TextView) findViewById(R.id.travelStepResult);
        upperBodySpeedResult = (TextView) findViewById(R.id.upperBodySpeedResult);
        hipsSpeedResult = (TextView) findViewById(R.id.hipSpeedResult);
        travelMovementSpeedResult = (TextView) findViewById(R.id.travelSpeedResult);

        generateLayersButton = findViewById(R.id.generateLayersButton);



    }

    //When FAB is pressed, it will launch the Saved Layers Activity, a RecyclerView of layers saved
    private void launchSavedLayers() {

        //Bundle for holding layer information to be passed to SavedLayers Activity
        Bundle bundle = new Bundle();

        //Get strings of movements generated
        String chestMovement = upperBodyMovementResult.getText().toString();
        String chestTiming = upperBodySpeedResult.getText().toString();
        String hipMovement = hipsMovementResult.getText().toString();
        String hipTiming = hipsSpeedResult.getText().toString();
        String travelMovement = travelMovementResult.getText().toString();
        String travelTiming = travelMovementSpeedResult.getText().toString();

        //Create string of all layers to be supplied to bundle
        String savedLayer = chestMovement + " " + chestTiming + "\n" + hipMovement + " " +
                hipTiming + "\n" + travelMovement + " " + travelTiming + "\n";


        //Add Layer to bundle with keyword Layer
        bundle.putString("Layer", savedLayer);

        //Create intent to start SavedLayers and send new layer to activity
        Intent intent = new Intent(this, SavedLayers.class);
        intent.putExtras(bundle);

        startActivity(intent, bundle);

    }

    //Settings will be in a future build
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.view_saved_layers) {
            Intent intent = new Intent(this, SavedLayers.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    //generateLayers method to generate the layers to display in the TextViews
    public void generateLayers(View view) {

        //Array lists of movements from files
        ArrayList<String> hipMoves = new ArrayList<>();
        ArrayList<String> chestMoves = new ArrayList<>();
        ArrayList<String> travelMoves = new ArrayList<>();
        ArrayList<String> speeds = new ArrayList<>();

        //String variables to hold results
        String hipResult;
        String chestResult;
        String travelResult;
        String hipSpeed;
        String chestSpeed;
        String travelSpeed;

        //Populate arrays from files
        hipMoves = getArray("hipmovements.txt");
        chestMoves = getArray("uppermovements.txt");
        travelMoves = getArray("travelsteps.txt");
        speeds = getArray("speeds.txt");


        //Generate random numbers to randomize movements
        int hipRandom = getRandom(hipMoves);
        int chestRandom = getRandom(chestMoves);
        int travelRandom = getRandom(travelMoves);

        //Generate random speeds
        hipSpeed = getSpeed(speeds);
        chestSpeed = getSpeed(speeds);
        travelSpeed = getSpeed(speeds);

        //Generate random results from random numbers and store in Strings
        hipResult = hipMoves.get(hipRandom);
        chestResult = chestMoves.get(chestRandom);
        travelResult = travelMoves.get(travelRandom);

        //Set TextFields to results
        hipsMovementResult.setText(hipResult);
        hipsSpeedResult.setText(hipSpeed);
        upperBodyMovementResult.setText(chestResult);
        upperBodySpeedResult.setText(chestSpeed);
        travelMovementResult.setText(travelResult);

        //If the travel result is "in place," there is no reason to have a timing
        if (travelResult.equalsIgnoreCase("in place")) {
            travelMovementSpeedResult.setText("");
        } else {
            travelMovementSpeedResult.setText(travelSpeed);

        }


    }

    //Read file of moves and populate the array
    public ArrayList<String> getArray(String filename)
    {
        ArrayList<String> array = new ArrayList<>();
        // Scanner s;

        //To open file
        try {

            InputStream in = getAssets().open(filename);
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            // s = new Scanner(new File(filename));

            String line;
            while ((line = r.readLine()) != null) {
                array.add(line);
            }

            in.close();
        }
        catch (Throwable tx) {
            System.out.println("File does not exist.");
        }

        return array;

    }

    //Generate random numbers according to file size
    public static int getRandom(ArrayList<String> array){
        Random rand = new Random();
        int result;

        result = rand.nextInt(array.size());

        return result;

    }

    //Generate random numbers according to array size of speeds
    public static String getSpeed(ArrayList<String> array) {
        int speed;
        String result;

        speed = getRandom(array);
        result = array.get(speed);

        return result;

    }

    //saveLayer method for future build
    public void saveLayer(View view) {
    }
}

