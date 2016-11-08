package com.example.lenovot540p.addressbook;

import android.app.Activity;
import android.app.ListFragment;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.sql.SQLException;

/**
 * Created by LenovoT540p on 4/13/2015.
 */
public class ContactListFragment extends ListFragment {


    public interface ContactListFragmentListener
    {
        public void onContactSelected(long num);

        public void onAddContact();
    }

    AdapterView.OnItemClickListener viewContactListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long number)
        {
            listener.onContactSelected(number);
        }
    };

    private ContactListFragmentListener listener;
    private ListView contactListView;
    private CursorAdapter contactAdapter;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        listener = (ContactListFragmentListener) activity;
    }


    @Override
    public void onDetach()
    {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        setEmptyText(getResources().getString(R.string.no_contact));
        contactListView = getListView();
        contactListView.setOnItemClickListener(viewContactListener);
        contactListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        String[] from = new String[] {"name"};
        int[] to = new int[] {android.R.id.text1};
        contactAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, null, from, to, 0);
        setListAdapter(contactAdapter);
    }



    @Override
    public void onResume()
    {
        super.onResume();
        new GetContactsTask().execute((Object[]) null);
    }

    private class GetContactsTask extends AsyncTask<Object, Object, Cursor>
    {
        activity databaseConnector = new activity(getActivity());

        @Override
        protected Cursor doInBackground(Object... params)
        {
            try {
                databaseConnector.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return databaseConnector.getAllContacts();
        }

        @Override
        protected void onPostExecute(Cursor result)
        {
            contactAdapter.changeCursor(result);
            try {
                databaseConnector.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

        @Override
        public void onStop()
        {
            Cursor cursor = contactAdapter.getCursor();
            contactAdapter.changeCursor(null);

            if(cursor != null)
            {
                cursor.close();
            }
            super.onStop();

        }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.new_menu, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_add:
                listener.onAddContact();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    public void updateContactList()
    {
        new GetContactsTask().execute((Object[]) null);
    }


}
