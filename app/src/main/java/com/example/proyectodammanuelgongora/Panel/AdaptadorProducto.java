package com.example.proyectodammanuelgongora.Panel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodammanuelgongora.Modelos.Producto;
import com.example.proyectodammanuelgongora.R;

import java.util.ArrayList;

public class AdaptadorProducto extends RecyclerView.Adapter<AdaptadorProducto.MiViewHolder> implements View.OnClickListener {

    private ArrayList<Producto> listaProductos;
    private View.OnClickListener listener;
    private Context context;
    private int idUser;

    public AdaptadorProducto(Context context, ArrayList<Producto> lista, int idUser) {
        this.listaProductos = lista;
        this.context = context;
        this.idUser = idUser;
    }

    @NonNull
    @Override
    public MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_panel_producto,null,false); //Referencia la vista
        view.setOnClickListener(this);
        return new MiViewHolder(view); // Pasa la vista a la subclase
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewHolder holder, int position) {
        holder.nombreProducto.setText(listaProductos.get(position).getNombreProd());
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

        TextView nombreProducto;
        Button btnEditar;

        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreProducto = itemView.findViewById(R.id.eti_nombre_prod_panel);
            btnEditar = itemView.findViewById(R.id.btn_editar_prod);

            // Listeners de objectos concretos
            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditarEliminarActivity.class);
                    int id = listaProductos.get(getAdapterPosition()).getIdProducto();
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
