package com.example.pcos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.webkit.MimeTypeMap;

public class MedicalReports2 extends AppCompatActivity {
    private String username;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical__reports2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button reportsButton = findViewById(R.id.button7);
        reportsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MedicalReports2.this, MedicalReports.class);
            intent.putExtra("username", username);  // Pass the username to MedicalReports activity
            startActivity(intent);
        });

        Button buttonOpenGallery = findViewById(R.id.button8);
        buttonOpenGallery.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
        });

        // Retrieve the username from the Intent Extras
        username = getIntent().getStringExtra("username");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                uploadImageToServer(selectedImageUri);
            } catch (IOException e) {
                Toast.makeText(this, "Error uploading image: " + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("UploadError", "Problem uploading image", e);
            }
        }
    }
    private void uploadImageToServer(Uri fileUri) throws IOException {
        File file = FileUtils.getFile(this, fileUri); // Ensure FileUtils handles content Uris correctly

        // Using a generic MediaType that doesn't require knowing the exact file type
        MediaType mediaType = MediaType.parse("application/octet-stream");

        RequestBody fileBody = RequestBody.create(mediaType, file);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(), fileBody)
                .addFormDataPart("name", username)
                .build();

        Request request = new Request.Builder()
                .url(IpV4Connection.getUrl("medicalrecords.php"))
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(MedicalReports2.this, "Failed to Upload Image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(() -> {
                    try {
                        if (response.isSuccessful()) {
                            Toast.makeText(MedicalReports2.this, "Image Uploaded Successfully!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MedicalReports2.this, "Upload failed: " + response.body().string(), Toast.LENGTH_LONG).show();
                        }
                    } catch (IOException e) {
                        Log.e("ResponseError", "Error reading response", e);
                    }
                });
            }
        });
    }


}
