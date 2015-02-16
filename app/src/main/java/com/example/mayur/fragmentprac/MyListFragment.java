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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NoteOpenHelper.NotesDBApi dbApi = new NoteOpenHelper.NotesDBApi(getActivity());
        dbApi.open();
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
        NoteOpenHelper.NotesDBApi dbApi = new NoteOpenHelper.NotesDBApi(getActivity());
        dbApi.open();
        List<NoteOpenHelper.Note> values = dbApi.getAllNotes();

        ArrayAdapter<NoteOpenHelper.Note> adapter = new ArrayAdapter<NoteOpenHelper.Note>(getActivity(), android.R.layout.simple_list_item_1, values);

//        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Heroes, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        dbApi.close();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        @SuppressWarnings("unchecked")
        final ArrayAdapter<NoteOpenHelper.Note> adapter = (ArrayAdapter<NoteOpenHelper.Note>) getListAdapter();
        NoteOpenHelper.NotesDBApi dbApi = new NoteOpenHelper.NotesDBApi(getActivity());
        dbApi.open();
        Log.i("activity-shit", "**** List view is clicked ****");
        Log.i("activity-shit", "**** position : " + position);
        Log.i("activity-shit", "**** ID : " + id);

        final TextView temp = (TextView) v;
        final NoteOpenHelper.Note note = adapter.getItem(position);
//        Toast.makeText(getActivity(), temp.getText() + "" + position, Toast.LENGTH_SHORT).show();
        Log.i("activity-shit", "**** NOTE-ID : " + note.getId());

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Edit");
        alert.setMessage("Update the note");

        EditText input = new EditText(getActivity());
        alert.setView(input);

        alert.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String updated_note = temp.getText().toString();
//                NoteOpenHelper.Note note_updated = dbApi.update(note.getId(), updated_note);
//                adapter.add(note_updated);
//                adapter.notifyDataSetChanged();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // nothing to be done
            }
        });

        alert.show();
        dbApi.close();
    }

    // for button clicks
    public void onClickButton(View v) {
        @SuppressWarnings("unchecked")
        final ArrayAdapter<NoteOpenHelper.Note> adapter = (ArrayAdapter<NoteOpenHelper.Note>) getListAdapter();
        final NoteOpenHelper.NotesDBApi dbApi = new NoteOpenHelper.NotesDBApi(getActivity());
        dbApi.open();

        switch (v.getId()) {
            case R.id.add:
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
        dbApi.close();
    }


}