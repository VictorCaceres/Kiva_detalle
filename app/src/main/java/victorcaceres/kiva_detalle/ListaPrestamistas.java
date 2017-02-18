package victorcaceres.kiva_detalle;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

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

public class ListaPrestamistas extends AppCompatActivity {

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_prestamistas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Nuestros Prestamistas");

        mContext=this;
        String url="http://api.kivaws.org/v1/lenders/newest.json";
        getKivaPresta(url);

    }
    private void getKivaPresta(String url) {
        final Context context=this;
        JsonObjectRequest jor=new JsonObjectRequest(
                url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray presta=response.getJSONArray("lenders");

                            ArrayList<JSONObject> dataSourse=new ArrayList<JSONObject>();
                            for(int i=0;i<presta.length();i++)
                            {
                                dataSourse.add(presta.getJSONObject(i));

                            }
                            AdaptadorPrestamistas adapter=new AdaptadorPrestamistas(context,0,dataSourse);
                            ((ListView)findViewById(R.id.listViewPresta)).setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        MySingleton.getInstance(mContext).addToRequestQueue(jor);
    }

}


