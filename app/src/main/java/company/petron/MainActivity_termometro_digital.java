package company.petron.termometro_digital;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

import java.net.URI;
import java.util.List;

public class MainActivity_termometro_digital extends AppCompatActivity implements SensorEventListener{
    private TextView temperatura,temperaturaf,estado_sensor,temperatura1;
    private ImageView exite_ter;
    private StartAppAd startAppAd = new StartAppAd(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this, "101423750", "203739616", true);
        setContentView(R.layout.activity_main_activity_termometro_digital);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        temperatura =(TextView) findViewById(R.id.temperatura);
        temperaturaf =(TextView) findViewById(R.id.temperatura_f);
        estado_sensor = (TextView) findViewById(R.id.estado_sensor);
        estado_sensor.setTextColor(getResources().getColor(R.color.color_text));
        estado_sensor.setText((R.string.estado_sensor));
        exite_ter = (ImageView) findViewById(R.id.imagen_existe_termometro);
        temperatura1 = (TextView) findViewById(R.id.temperatura1);
        temperatura1.setText(getResources().getString(R.string.temperatura));
        temperatura1.setTextColor(getResources().getColor(R.color.color_text));




        //Cargamos el sensor de temperatura
        SensorManager sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        Sensor temp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorManager.registerListener(this,temp,SensorManager.SENSOR_DELAY_UI);
        if (temp == null){
            Toast.makeText(getApplicationContext(), getString(R.string.no_sensor), Toast.LENGTH_SHORT).show();
            exite_ter.setImageResource(R.mipmap.imagen_aspa1);
        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.si_sensor), Toast.LENGTH_SHORT).show();
            exite_ter.setImageResource(R.mipmap.imagen_ok1);
            sensorManager.registerListener(this, temp, SensorManager.SENSOR_DELAY_NORMAL);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                //vemos el dialogo de ayuda
                openAlert(view);

            }
        });



    }
    private void openAlert (View view){
        //creamos un dialogo de ayuda
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)(this));
        builder.setMessage((CharSequence)(getResources().getString(R.string.toast_ayuda)))
                .setIcon(R.mipmap.ic_launcher).setTitle((CharSequence) (getResources().getString(R.string.ayuda)))
                .setCancelable(false).setNeutralButton((CharSequence) (getResources().getString(R.string.ok)),
                (DialogInterface.OnClickListener) (new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogInterface, int n) {
                        dialogInterface.cancel();
                    }
                }));
        builder.create().show();
    }
    @Override
    protected void onResume() {

        super.onResume();
        SensorManager sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        Sensor temp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorManager.registerListener(this, temp, SensorManager.SENSOR_DELAY_UI);
        if (temp == null){
            Toast.makeText(getApplicationContext(), getString(R.string.no_sensor), Toast.LENGTH_SHORT).show();
            exite_ter.setImageResource(R.mipmap.imagen_aspa1);
        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.si_sensor), Toast.LENGTH_SHORT).show();
            exite_ter.setImageResource(R.mipmap.imagen_ok1);
            sensorManager.registerListener(this, temp, SensorManager.SENSOR_DELAY_NORMAL);
        }
        startAppAd.onResume();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();



        //poner en marcha la publicidad al salir
        MainActivity_termometro_digital.this.startAppAd.showAd();
        MainActivity_termometro_digital.this.startAppAd.loadAd();

    }

    @Override
    public void onPause() {
        super.onPause();
        startAppAd.onPause();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float [] values = event.values;
        final double faren = values[0] * 1.8 + 32;


        // convertimos float a String con un solo decimal.
        String convertido;
        convertido =  String.format("%.1f", values[0]);
        String grafaren;
        grafaren = String.format("%.1f",faren);



        if (values[0] <= 10){
            temperatura.setTextColor(Color.rgb(3,169,244));
            temperaturaf.setTextColor(Color.rgb(3, 169, 244));
        }

        if (values[0]>10 || values[0] <20){
            temperatura.setTextColor(Color.rgb(76,175,80));
            temperaturaf.setTextColor(Color.rgb(76, 175, 80));
        }
        if (values[0]>=20){
            temperatura.setTextColor(Color.rgb(211,47,47));
            temperaturaf.setTextColor(Color.rgb(211, 47, 47));
        }
        temperatura.setText(convertido + getResources().getString(R.string.grados_centigrados));
        temperaturaf.setText(grafaren + getResources().getString(R.string.grados_fahrenheit));

    }



    @Override
    protected void onStop() {

        super.onStop();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_termometro_digital, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.votar:
                //botón votar
                Uri uriUrl = Uri.parse("https://play.google.com/store/apps/details?id=company.petron.termometro_digital");
                Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(intent);
                return  true;
            case R.id.salir:
                //botón salir
               MainActivity_termometro_digital.this.startAppAd.showAd();
               MainActivity_termometro_digital.this.startAppAd.loadAd();

                finish();
                return true;

        }


        return super.onOptionsItemSelected(item);
    }

}
