package app.ij.mlwithtensorflowlite;

import static java.io.File.createTempFile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.ij.mlwithtensorflowlite.ml.Model;

public class Scanner extends AppCompatActivity{
    ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture;
    PreviewView previewView;
    Button scan;
    ImageView gallery;
    private ImageCapture imageCapture;
    File imageFile;
    int imageSize = 256;
    ProgressBar pbar;
    int flashMode=ImageCapture.FLASH_MODE_OFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        scan=findViewById(R.id.scan);
        pbar=findViewById(R.id.pbar);
        previewView = findViewById(R.id.previewView);
//        flash=findViewById(R.id.flash);

        gallery=findViewById(R.id.gallery);
        cameraProviderListenableFuture=ProcessCameraProvider.getInstance(this);
        cameraProviderListenableFuture.addListener(()->{
            try {
                ProcessCameraProvider cameraProvider=cameraProviderListenableFuture.get();
                startCameraX(cameraProvider);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },getExecutor());

//        flash.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(flashMode==ImageCapture.FLASH_MODE_OFF){
//                    flash.setBackground(ContextCompat.getDrawable(Scanner.this, R.drawable.ic_baseline_flash_off_24));
//                    flashMode=ImageCapture.FLASH_MODE_ON;
//                }else{
//                    flash.setBackground(ContextCompat.getDrawable(Scanner.this, R.drawable.ic_baseline_flash_on_24));
//                    flashMode=ImageCapture.FLASH_MODE_OFF;
//                }
//            }
//        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbar.setVisibility(View.VISIBLE);
                scanPhoto();

            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(cameraIntent, 1);
                pbar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void scanPhoto() {

        try {
             imageFile= createTempFile("anImage", ".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        File photo=new File("photo");
        imageCapture.takePicture(new ImageCapture.OutputFileOptions.Builder(imageFile).build(), getExecutor()
                , new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Bitmap picBitmap = BitmapFactory.decodeFile(String.valueOf(imageFile));
//                        Toast.makeText(Scanner.this, picBitmap.getWidth()+"Captured"+picBitmap.getHeight(), Toast.LENGTH_SHORT).show();
                        int dimension = Math.min(picBitmap.getWidth(), picBitmap.getHeight());
                        picBitmap = ThumbnailUtils.extractThumbnail(picBitmap, dimension, dimension);
                        picBitmap = Bitmap.createScaledBitmap(picBitmap, imageSize, imageSize, false);
                        classifyImage(picBitmap);
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(Scanner.this, "Not Captured!"+exception.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }

    private void startCameraX(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();

        CameraSelector cameraSelector=new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        Preview preview=new Preview.Builder().build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        imageCapture=new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
//                .setFlashMode(flashMode)
                .build();
        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageCapture);
    }

    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    public void classifyImage(Bitmap image){
        try {
            Model model = Model.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 256, 256, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for(int i = 0; i < imageSize; i ++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 1));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {"Buddha Statue","Shiva Natraj","Sitar","Taj Mahal"};

            Toast.makeText(this, ""+classes[maxPos], Toast.LENGTH_SHORT).show();
            pbar.setVisibility(View.GONE);

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==3){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();

        }
        else{

            Uri dat = data.getData();
            Bitmap image = null;
            try {
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
            } catch (IOException e) {
                e.printStackTrace();
            }
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            classifyImage(image);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}