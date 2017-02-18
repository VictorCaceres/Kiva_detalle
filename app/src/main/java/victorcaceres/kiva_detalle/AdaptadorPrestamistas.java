package victorcaceres.kiva_detalle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Victor Cáceres on 16/2/2017.
 */

public class AdaptadorPrestamistas extends ArrayAdapter<JSONObject> {

    public AdaptadorPrestamistas(Context context, int resourse, List<JSONObject> items){
        super(context,resourse,items);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View celda = convertView;
        if (celda==null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            celda= layoutInflater.inflate(R.layout.celda_complejapresta,null);
        }

        TextView nombre = (TextView) celda.findViewById(R.id.textViewNombrePresta);
        TextView id=(TextView) celda.findViewById(R.id.textViewPresta);
        NetworkImageView niv= (NetworkImageView)celda.findViewById(R.id.networkImageViewFotoPresta);

        JSONObject elemento=this.getItem(position);


        try {

            JSONObject imagen=elemento.getJSONObject("image");
            String idImagen=imagen.getString("id");
            nombre.setText(elemento.getString("name"));
            id.setText("Id: "+elemento.getString("lender_id"));

            niv.setImageUrl("https://www.kiva.org/img/512/" + Integer.parseInt(idImagen) + ".jpg", MySingleton.getInstance(MainActivity.mContext).getImageLoader());



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return celda;
    }
}
