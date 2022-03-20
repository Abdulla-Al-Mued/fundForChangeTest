package com.example.fundforchangetest.activities.pdfGenerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fundforchangetest.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class downloadDashboardData extends AppCompatActivity {


    TextView tAmount, tDonor, tEvent, pendingEvents, user, finishedEvent, header;
    Button generate_btn;
    String tA, tD, tE, pE, u, fEvent, hd;
    int pageWidth = 1200;

    // declaring width and height
    // for our PDF file.
    int pageHeight = 1120;
    int pagewidth = 792;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_dashboard_data);

        tAmount = findViewById(R.id.total_donation);
        tDonor = findViewById(R.id.total_donor);
        tEvent = findViewById(R.id.total_event);
        pendingEvents = findViewById(R.id.pending_events);
        user = findViewById(R.id.total_users);
        finishedEvent = findViewById(R.id.finished_events);
        header = findViewById(R.id.header);
        generate_btn = findViewById(R.id.generate_btn);

        tA = getIntent().getStringExtra("TA");
        tD = getIntent().getStringExtra("TD");
        tE = getIntent().getStringExtra("TE");
        pE = getIntent().getStringExtra("PE");
        u = getIntent().getStringExtra("U");
        fEvent = getIntent().getStringExtra("FE");
        hd = getIntent().getStringExtra("H");


        tAmount.setText(tA);
        tDonor.setText(tD);
        tEvent.setText(tE);
        pendingEvents.setText(pE);
        finishedEvent.setText(fEvent);
        user.setText(u);
        header.setText(hd);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);




        generatePDF();


    }

    private void generatePDF() {

        generate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PdfDocument myPdfDocument = new PdfDocument();
                Paint myPaint = new Paint();

                //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(250, 400, 1).create();
                PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
                Canvas canvas = myPage1.getCanvas();


                canvas.drawText("testing....",40,50, myPaint);
                myPdfDocument.finishPage(myPage1);





                File file = new File(Environment.getExternalStorageDirectory(), "/Hello.pdf");


                try {
                    //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                    myPdfDocument.writeTo(new FileOutputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                myPdfDocument.close();

            }
        });




    }

    /*private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }

    }*/
    
    
}