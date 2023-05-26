package com.example.proyectodammanuelgongora.MiCuenta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Modelos.Publicacion;
import com.example.proyectodammanuelgongora.R;
import com.example.proyectodammanuelgongora.Utils.Utiles;

import java.util.ArrayList;

public class AdaptadorPublicaciones extends RecyclerView.Adapter<AdaptadorPublicaciones.MiViewHolder> implements View.OnClickListener {

    Utiles utiles = new Utiles();
    private ArrayList<Publicacion> listaPublicaciones;
    private Context context;
    private View.OnClickListener listener;
    private int idUsuario;

    public AdaptadorPublicaciones(Context context, ArrayList<Publicacion> lista, int idUsuario) {
        this.context = context;
        this.listaPublicaciones = lista;
        this.idUsuario = idUsuario;
    }

    @NonNull
    @Override
    public MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_publicaciones, parent, false);
        view.setOnClickListener(this);
        return new MiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewHolder holder, int position) {
        holder.rellenarEtis(listaPublicaciones.get(position));
    }

    @Override
    public int getItemCount() {
        return listaPublicaciones.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }


    public class MiViewHolder extends RecyclerView.ViewHolder { //Subclase

        ImageView imagenPublicacion;
        TextView descripcion, nombreUsuario, likes;
        Button btnEliminar;
        LottieAnimationView btnLikes;
        DataBase conexion = new DataBase();

        boolean like = false;

        public MiViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreUsuario = itemView.findViewById(R.id.username_social);
            imagenPublicacion = itemView.findViewById(R.id.publicacion_user_social);
            descripcion = itemView.findViewById(R.id.descrpcion_social);
            likes = itemView.findViewById(R.id.num_likes_social);
            btnLikes = itemView.findViewById(R.id.btn_like_social);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar_publicacion);

            // Pulsador likes
            btnLikes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Publicacion publicacion = listaPublicaciones.get(position);
                        int likesActuales = publicacion.getLikes();
                        likesActuales++;
                        listaPublicaciones.get(position).setLikes(likesActuales);
                        if (conexion.comprobarLike(idUsuario, publicacion.getIdPublicacion())) {
                            conexion.incrementarLikes(likesActuales, publicacion.getIdPublicacion(), idUsuario);
                            notifyDataSetChanged();
                            like = animacionLike(btnLikes, R.raw.animacion_like, like);
                        } else {
                            Toast.makeText(context, "No puede dar más de un like a una publicación", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    conexion.eliminarUnaPublicacion(idUsuario, listaPublicaciones.get(getAdapterPosition()).getIdPublicacion());
                    listaPublicaciones.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }

        public void rellenarEtis(Publicacion publicacion) {
            nombreUsuario.setText(publicacion.getNombre_usuario());
            ImageView imageView = utiles.blobAImageView(context, publicacion.getImagen());
            imagenPublicacion.setImageDrawable(imageView.getDrawable());
            descripcion.setText(publicacion.getDescripcion());
            likes.setText(String.valueOf(publicacion.getLikes()));

            conexion.conectar();
            if (!conexion.comprobarLike(idUsuario, publicacion.getIdPublicacion())) {
                like = true;
                btnLikes.setAnimation(R.raw.animacion_like);
                btnLikes.playAnimation();
            }
        }

        private Boolean animacionLike(LottieAnimationView imageView, int animacion, boolean like) {
            if (!like) {
                imageView.setAnimation(animacion);
                imageView.playAnimation();
            } else {
                imageView.setImageResource(R.drawable.like);
            }
            return !like;
        }
    }

}
