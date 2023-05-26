package com.example.proyectodammanuelgongora.MiCuenta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodammanuelgongora.Modelos.Pedido;
import com.example.proyectodammanuelgongora.R;

import java.util.ArrayList;

public class AdaptadorMisPedidos extends RecyclerView.Adapter<AdaptadorMisPedidos.MiViewHolder> implements View.OnClickListener{

    private ArrayList<Pedido> listaPedidos;
    private View.OnClickListener listener;

    public AdaptadorMisPedidos(ArrayList<Pedido> lista) {
        this.listaPedidos = lista;
    }

    @NonNull
    @Override
    public MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pedido,null,false); //Referencia la vista
        view.setOnClickListener(this);
        return new MiViewHolder(view); // Pasa la vista a la subclase
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewHolder holder, int position) {
        Pedido pedido = listaPedidos.get(position);

        holder.fechaPedido.setText(pedido.getFechaPedido().toString());
        holder.totalPedido.setText("Total: " + String.valueOf(pedido.getTotalPedido()));
        holder.numPedido.setText("Pedido: " + String.valueOf(pedido.getId()));

    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
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

        TextView fechaPedido, totalPedido, numPedido;

        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            fechaPedido = itemView.findViewById(R.id.eti_fecha_pedido);
            totalPedido = itemView.findViewById(R.id.eti_total_pedido);
            numPedido = itemView.findViewById(R.id.eti_num_pedido);

        }


    }

}
