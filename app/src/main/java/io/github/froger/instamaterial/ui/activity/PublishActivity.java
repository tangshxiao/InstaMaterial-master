package io.github.froger.instamaterial.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ToggleButton;

import java.io.IOException;

import butterknife.BindView;
import io.github.froger.instamaterial.R;
import io.github.froger.instamaterial.Utils;

/**
 * Created by Miroslaw Stanek on 21.02.15.
 */
public class PublishActivity extends BaseActivity {
    public static final String ARG_TAKEN_PHOTO_URI = "arg_taken_photo_uri";

    @BindView(R.id.tbFollowers)
    ToggleButton tbFollowers;
    @BindView(R.id.tbDirect)
    ToggleButton tbDirect;
    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;

    private boolean propagatingToggleState = false;
    private Uri photoUri;
    private int photoSize;

    public static void openWithPhotoUri(Activity openingActivity, Uri photoUri) {
        Intent intent = new Intent(openingActivity, PublishActivity.class);
        intent.putExtra(ARG_TAKEN_PHOTO_URI, photoUri);
        openingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_grey600_24dp);
        photoSize = getResources().getDimensionPixelSize(R.dimen.publish_photo_thumbnail_size);
        photoUri = getIntent().getParcelableExtra(ARG_TAKEN_PHOTO_URI);
//        if (savedInstanceState == null) {
//
//        } else {
//            photoUri = savedInstanceState.getParcelable(ARG_TAKEN_PHOTO_URI);
//        }
        updateStatusBarColor();

        ivPhoto.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                ivPhoto.getViewTreeObserver().removeOnPreDrawListener(this);
                try {
                    loadThumbnailPhoto();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void updateStatusBarColor() {
        if (Utils.isAndroid5()) {
            getWindow().setStatusBarColor(0xff888888);
        }
    }

    private void loadThumbnailPhoto() throws IOException {
        ivPhoto.setImageURI(photoUri);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_publish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_publish) {
            bringMainActivityToTop();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void bringMainActivityToTop() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setAction(MainActivity.ACTION_SHOW_LOADING_ITEM);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_TAKEN_PHOTO_URI, photoUri);
    }
//
//    @OnCheckedChanged(R.id.tbFollowers)
//    public void onFollowersCheckedChange(boolean checked) {
//        if (!propagatingToggleState) {
//            propagatingToggleState = true;
//            tbDirect.setChecked(!checked);
//            propagatingToggleState = false;
//        }
//    }
//
//    @OnCheckedChanged(R.id.tbDirect)
//    public void onDirectCheckedChange(boolean checked) {
//        if (!propagatingToggleState) {
//            propagatingToggleState = true;
//            tbFollowers.setChecked(!checked);
//            propagatingToggleState = false;
//        }
//    }
}
