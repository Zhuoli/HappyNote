package com.example.zhuoli.notebook;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhuoli on 1/18/15.
 */
public class AddContent extends Activity implements View.OnClickListener {

    private String val;
    private Button savebtn, deletebtn;
    private EditText ettext;
    private ImageView c_img;
    private VideoView  v_video;
    private File photoFile=null;

    private NotesDB notesDB;
    private SQLiteDatabase dbWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcontent);
        val=getIntent().getStringExtra("flag");
        savebtn= (Button) findViewById(R.id.save);
        deletebtn=(Button)findViewById(R.id.delete);
        ettext=(EditText)findViewById(R.id.ettext);
        c_img=(ImageView)findViewById(R.id.c_img);
        v_video=(VideoView)findViewById(R.id.c_video);
        savebtn.setOnClickListener(this);
        deletebtn.setOnClickListener(this);
        notesDB=new NotesDB(this);
        dbWrite=notesDB.getWritableDatabase();
        initView();

    }

    public void initView(){
        //text
        if(val.equals("1")){
            c_img.setVisibility(View.GONE);
            v_video.setVisibility(View.GONE);
        }
        //image
        if(val.equals("2")){
            c_img.setVisibility(View.VISIBLE);
            v_video.setVisibility(View.GONE);
            Intent iimg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            photoFile=new File(
                    Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+getTime()+".jpg");
            iimg.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            startActivityForResult(iimg,1);

        }
        //video
        if(val.equals("3")){
            c_img.setVisibility(View.GONE);
            v_video.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                addDB();
                finish();
                break;
            case R.id.delete:
                finish();
                break;
        }
    }

    public void addDB(){
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.CONTENT,ettext.getText().toString());
        cv.put(NotesDB.TIME,getTime());
        cv.put(notesDB.PATH, photoFile+"");
        dbWrite.insert(NotesDB.TABLE_NAME,null,cv);
    }

    private String getTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date();
        return format.format(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            c_img.setImageBitmap(bitmap);
        }
    }
}
