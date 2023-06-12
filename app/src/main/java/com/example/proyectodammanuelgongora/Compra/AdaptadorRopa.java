package com.example.proyectodammanuelgongora.Compra;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodammanuelgongora.Aplicacion.InicioActivity;
import com.example.proyectodammanuelgongora.Modelos.Producto;
import com.example.proyectodammanuelgongora.R;
import com.example.proyectodammanuelgongora.Utils.Utiles;

import java.util.ArrayList;

public class AdaptadorRopa extends RecyclerView.Adapter<AdaptadorRopa.MiViewHolder> implements View.OnClickListener {

    private ArrayList<Producto> listaProductos;
    private View.OnClickListener listener;
    private Context context;
    Utiles utiles = new Utiles();
    private int idUser;

    public AdaptadorRopa(Context context, ArrayList<Producto> lista, int idUser) {
        this.context = context;
        this.listaProductos = lista;
        this.idUser = idUser;
    }

    @NonNull
    @Override
    public MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_producto,null,false); //Referencia la vista
        view.setOnClickListener(this);
        return new MiViewHolder(view); // Pasa la vista a la subclase
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewHolder holder, int position) {
        // Rellenar componentes
        ImageView imageView = utiles.blobAImageView(context, listaProductos.get(position).getImagen()); // Pasamos la imagen en bytes y se convierte a ImageView
        if (imageView == null) {
            holder.imagenProducto.setImageResource(R.drawable.sin_imagen);
        } else {
            holder.imagenProducto.setImageDrawable(imageView.getDrawable());
        }

        holder.nombreProducto.setText(listaProductos.get(position).getNombreProd());
        holder.precioProducto.setText(String.valueOf(listaProductos.get(position).getPrecio()) + " €");
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
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

        ImageView imagenProducto;
        TextView nombreProducto;
        TextView precioProducto;
        CardView producto;

        public MiViewHolder(@NonNull View itemView) {
            super(itemView);

            // Relaciones componentes
            imagenProducto = itemView.findViewById(R.id.imagenProducto);
            nombreProducto = itemView.findViewById(R.id.nombreProducto);
            precioProducto = itemView.findViewById(R.id.precioProducto);
            producto = itemView.findViewById(R.id.cardview_producto);

            producto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posicion = getAdapterPosition();
                    int id = listaProductos.get(posicion).getIdProducto();
                    Intent intent = new Intent(context, ProductoActivity.class);
                    ArrayList<Integer> ids = new ArrayList<>();
                    ids.add(id);
                    ids.add(idUser);
                    intent.putIntegerArrayListExtra("ids", ids);
                    context.startActivity(intent);
                }
            });

        }

    }

}
