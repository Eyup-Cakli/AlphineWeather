package com.example.alphineweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{

    EditText editText;
    Button button;
    ImageView imageView;
    TextView ülke, şehir_adı, zaman, sıcaklık, enlem, boylam, nem, min_sıcaklık, max_sıcaklık, basınc, rüzgarhızı, hissedilen_sıcaklık;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextTextPersonName);
        imageView =findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        ülke = findViewById(R.id.ülke);
        şehir_adı = findViewById(R.id.şehir);
        sıcaklık = findViewById(R.id.textView5);
        enlem = findViewById(R.id.enlem);
        boylam = findViewById(R.id.boylam);
        nem = findViewById(R.id.nem);
        min_sıcaklık = findViewById(R.id.min_sıcaklık);
        max_sıcaklık = findViewById(R.id.max_sıcaklık);
        basınc = findViewById(R.id.basınç);
        rüzgarhızı =findViewById(R.id.rüzgarhızı);
        zaman = findViewById(R.id.zaman);
        hissedilen_sıcaklık = findViewById(R.id.hissedilen);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FindWeather();
            }


        });
    }
    public void FindWeather()
    {
        final String city =editText.getText().toString();
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=1281b514e1ba14459a9b04fa41e03426&units=metric";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    //Sıcaklık
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject object = jsonObject.getJSONObject("main");
                    double temp = object.getDouble("temp");
                    sıcaklık.setText(temp+"°C");

                    //Hissedilen Sıcaklık
                    JSONObject JsonObject_9 = new JSONObject(response);
                    JSONObject object9 =JsonObject_9.getJSONObject("main");
                    double feels = object9.getDouble("feels_like");
                    hissedilen_sıcaklık.setText(feels+"°C");

                    //Hava Durumu İconu
                    JSONArray jsonArray = jsonObject.getJSONArray("weather");
                    JSONObject obj = jsonArray.getJSONObject(0);
                    String icon = obj.getString("icon");
                    Picasso.get().load("http://openweathermap.org/img/wn/"+icon+"@2x.png").into(imageView);

                    //Tarih ve Saat
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat std = new SimpleDateFormat("dd/MM/yyyy \nHH:mm:ss");
                    String date = std.format(calendar.getTime());
                    zaman.setText(date);

                    //Enlem
                    JSONObject object1 = jsonObject.getJSONObject("coord");
                    double lat = object1.getDouble("lat");
                    enlem.setText(lat+"°  N");

                    //Boylam
                    JSONObject object2 = jsonObject.getJSONObject("coord");
                    double lon = object2.getDouble("lon");
                    boylam.setText(lon+"°  E");

                    //Nem Oranı
                    JSONObject object3 = jsonObject.getJSONObject("main");
                    int hum = object3.getInt("humidity");
                    nem.setText(hum+"  %");

                    //Min Sıcaklık
                    JSONObject object4 = jsonObject.getJSONObject("main");
                    String rise = object4.getString("temp_min");
                    min_sıcaklık.setText(rise+"°C");

                    //Max Sıcaklık
                    JSONObject object5 = jsonObject.getJSONObject("main");
                    String set = object5.getString("temp_max");
                    max_sıcaklık.setText(set+"°C");

                    //Basınç
                    JSONObject object6 = jsonObject.getJSONObject("main");
                    int pres = object6.getInt("pressure");
                    basınc.setText(pres+"  hPa");

                    //Rüzgar Hızı
                    JSONObject object7 = jsonObject.getJSONObject("wind");
                    double wind_speed = object7.getDouble("speed");
                    rüzgarhızı.setText(wind_speed+"  km/h");

                    //Ülke Kısaltması
                    JSONObject object8 = jsonObject.getJSONObject("sys");
                    String count = object8.getString("country");
                    ülke.setText(count+"   :");

                    //Şehir
                    String city = jsonObject.getString("name");
                    şehir_adı.setText(city);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(MainActivity.this,"Yanlış Şehir Adı",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue =Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }


}