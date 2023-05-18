package com.example.proyectodammanuelgongora.Compra;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodammanuelgongora.Modelos.Producto;
import com.example.proyectodammanuelgongora.R;

import java.util.ArrayList;

public class AdaptadorRopa extends RecyclerView.Adapter<AdaptadorRopa.MiViewHolder> implements View.OnClickListener {

    private ArrayList<Producto> listaProductos;
    private View.OnClickListener listener;

    public AdaptadorRopa(ArrayList<Producto> lista) {
        this.listaProductos = lista;
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
        holder.imagenProducto.setImageResource(R.drawable.camiseta);
        holder.nombreProducto.setText(listaProductos.get(position).getNombreProd());
        holder.precioProducto.setText(String.valueOf(listaProductos.get(position).getPrecio()));

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

        ImageButton imagenProducto;
        TextView nombreProducto;
        TextView precioProducto;

        public MiViewHolder(@NonNull View itemView) {
            super(itemView);

            // Relaciones componentes
            imagenProducto = itemView.findViewById(R.id.imagenProducto);
            nombreProducto = itemView.findViewById(R.id.nombreProducto);
            precioProducto = itemView.findViewById(R.id.precioProducto);

        }

    }

}
