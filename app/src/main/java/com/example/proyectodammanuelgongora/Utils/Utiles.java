package com.example.proyectodammanuelgongora.Utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.SQLException;

public class Utiles {



    public byte[] convertirImageButtonABlob(ImageButton imageButton) {
        Bitmap bitmap = ((BitmapDrawable) imageButton.getDrawable()).getBitmap();

        // Comprimir el bitmap en formato JPEG
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        // Obtener el arreglo de bytes del bitmap comprimido
        byte[] blob = outputStream.toByteArray();

        return blob;
    }

    public static ImageView obtenerImageViewDesdeBytes(Context context, byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ImageView imageView = new ImageView(context);
            imageView.setImageBitmap(bitmap);
            return imageView;
        }
        return null;
    }


}
