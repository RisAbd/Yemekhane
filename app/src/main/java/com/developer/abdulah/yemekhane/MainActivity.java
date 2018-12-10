package com.developer.abdulah.yemekhane;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.abdulah.yemekhane.yemekhane.DateMenu;
import com.developer.abdulah.yemekhane.yemekhane.Menu;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "kek";

    private ListView menuListView;
    private MenuListAdapter menuAdapter;

    private ProgressBar spinner;

    Menu menu = new Menu();

    class MenuListAdapter extends BaseAdapter {

        class OnMealClickListener implements View.OnClickListener {
            final String name;

            OnMealClickListener(String name) {
                this.name = name;
            }

            @Override
            public void onClick(View v) {

//                Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();

                String url = "https://www.google.com.tr/search?um=1&hl=tr&rls=tr&channel=suggest&tbm=isch&sa=1&q="+name;

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                if (browserIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(MainActivity.this, "No browsers installed on device", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public int getCount() {
            return menu.size();
        }

        @Override
        public DateMenu getItem(int position) {
            return menu.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                v = getLayoutInflater().inflate(R.layout.list_item_date_menu, parent, false);
            }

            DateMenu m = getItem(position);

            String todayString = new SimpleDateFormat("Y-M-d").format(new Date());

            TextView dateLabel = v.findViewById(R.id.date_label);
            dateLabel.setText(m.date);

            dateLabel.setTextColor(m.date.equals(todayString) ? 0xff990000 : 0xff1e660a);

            int ids[] = { R.id.yemek1_container, R.id.yemek2_container, R.id.yemek3_container, R.id.yemek4_container };

            for (int i = 0; i < ids.length; i++) {
                RelativeLayout container = v.findViewById(ids[i]);
                TextView nameTf = (TextView) container.getChildAt(0);
                TextView caloryTf = (TextView) container.getChildAt(1);
                nameTf.setText(m.meals[i].name);
                caloryTf.setText(m.meals[i].calory+"");

                container.setOnClickListener(new OnMealClickListener(m.meals[i].name));
            }

            TextView totalCaloryTF = v.findViewById(R.id.total_calory_label);
            totalCaloryTF.setText(m.totalCalory()+"");


            return v;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuListView = findViewById(R.id.mealsList);
        menuAdapter = new MenuListAdapter();

        menuListView.setAdapter(menuAdapter);

        spinner = findViewById(R.id.spinner);

        fetchMenu();
    }


    private void fetchMenu() {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Menu menu = Menu.get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            menuFetched(menu);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        th.start();
    }

    private void menuFetched(Menu menu) {
        this.menu = menu;
        menuAdapter.notifyDataSetChanged();
        spinner.setVisibility(View.INVISIBLE);
    }
}
