package com.example.mayur.fragmentprac;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;


/**
 * Created by mayur on 2/2/15.
 */
public class MyListFragment extends ListFragment {
    private NoteOpenHelper.NotesDBApi dbApi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button btn_add = (Button) view.findViewById(R.id.add);
        Button btn_del = (Button) view.findViewById(R.id.delete);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton(v);
            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton(v);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i("activity-shit", "**** activity is created ****");
        super.onActivityCreated(savedInstanceState);


        dbApi = new NoteOpenHelper.NotesDBApi(getActivity());
        dbApi.open();
        List<NoteOpenHelper.Note> values = dbApi.getAllNotes();

        ArrayAdapter<NoteOpenHelper.Note> adapter = new ArrayAdapter<NoteOpenHelper.Note>(getActivity(), android.R.layout.simple_list_item_1, values);

//        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Heroes, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        @SuppressWarnings("unchecked")
        final ArrayAdapter<NoteOpenHelper.Note> adapter = (ArrayAdapter<NoteOpenHelper.Note>) getListAdapter();

        Log.i("activity-shit", "**** List view is clicked ****");
        final TextView temp = (TextView) v;
//        Toast.makeText(getActivity(), temp.getText() + "" + position, Toast.LENGTH_SHORT).show();
        final String note_old = temp.getText().toString();
        Log.i("NOTE-TAG", "$$$$ --- OLD " + note_old + " --- $$$$");

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Edit");
        alert.setMessage("bwat the fuck");

        EditText input = new EditText(getActivity());
        alert.setView(input);

        alert.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NoteOpenHelper.Note note = dbApi.update(note_old);

                String note_new = temp.getText().toString();
                Log.i("NOTE-TAG", "$$$$ --- NEW " + note_new + " --- $$$$");
                adapter.add(note);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // nothing to be done
            }
        });

        alert.show();
    }

    // for button clicks
    public void onClickButton(View v) {
        @SuppressWarnings("unchecked")
        final ArrayAdapter<NoteOpenHelper.Note> adapter = (ArrayAdapter<NoteOpenHelper.Note>) getListAdapter();

        switch (v.getId()) {
            case R.id.add:
//                String[] notes = new String[]{"Cool", "Very nice", "Hate it"};
//                int nextInt = new Random().nextInt(3);


                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Enter Input");
                alert.setMessage("Do something gawd damn it");

                //input is my edit view
                final EditText input = new EditText(getActivity());
                alert.setView(input);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NoteOpenHelper.Note note;
                        note = dbApi.create(input.getText().toString());
                        adapter.add(note);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // nothing to be done
                    }
                });

                alert.show();
                // save the new comment to the database
//                note = dbApi.create(notes[nextInt]);
//                adapter.add(note);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    NoteOpenHelper.Note note;
                    note = (NoteOpenHelper.Note) getListAdapter().getItem(0);
                    dbApi.delete(note);
                    adapter.remove(note);
                }
                break;
        }
        adapter.notifyDataSetChanged();

    }


}