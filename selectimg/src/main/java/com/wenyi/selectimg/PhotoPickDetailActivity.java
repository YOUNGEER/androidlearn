package com.wenyi.selectimg;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.wenyi.photoview.PhotoView;
import com.wenyi.selectimg.utils.Util;

import java.util.ArrayList;


public class PhotoPickDetailActivity extends AppCompatActivity {

    public static final String PICK_DATA = "PICK_DATA";
    public static final String ALL_DATA = "ALL_DATA";
    public static final String FOLDER_NAME = "FOLDER_NAME";
    public static final String PHOTO_BEGIN = "PHOTO_BEGIN";
    public static final String EXTRA_MAX = "EXTRA_MAX";

    private ArrayList<ImageInfo> mPickPhotos;
    private ArrayList<ImageInfo> mAllPhotos;

    private ViewPager mViewPager;
    private CheckBox mCheckBox;

    private int mMaxPick = 3;
    private MenuItem mMenuSend;
    private final String actionbarTitle = "%d/%d";
    Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_pick_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        mPickPhotos = (ArrayList<ImageInfo>) extras.getSerializable(PICK_DATA);
        mAllPhotos = (ArrayList<ImageInfo>) extras.getSerializable(ALL_DATA);

        int mBegin = extras.getInt(PHOTO_BEGIN, 0);
        mMaxPick = extras.getInt(EXTRA_MAX, 5);
        if (mAllPhotos == null) {
            String folderName = extras.getString(FOLDER_NAME, "");
            String where = folderName;
            if (!folderName.isEmpty()) {
                where = String.format("%s='%s'",
                        MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                        folderName);
            }
            mCursor = getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{
                            MediaStore.Images.ImageColumns._ID,
                            MediaStore.Images.ImageColumns.DATA},
                    where,
                    null,
                    MediaStore.MediaColumns.DATE_ADDED + " DESC");
        }

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(new ImagePagerAdapter());
        mViewPager.setCurrentItem(mBegin);

        mCheckBox = (CheckBox) findViewById(R.id.checkbox);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateDisplay(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mViewPager.getCurrentItem();

                String uri = getImagePath(pos);

                if (((CheckBox) v).isChecked()) {
                    if (mPickPhotos.size() >= mMaxPick) {
                        ((CheckBox) v).setChecked(false);
                        String s = String.format("最多只能选择%d张", mMaxPick);
                        Toast.makeText(PhotoPickDetailActivity.this, s, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    addPicked(uri);
                } else {
                    removePicked(uri);
                }

                updateDataPickCount();
            }
        });

        updateDisplay(mBegin);
    }

    @Override
    protected void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
        }

        super.onDestroy();
    }

    private void updateDisplay(int pos) {
        String uri = getImagePath(pos);
        mCheckBox.setChecked(isPicked(uri));
        getSupportActionBar().setTitle(String.format(actionbarTitle, pos + 1, getImageCount()));
    }

    private boolean isPicked(String path) {
        for (ImageInfo item : mPickPhotos) {
            if (item.path.equals(path)) {
                return true;
            }
        }

        return false;
    }

    private void addPicked(String path) {
        if (!isPicked(path)) {
            mPickPhotos.add(new ImageInfo(path));
        }
    }

    private void removePicked(String path) {
        for (int i = 0; i < mPickPhotos.size(); ++i) {
            if (mPickPhotos.get(i).path.equals(path)) {
                mPickPhotos.remove(i);
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        selectAndSend(false);
    }

    private void selectAndSend(boolean send) {
        Intent intent = new Intent();
        intent.putExtra("data", mPickPhotos);
        intent.putExtra("send", send);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo_detail, menu);
        mMenuSend = menu.getItem(0);
        updateDataPickCount();

        return true;
    }

    private void updateDataPickCount() {
        String send = String.format("确定(%d/%d)", mPickPhotos.size(), mMaxPick);
        mMenuSend.setTitle(send);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            selectAndSend(false);
            return true;
        } else if (id == R.id.action_send) {
            selectAndSend(true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

     class ImagePagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return getImageCount();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());

            Util.displayImage(container.getContext(),getImagePath(position),photoView);
            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return photoView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            Util.clear(view);
            container.removeView(view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }


    String getImagePath(int pos) {
        if (mAllPhotos != null) {
            return mAllPhotos.get(pos).path;
        } else {
            String path = "";
            if (mCursor.moveToPosition(pos)) {
                path = ImageInfo.pathAddPreFix(mCursor.getString(1));
            }
            return path;
        }
    }

    int getImageCount() {
        if (mAllPhotos != null) {
            return mAllPhotos.size();
        } else if (mCursor != null) {
            return mCursor.getCount();
        } else {
            return 0;
        }
    }

}
