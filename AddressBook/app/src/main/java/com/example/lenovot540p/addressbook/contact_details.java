package com.example.lenovot540p.addressbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.SQLException;

/**
 * Created by Mike on 4/13/2015.
 */
public class contact_details extends Fragment {

    public interface contact_detailsListener
    {
        public void onContactDeleted();

        public void onEditContact(Bundle arguments);
    }

    contact_detailsListener listener;
    long num = -1;
    TextView nameInXml;
    TextView phoneInXml;
    TextView emailInXml;

    TextView streetInXml;

    TextView cityTextView;
    TextView stateInXml;
    TextView zipInXml;


    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
        listener = (contact_detailsListener) activity;

    }

    @Override
    public void onDetach() {

        super.onDetach();
        listener = null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setRetainInstance(true);

        if(savedInstanceState != null)
        {
            num = savedInstanceState.getLong(ContactList.Num);
        }
else
        {
            Bundle arguments = getArguments();
            if(arguments != null)
            {
                num = arguments.getLong(ContactList.Num);
            }
        }

        View view = inflater.inflate(R.layout.contact_details, container, false);
        setHasOptionsMenu(true);
        nameInXml = (TextView) view.findViewById(R.id.nametext);
        phoneInXml = (TextView) view.findViewById(R.id.phonetext);
        emailInXml = (TextView) view.findViewById(R.id.emailtext);
        streetInXml = (TextView) view.findViewById(R.id.streettext);
        cityTextView = (TextView) view.findViewById(R.id.citytext);
        stateInXml = (TextView) view.findViewById(R.id.statetext);
        zipInXml = (TextView) view.findViewById(R.id.ziptext);


        return view;
    }


    @Override
    public void onResume() {

        super.onResume();
        new LoadContactTask().execute(num);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(ContactList.Num, num);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_contact_list,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch(item.getItemId())
        {
            case R.id.action_edit:

                Bundle arguments = new Bundle();
                arguments.putLong(ContactList.Num, num);
                arguments.putCharSequence("name", nameInXml.getText());
                arguments.putCharSequence("phone", phoneInXml.getText());
                arguments.putCharSequence("email", emailInXml.getText());
                arguments.putCharSequence("street", streetInXml.getText());
                arguments.putCharSequence("city", cityTextView.getText());
                arguments.putCharSequence("state", stateInXml.getText());
                arguments.putCharSequence("zip", zipInXml.getText());
                System.out.println("Test20");
                listener.onEditContact(arguments);
                System.out.println("Test21");
                return true;


            case R.id.action_delete:
                System.out.println("Test22");
                deleteContact();
                System.out.println("Test23");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class LoadContactTask extends AsyncTask<Long, Object, Cursor>
    {
        activity activity = new activity(getActivity());
        @Override
        protected Cursor doInBackground(Long... params)
        {
            try {
                activity.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return activity.getOneContact(params[0]);



        }
        @Override
        protected void onPostExecute(Cursor result) {
            super.onPostExecute(result);
            result.moveToFirst();

            int nameIndex = result.getColumnIndex("name");
            int phoneIndex = result.getColumnIndex("phone");
            int emailIndex = result.getColumnIndex("email");
            int streetIndex = result.getColumnIndex("street");
            int cityIndex = result.getColumnIndex("city");
            int stateIndex = result.getColumnIndex("state");
            int zipIndex = result.getColumnIndex("zip");

            nameInXml.setText(result.getString(nameIndex));
            phoneInXml.setText(result.getString(phoneIndex));
            emailInXml.setText(result.getString(emailIndex));
            streetInXml.setText(result.getString(streetIndex));
            cityTextView.setText(result.getString(cityIndex));
            stateInXml.setText(result.getString(stateIndex));
            zipInXml.setText(result.getString(zipIndex));

            result.close();
            try {
                activity.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    public void deleteContact()
    {
        confirmDelete.show(getFragmentManager(), "confirm delete");
    }

    public DialogFragment confirmDelete = new DialogFragment()
    {
        @Override
        public Dialog onCreateDialog(Bundle bundle)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.confirm_title);
            builder.setMessage(R.string.confirm_message);
            builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener()
            {
            @Override
            public void onClick(DialogInterface dialog, int button)

                {
                    final activity activity = new activity(getActivity());
                    AsyncTask<Long, Object, Object> deleteTask = new AsyncTask<Long, Object, Object>() {
                        @Override
                        protected Object doInBackground(Long... params) {
                            try {
                                activity.deleteContact(params[0]);

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object result) {
                            listener.onContactDeleted();
                        }

                    };

                    deleteTask.execute(new Long[] {num});
                }
        }

            );
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
            {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            return builder.create();
        }
    };
}
