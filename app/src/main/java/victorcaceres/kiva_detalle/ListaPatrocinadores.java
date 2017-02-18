package victorcaceres.kiva_detalle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Victor Cáceres on 16/2/2017.
 */

public class ListaPatrocinadores extends AppCompatActivity {
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_patrocinadores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Nuestros Socios");

        mContext=this;
        String url="http://api.kivaws.org/v1/partners.json";
        getKivaPatro(url);


        ListView lv3=(ListView) findViewById(R.id.listViewPatrocinadores);
        lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView) view.findViewById(R.id.textPatroId);
                final String patroId = (String) textView.getText();

                Intent intent = new Intent(mContext, patrocinadores.class);
                intent.putExtra("numeroPatrocinador", patroId);
                startActivity(intent);

            }
        });


    }
    private void getKivaPatro(String url) {
        final Context context=this;
        JsonObjectRequest jor=new JsonObjectRequest(url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray patro=response.getJSONArray("partners");

                    ArrayList<JSONObject> dataSourse=new ArrayList<JSONObject>();
                    for(int i=0;i<patro.length();i++)
                    {
                        if (i!=12)
                        {
                            dataSourse.add(patro.getJSONObject(i));
                        }

                    }
                    AdaptadorPatrocinadores adapter=new AdaptadorPatrocinadores(context,0,dataSourse);
                    ((ListView)findViewById(R.id.listViewPatrocinadores)).setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(mContext).addToRequestQueue(jor);
    }
}

