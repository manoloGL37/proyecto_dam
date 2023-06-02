package com.example.proyectodammanuelgongora.Compra;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Modelos.Producto;
import com.example.proyectodammanuelgongora.R;

import java.util.ArrayList;

public class AdaptadorCarrito extends RecyclerView.Adapter<AdaptadorCarrito.MiViewHolder> implements View.OnClickListener {

    private ArrayList<Producto> listaProductos;
    private View.OnClickListener listener;
    private int idUser;
    private TextView etiTotal;
    private Context context;

    public AdaptadorCarrito(Context context,ArrayList<Producto> lista, int idUser, TextView etiTotal) {
        this.context = context;
        this.listaProductos = lista;
        this.idUser = idUser;
        this.etiTotal = etiTotal;
    }

    @NonNull
    @Override
    public MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_carrito,null,false); //Referencia la vista
        view.setOnClickListener(this);
        return new MiViewHolder(view); // Pasa la vista a la subclase
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewHolder holder, int position) {
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

        TextView nombreProducto;
        TextView precioProducto;
        ImageButton eliminarProducto;
        DataBase conexion = new DataBase();

        public MiViewHolder(@NonNull View itemView) {
            super(itemView);

            conexion.conectar();

            // Relaciones componentes
            nombreProducto = itemView.findViewById(R.id.eti_nombre_prod_carito);
            precioProducto = itemView.findViewById(R.id.eti_precio_prod_carrito);
            eliminarProducto = itemView.findViewById(R.id.btn_eliminar_carrito);

            eliminarProducto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirmar eliminación");
                    builder.setMessage("¿Estás seguro de que deseas eliminar el elemento del carrito?");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            conexion.eliminarUnProdCarrito(idUser, listaProductos.get(getAdapterPosition()).getIdProducto());
                            etiTotal.setText(conexion.totalCarrito(idUser) + " €");
                            listaProductos.remove(getAdapterPosition());
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });

        }

    }

}
