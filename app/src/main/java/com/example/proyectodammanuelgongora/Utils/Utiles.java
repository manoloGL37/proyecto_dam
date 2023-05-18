package com.example.proyectodammanuelgongora.Utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class Utiles {

    // Conversion de ImageButton a blob recibido de la app
    public byte[] imageButtonABlob(ImageButton imageButton) {
        Bitmap bitmap = ((BitmapDrawable) imageButton.getDrawable()).getBitmap();

        // Comprimir el bitmap en formato JPEG
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        // Obtener el array de bytes del bitmap comprimido
        byte[] blob = outputStream.toByteArray();

        return blob;
    }

    // Conversion de blob a ImageView recibido de la base de datos
    public static ImageView blobAImageView(Context context, byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ImageView imageView = new ImageView(context);
            imageView.setImageBitmap(bitmap);
            return imageView;
        }
        return null;
    }


}
