package com.example.cameratest;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {

	Uri imageFileUri;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ImageButton button = (ImageButton) findViewById(R.id.TakeAPhoto);
		OnClickListener listener = new OnClickListener() {
			public void onClick(View v) {
				takeAPhoto();
			}
		};
		button.setOnClickListener(listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	// diffrentiates between resp from other activitis
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	public void takeAPhoto() {
		// TODO: Create an intent with the action
		// MediaStore.ACTION_IMAGE_CAPTURE
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// pass the location of where to save image
		
		
		// ComponentName cn = new ComponentName("es.softwareprocess.bogopicgen",
		// "es.softwareprocess.bogopicgen.BogoPicGenActivity");
		// ComponentName cn = new ComponentName("com.android.camera",
		// "com.android.camera.Camera");
		// intent.setComponent(cn);

		// Create a folder to store pictures
		String folder = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/tmp";
		File folderF = new File(folder);
		if (!folderF.exists()) {
			folderF.mkdir();
		}

		// Create an URI for the picture file
		String imageFilePath = folder + "/"
				+ String.valueOf(System.currentTimeMillis()) + ".jpg";
		File imageFile = new File(imageFilePath);
		imageFileUri = Uri.fromFile(imageFile);

		// TODO: Put in the intent in the tag MediaStore.EXTRA_OUTPUT the URI
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
		
		// TODO: Start the activity (expecting a result), with the code
		// CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE
		// the data is returned as an intent
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		
	}

	// This is called wen the activity you called returns.
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO: Handle the results from CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE
		
		// TODO: Handle the cases for RESULT_OK, RESULT_CANCELLED, and others
		
		// When the result is OK, set text "Photo OK!" in the status
		//		and set the image in the Button with:
		//		button.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath()));
		// When the result is CANCELLED, set text "Photo canceled" in the status
		// Otherwise, set text "Not sure what happened!" with the resultCode
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			
			TextView tv = (TextView) findViewById(R.id.status);
			
			if (resultCode == RESULT_OK){
				if (data.hasExtra("es.softwareprocess.bogopicgen.RETURN_MESSAGE")){
					String message = data.getStringExtra("es.softwareprocess.bogopicgen.RETURN_MESSAGE");
					tv.setText("Photo OK! " + message);
				} else {
					tv.setText("Photo OK!");
				}
				
				
				
				// set the text to the status
				
				
				
				ImageButton button = (ImageButton)findViewById(R.id.TakeAPhoto);
				button.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath()));
			} else if (resultCode == RESULT_CANCELED) {
				tv.setText("Photo canceled");
			} else {
				tv.setText("Not sure what happened!");
			}
		}
	}
}
