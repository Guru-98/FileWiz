package io.github.guru_98.filewiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Home extends AppCompatActivity {

    private String path;
    private ListView FS_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FS_List = (ListView) findViewById(R.id.FS_List);

        path = "/";
        if (getIntent().hasExtra("path")){
            path=getIntent().getStringExtra("path");
        }
        setTitle(path);

        List FS = new ArrayList();
        File FS_file = new File(path);
        if(!FS_file.canRead()){
            setTitle(getTitle()+" (inaccessible)");
        }
        String[] list = FS_file.list();
        for (String file:list
             )
            FS.add(file);

        Collections.sort(FS);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_2,android.R.id.text1, FS);

        FS_List.setAdapter(adapter);
        FS_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String filename = (String) FS_List.getAdapter().getItem(position);
                if (path.endsWith(File.separator)) {
                    filename = path + filename;
                } else {
                    filename = path + File.separator + filename;
                }
                if (new File(filename).isDirectory()) {
                    Intent intent = new Intent(parent.getContext(), Home.class);
                    intent.putExtra("path", filename);
                    startActivity(intent);
                } else {
                    Toast.makeText(parent.getContext(), filename + " is not a directory", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
