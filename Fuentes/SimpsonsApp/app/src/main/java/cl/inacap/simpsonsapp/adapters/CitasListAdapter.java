package cl.inacap.simpsonsapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import cl.inacap.simpsonsapp.dto.Cita;
import cl.inacap.simpsonsapp.R;

public class CitasListAdapter extends ArrayAdapter<Cita> {

    private List<Cita> frases;
    private Activity contexto;

    public CitasListAdapter(@NonNull Activity context, int resource, @NonNull List<Cita> objects) {
        super(context, resource, objects);
        this.frases = objects;
        this.contexto = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = this.contexto.getLayoutInflater();
        View fila = inflater.inflate(R.layout.citas_list,null, true);
        TextView nombreTxt = fila.findViewById(R.id.tv_nombre);
        TextView citaTxt = fila.findViewById(R.id.tv_cita);
        ImageView imagen = fila.findViewById(R.id.iv_personaje);
        nombreTxt.setText(this.frases.get(position).getCharacter());
        citaTxt.setText(this.frases.get(position).getQuote());
        Picasso.get().load(this.frases.get(position).getImage())
                .resize(500,500)
                .into(imagen);

        return fila;

    }
}
