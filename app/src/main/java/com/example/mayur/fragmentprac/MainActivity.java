package com.example.mayur.fragmentprac;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.Random;


public class MainActivity extends ActionBarActivity {
    private NoteOpenHelper.NotesDBApi dbApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MyListFragment())
                    .commit();
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new ShitFragment())
//                    .commit();

        }
    }
}
