package com.example.pcos;

import android.content.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CsvReader {
    public static ArrayList<String[]> readCsv(Context context, String fileName) {
        ArrayList<String[]> resultList = new ArrayList<>();
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] row = line.split(",");
                resultList.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}

