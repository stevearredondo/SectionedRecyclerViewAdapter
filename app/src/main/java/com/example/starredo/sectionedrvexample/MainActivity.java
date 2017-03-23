package com.example.starredo.sectionedrvexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final LinkedHashMap<String, List<String>> DATA = new LinkedHashMap<String, List<String>>() {{
        put("Section 1 Header", Arrays.asList("Section 1 Item 1", "Section 1 Item 2", "Section 1 Item 3"));
        put("Section 2 Header", Collections.singletonList("Section 2 Item 1"));
        put("Section 3 Header", new ArrayList<String>());
        put("Section 4 Header", Arrays.asList("Section 4 Item 1", "Section 4 Item 2"));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final MySectionAdapter adapter = new MySectionAdapter(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyDividerDecorator(this));

        adapter.update(DATA);
        adapter.notifyDataSetChanged();
    }
}
