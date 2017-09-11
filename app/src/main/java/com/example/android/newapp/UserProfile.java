package com.example.android.newapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {
    TextView name;
    ImageView profilePicture;
    private static int RESULT_LOAD_IMAGE = 1;
    private Bitmap bp;
    private String sname;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);




        name = (TextView) findViewById(R.id.UserName);
        profilePicture = (ImageView) findViewById(R.id.pic);
        //  contact = (TextView) findViewById(R.id.Contact);
        Intent intent = getIntent();

         sname = intent.getStringExtra("Name");
        // String sgender = intent.getStringExtra("Gender");
        //  String smobile = intent.getStringExtra("mobileno");
        name.setText(sname);
        final StudentDbHelper dbHelper2 = new StudentDbHelper(this);
        final DbBitmapUtility dbBitmapUtility2 = new DbBitmapUtility();


        SQLiteDatabase dbs = dbHelper2.getReadableDatabase();
        String Query = "SELECT * FROM user WHERE sname='" + sname+"'" ;


        Cursor c = dbs.rawQuery(Query, null);
        c.moveToLast();
        if(c.getBlob(4)!=null) {

            profilePicture.setImageBitmap(dbBitmapUtility2.getImage(c.getBlob(4)));
            c.close();
        }











    }
    // contact.setText(smobile);


    //The Gallery which displays the user's photos is a separate application over which we don't have control, in the sense
    // that there isn't an API that will allow us to manipulate the Gallery from inside our app.
    // However, Android has a mechanism that allows us to get a result from another Activity, even if that activity is not ours.
    // This is accomplished by calling startActivityForResult() (instead of startActivity()),
    // and overriding onActivityResult() to retrieve the result from the previous started Activity.

    //      startActivityForResult() takes two parameters, the Intent to start and a request code.


    public void buttonClicked(View v) {
        int id = v.getId();

        switch (id) {

            case R.id.pic:

                Intent i = new Intent(
                        Intent.ACTION_GET_CONTENT,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // this(ACTION_PICK) IS for SDCARD only .for all location replace this with  ACTION_GET_CONTENT.

                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
            case R.id.save:
                final StudentDbHelper dbHelper = new StudentDbHelper(this);
                final DbBitmapUtility dbBitmapUtility = new DbBitmapUtility();
                SQLiteDatabase sqldb = dbHelper.getReadableDatabase();


                String Query = "update user set simage = null where sname='" +sname +"'" ;

                 sqldb.rawQuery(Query, null);



             dbHelper.updateContact(dbBitmapUtility.getBytes(bp),sname);




                break;
           // case R.id.display:
              // final StudentDbHelper dbHelper2 = new StudentDbHelper(this);
              //  final DbBitmapUtility dbBitmapUtility2 = new DbBitmapUtility();


              // SQLiteDatabase dbs = dbHelper2.getReadableDatabase();

              //  Cursor c = dbs.rawQuery("SELECT * FROM user WHERE sname='" + sname+"'", null);
              //  c.moveToLast();

              //  profilePicture.setImageBitmap(dbBitmapUtility2.getImage(c.getBlob(4)));
              //  c.close();
              //  break;


        }
    }




//(ACTION_GET_CONTENT,) is not working for me, I get a null from cursor.getString(columnIndex);

//Careful with this method: the filename will be 'null' when the user selects a photo from a picasa album or from the Google+ Photos app. – Ciske Boekelo Mar 12 '14 at 11:54
//(ACTION_GET_CONTENT,) will not work for all

    //The "request code" (RESULT_LOAD_IMAGE) is an integer number that identifies your request
    // (doesn't have to be "1", you can use any integer number).
    // When receiving the result in the onActivityResult() callback,
    // the same request code is provided so that your app can properly identify the result and determine how to handle it.


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri choosenImage = data.getData();

            if (choosenImage != null) {

                bp = decodeUri(choosenImage, 400);
                profilePicture.setImageBitmap(bp);
            }


        }


    }


    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

