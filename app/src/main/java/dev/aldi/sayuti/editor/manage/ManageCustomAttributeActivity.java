package dev.aldi.sayuti.editor.manage;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.HashMap;

import dev.aldi.sayuti.editor.injection.AddCustomAttributeActivity;
import mod.hey.studios.util.Helper;

public class ManageCustomAttributeActivity extends AppCompatActivity {

    public Intent i = new Intent();
    public ArrayList<String> list = new ArrayList<>();
    public ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
    public ListView listview;
    public HashMap<String, Object> map = new HashMap<>();
    /**
     * The current project's ID, like 605
     */
    public String str = "";
    public String str2 = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_custom_attribute);
        listview = findViewById(R.id.manage_attr_listview);
        initList();
        initToolbar();
    }

    public void initList() {
        if (getIntent().hasExtra("sc_id") && getIntent().hasExtra("file_name")) {
            str = getIntent().getStringExtra("sc_id");
            str2 = getIntent().getStringExtra("file_name").replaceAll(".xml", "");
        }
        addType("Toolbar");
        addType("AppBarLayout");
        addType("CoordinatorLayout");
        addType("FloatingActionButton");
        addType("DrawerLayout");
        addType("NavigationDrawer");
        listview.setAdapter(new CustomAdapter(listMap));
        ((BaseAdapter) listview.getAdapter()).notifyDataSetChanged();
    }

    public void addType(String type) {
        map = new HashMap<>();
        map.put("type", type);
        listMap.add(map);
    }

    public void initToolbar() {
        ((TextView) findViewById(R.id.tx_toolbar_title)).setText("AppCompat Injection Manager");
        ImageView imageView = findViewById(R.id.ig_toolbar_back);
        Helper.applyRippleToToolbarView(imageView);
        imageView.setOnClickListener(Helper.getBackPressedClickListener(this));
    }

    public void makeup(View view) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(new float[]{10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f});
        gradientDrawable.setColor(Color.parseColor("#ffffff"));
        RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor("#20008DCD")}), gradientDrawable, null);
        view.setElevation(5.0f);
        view.setBackground(rippleDrawable);
        view.setClickable(true);
        view.setFocusable(true);
    }

    private class CustomAdapter extends BaseAdapter {

        private final ArrayList<HashMap<String, Object>> _data;

        public CustomAdapter(ArrayList<HashMap<String, Object>> arrayList) {
            _data = arrayList;
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int position) {
            return _data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.custom_view_attribute, null);
            }
            LinearLayout linearLayout = convertView.findViewById(R.id.cus_attr_layout);
            makeup(linearLayout);
            ((ImageView) convertView.findViewById(R.id.cus_attr_btn)).setImageResource(R.drawable.ic_property_inject);
            ((TextView) convertView.findViewById(R.id.cus_attr_text)).setText(_data.get(position).get("type").toString());
            linearLayout.setOnClickListener(v -> {
                i.setClass(getApplicationContext(), AddCustomAttributeActivity.class);
                i.putExtra("sc_id", str);
                i.putExtra("file_name", str2);
                i.putExtra("widget_type", _data.get(position).get("type").toString().toLowerCase());
                startActivity(i);
            });
            return convertView;
        }
    }
}