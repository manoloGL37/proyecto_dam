package com.example.proyectodammanuelgongora.Utils;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.example.proyectodammanuelgongora.Social.PublicacionActivity;

import java.io.ByteArrayOutputStream;

public class Utiles {

    private Uri uri = null;

    String[] required_permissions = new String[]{
            Manifest.permission.READ_MEDIA_IMAGES
    };

    boolean acceso_galeria_permitido = false;

    String TAG = "Permission";

    public void abrirGaleria(ActivityResultLauncher<Intent> galeriaLauncher, ActivityResultLauncher<String> request_permission_launcher_storage_images,Context context) {

        if (acceso_galeria_permitido) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            galeriaLauncher.launch(intent);
        } else {
                if (ContextCompat.checkSelfPermission(context, required_permissions[0]) == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, required_permissions[0] + " Granted");
                    acceso_galeria_permitido = true;
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    galeriaLauncher.launch(intent);
                } else {
                    request_permission_launcher_storage_images.launch(required_permissions[0]);
                }
        }

    }


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

    public static ImageButton blobAImageButton(Context context, byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ImageButton imageButton = new ImageButton(context);
            imageButton.setImageBitmap(bitmap);
            return imageButton;
        }
        return null;
    }


}
