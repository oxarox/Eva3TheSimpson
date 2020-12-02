package cl.inacap.simpsonsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cl.inacap.simpsonsapp.adapters.CitasListAdapter;
import cl.inacap.simpsonsapp.dto.Cita;

public class MainActivity extends AppCompatActivity {

    private ListView listViewCitas;
    private Spinner spNumeroCitas;
    private Button solicitarCitasBtn;
    private RequestQueue queue;
    private List<Cita> citas = new ArrayList<>();
    private CitasListAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.spNumeroCitas = findViewById(R.id.spnNumCitas);
        String[] list = new String[10];
        for (int i = 0; i < 10; i++) {
            list[i] = "" + i;
        }
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,list);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNumeroCitas.setAdapter(adapterSpinner);
        this.listViewCitas = findViewById(R.id.listView);
        this.adapter = new CitasListAdapter(this, R.layout.citas_list,this.citas);
        this.listViewCitas.setAdapter(this.adapter);
        this.solicitarCitasBtn = findViewById(R.id.btnSolicitar);
        this.solicitarCitasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queue = Volley.newRequestQueue(MainActivity.this);
                String cantidadCitas = spNumeroCitas.getSelectedItem().toString().trim();
                JsonArrayRequest jsonReq = new JsonArrayRequest(Request.Method.GET
                        , "https://thesimpsonsquoteapi.glitch.me/quotes?count="+cantidadCitas
                        ,null
                        , new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            citas.clear();
                            Cita[] citaObt = new Gson()
                                    .fromJson(response.toString(),
                                            Cita[].class);
                            citas.addAll(Arrays.asList(citaObt));
                        } catch (Exception ex) {
                            citas = null;
                        } finally {
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        citas = null;
                        adapter.notifyDataSetChanged();
                    }
                });
                queue.add(jsonReq);
            }
        });
    }
}