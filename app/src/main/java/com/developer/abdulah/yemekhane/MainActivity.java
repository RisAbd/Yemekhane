package com.developer.abdulah.yemekhane;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.developer.abdulah.yemekhane.yemekhane.DateMenu;
import com.developer.abdulah.yemekhane.yemekhane.Menu;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "kek";

    private ListView menuListView;
    private MenuListAdapter menuAdapter;

    Menu menu = new Menu();

    class MenuListAdapter extends BaseAdapter {



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

            TextView dateLabel = v.findViewById(R.id.date_label);
            dateLabel.setText(m.date);

            int ids[] = { R.id.yemek1_label, R.id.yemek2_label, R.id.yemek3_label, R.id.yemek4_label };

            for (int i = 0; i < ids.length; i++) {
                TextView mealLabel = v.findViewById(ids[i]);
                mealLabel.setText(m.meals[i].toString());
            }

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
    }
}
