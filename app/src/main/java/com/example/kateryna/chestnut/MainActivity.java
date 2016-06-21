package com.example.kateryna.chestnut;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    List<RestList> Rests = new ArrayList<RestList>();
    ListView restListView;
    Uri imageUri = Uri.parse("android.resource://com.example.kateryna.chestnut/mipmap/ic.png");
    DatabaseHandler dbHandler;
    EditText Namerst, Phonerst, Starsrst, Addressrst;
    ImageView resimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button addRst = (Button) findViewById(R.id.addRst);
        Namerst = (EditText) findViewById(R.id.rstName);
        Phonerst = (EditText) findViewById(R.id.rstPhone);
        Starsrst = (EditText) findViewById(R.id.rstStars);
        Addressrst = (EditText) findViewById(R.id.rstAddress);
        restListView = (ListView) findViewById(R.id.listView);
        resimg = (ImageView) findViewById(R.id.restImg);
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        dbHandler = new DatabaseHandler(getApplicationContext());
        registerForContextMenu(restListView);

        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("List");
        tabSpec.setContent(R.id.List);
        tabSpec.setIndicator("Restaurants");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Add");
        tabSpec.setContent(R.id.Add);
        tabSpec.setIndicator("ADD");
        tabHost.addTab(tabSpec);

        addRst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RestList rest = new RestList(dbHandler.getRestCount(), String.valueOf(Namerst.getText()),
                        String.valueOf(Phonerst.getText()), String.valueOf(Starsrst.getText()), String.valueOf(Addressrst.getText()),
                         imageUri);
                dbHandler.createRest(rest);
                Rests.add(rest);
                populateList();
                Toast.makeText(getApplicationContext(), Namerst.getText().toString() + " has been added!", Toast.LENGTH_SHORT).show();
            }
        });


        Namerst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addRst.setEnabled(!Namerst.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });

        resimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Restaurant Image"), 1);
            }
        });

       List<RestList> addableRests = dbHandler.getAllRests();
         int restCount = dbHandler.getRestCount();
        for (int i = 0; i < restCount; i++) {
            Rests.add(addableRests.get(i));
        }
        if (!addableRests.isEmpty()) {populateList();}
    }


    public void onActivityResult(int reqCode, int resCode, Intent data){
        if (resCode == RESULT_OK){
            if (reqCode == 1){
                imageUri = data.getData();
                resimg.setImageURI(data.getData());
            }
        }
    }

    private void populateList (){
        ArrayAdapter<RestList> adapter = new RestListListAdapter();
        restListView.setAdapter(adapter);
    }

    private class RestListListAdapter extends ArrayAdapter<RestList> {
public RestListListAdapter (){
    super(MainActivity.this, R.layout.listview_item, Rests);
}
        @Override
        public View getView(int position, View view, ViewGroup parent){
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);
            }
RestList currentRest = Rests.get(position);

                TextView name = (TextView) view.findViewById(R.id.restName);
                name.setText(currentRest.getName());

                TextView stars = (TextView) view.findViewById(R.id.restStars);
                stars.setText(currentRest.getStars());

                TextView phone = (TextView) view.findViewById(R.id.restPhone);
                phone.setText(currentRest.getPhone());

                TextView address = (TextView) view.findViewById(R.id.restAddress);
                address.setText(currentRest.getAddress());
ImageView ivRestimg = (ImageView) view.findViewById(R.id.ivRestimg);
            ivRestimg.setImageURI(currentRest.get_imageUri());

            return view;
        }
    }

}
