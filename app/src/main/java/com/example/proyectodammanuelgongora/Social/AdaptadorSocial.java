package com.example.proyectodammanuelgongora.Social;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodammanuelgongora.Modelos.Publicacion;
import com.example.proyectodammanuelgongora.R;
import com.example.proyectodammanuelgongora.Utils.Utiles;

import java.util.ArrayList;

public class AdaptadorSocial extends RecyclerView.Adapter<AdaptadorSocial.MiViewHolder> implements View.OnClickListener {

    Utiles utiles = new Utiles();
    private ArrayList<Publicacion> listaPublicaciones;
    private Context context;
    private View.OnClickListener listener;

    public AdaptadorSocial(Context context, ArrayList<Publicacion> lista) {
        this.context = context;
        this.listaPublicaciones = lista;
    }

    @NonNull
    @Override
    public MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_social,null,false);
        view.setOnClickListener(this);
        return new MiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewHolder holder, int position) {
        holder.nombreUsuario.setText(listaPublicaciones.get(position).getNombre_usuario());
        ImageView imageView = utiles.obtenerImageViewDesdeBytes(context, listaPublicaciones.get(position).getImagen()); // Pasamos la imagen en bytes y se convierte a ImageView
        holder.imagenPublicacion.setImageDrawable(imageView.getDrawable());
        holder.descripcion.setText(listaPublicaciones.get(position).getDescripcion());
        holder.likes.setText(String.valueOf(listaPublicaciones.get(position).getLikes()));
    }

    @Override
    public int getItemCount() {
        return listaPublicaciones.size();
    }

    public void  setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null) {
            listener.onClick(v);
        }
    }


    public class MiViewHolder extends RecyclerView.ViewHolder { //Subclase

        ImageView imagenPublicacion;
        TextView descripcion, nombreUsuario, likes;

        public MiViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreUsuario = itemView.findViewById(R.id.username_social);
            imagenPublicacion = itemView.findViewById(R.id.publicacion_user_social);
            descripcion = itemView.findViewById(R.id.descrpcion_social);
            likes = itemView.findViewById(R.id.num_likes_social);

        }


    }

}
