package com.developer.abdulah.yemekhane.yemekhane;

import java.util.ArrayList;
import java.io.IOException;
import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;


public class Menu extends ArrayList<DateMenu> {


    public static Menu get() throws IOException {

//        Document doc = Jsoup.parse(new File("menu.html"), "UTF-8");

         String urlString = "http://bis.manas.edu.kg/menu/";
         Document doc = Jsoup.connect(urlString).get();

        // BufferedWriter writer = new BufferedWriter(new FileWriter("menu.html"));
        // writer.write(doc.html());

        ArrayList<Element> tableRows = doc.select("tbody").get(0).children();

        Menu menu = new Menu();

        for (int i = 1; i < tableRows.size(); i++) {

            Element row = tableRows.get(i);

            ArrayList<Element> rowData = row.children();
            String date = rowData.get(0).text();

            Meal meals[] = new Meal[4];

            for (int j = 0; j < 4; j++) {
                int ri = j*2+1;
                String mealName = rowData.get(ri).text();
                int mealCalory = Integer.valueOf(rowData.get(ri+1).text());

                Meal m = new Meal(mealName, mealCalory);
                meals[j] = m;
            }

            DateMenu m = new DateMenu(date, meals);
            // System.out.println(m);

            menu.add(m);

            // System.out.println(row);
            // data.add(row.text());
        }

        return menu;
    }

    
}