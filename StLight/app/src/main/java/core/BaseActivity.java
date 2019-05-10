package core;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.stlight.R;

public class BaseActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_READ_CONTACTS = 100;
    IPermissionListener mIPermissionListener;
    public Toolbar toolbar;
    public ImageView toolbar_image ;
    public TextView toolbar_title;
    public ProgressDialog mProgress;
    public Button homeFooterButton;
    public Button devicesFooterBtn;
    public Button scheduleFooterBtn;
    public ImageView toolbar_filter_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_base);
        mProgress=new ProgressDialog(BaseActivity.this);
        mProgress.setMessage(getResources().getString(R.string.dialog_text));
        mProgress.setCancelable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void permissionCheck(int permissionCheck){
        if (permissionCheck == PackageManager.PERMISSION_GRANTED){
            mIPermissionListener =(IPermissionListener)this;
            mIPermissionListener.permissionCheck(true);

        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS,Manifest.permission.SEND_SMS,Manifest.permission.READ_PHONE_STATE},PERMISSION_REQUEST_READ_CONTACTS);
        }
    }

    public void showSnackBar(String message){
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).show();


    }
}
