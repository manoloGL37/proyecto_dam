package com.example.proyectodammanuelgongora.Panel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Modelos.Usuario;
import com.example.proyectodammanuelgongora.R;

import java.util.ArrayList;

public class AdaptadorUsuario extends RecyclerView.Adapter<AdaptadorUsuario.MiViewHolder> implements View.OnClickListener {

    private ArrayList<Usuario> listaUsuarios;
    private View.OnClickListener listener;

    public AdaptadorUsuario(ArrayList<Usuario> lista) {
        this.listaUsuarios = lista;
    }

    @NonNull
    @Override
    public MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_usuarios,null,false); //Referencia la vista
        view.setOnClickListener(this);
        return new MiViewHolder(view); // Pasa la vista a la subclase
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewHolder holder, int position) {
        holder.nombreUsuario.setText(listaUsuarios.get(position).getNombreUsuario());
        holder.rolUsuario.setText(listaUsuarios.get(position).getRolName());
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
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

        TextView nombreUsuario, rolUsuario;
        Button btnEliminar;
        DataBase conexion = new DataBase();

        public MiViewHolder(@NonNull View itemView) {
            super(itemView);

            conexion.conectar();

            nombreUsuario = itemView.findViewById(R.id.eti_nombre_usuario);
            rolUsuario = itemView.findViewById(R.id.eti_rol_usuario);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar_user);

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    conexion.eliminarUsuario(listaUsuarios.get(getAdapterPosition()).getIdUser());
                    listaUsuarios.remove(listaUsuarios.get(getAdapterPosition()));
                    notifyDataSetChanged();
                }
            });

        }


    }

}
