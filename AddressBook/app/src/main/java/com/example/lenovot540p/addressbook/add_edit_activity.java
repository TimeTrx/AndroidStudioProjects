package com.example.lenovot540p.addressbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;

/**
 * Created by Mike on 4/13/2015.
 */
public class add_edit_activity extends Fragment {



    public interface add_edit_activityListener
    {
        public void onAddEditCompleted(long rowID);
    }

    add_edit_activityListener listener;
    Bundle contactInfoBundle;
    Button saveContactButton;
    EditText nameEditText;
    EditText phoneEditText;
    EditText emailEditText;
    EditText streetEditText;
    EditText cityEditText;
    EditText stateEditText;
    EditText zipEditText;

    long rowID;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (add_edit_activityListener) activity;

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
        setHasOptionsMenu(true);


        View view = inflater.inflate(R.layout.add_edit_activity, container, false);

        nameEditText = (EditText) view.findViewById(R.id.nametext);
        phoneEditText = (EditText) view.findViewById(R.id.phonetext);
        emailEditText = (EditText) view.findViewById(R.id.emailtext);
        streetEditText = (EditText) view.findViewById(R.id.streettext);
        cityEditText = (EditText) view.findViewById(R.id.citytext);
        stateEditText = (EditText) view.findViewById(R.id.statetext);
        zipEditText = (EditText) view.findViewById(R.id.ziptext);

        contactInfoBundle = getArguments();

        if(contactInfoBundle != null)
        {
            rowID = contactInfoBundle.getLong(ContactList.Num);
            nameEditText.setText(contactInfoBundle.getString("name"));
            phoneEditText.setText(contactInfoBundle.getString("phone"));
            streetEditText.setText(contactInfoBundle.getString("street"));
            cityEditText.setText(contactInfoBundle.getString("city"));
            stateEditText.setText(contactInfoBundle.getString("province"));
            zipEditText.setText(contactInfoBundle.getString("postal"));
        }

        saveContactButton = (Button) view.findViewById(R.id.button);

        saveContactButton.setOnClickListener(saveContactButtonClicked);


        return view;
    }

    View.OnClickListener saveContactButtonClicked = new View.OnClickListener()
    {


        @Override
        public void onClick(View v)
        {

            System.out.println("test10");


                if (nameEditText.getText().toString().trim().length() != 0) {

                    AsyncTask<Object, Object, Object> saveContactTask = new AsyncTask<Object, Object, Object>() {

                    @Override
                        protected Object doInBackground(Object... params) {
                            System.out.println("test11");
                            try {
                                saveContact();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }


                        protected void doPostExecute(Object... params) {
                            System.out.println("test12");
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                            listener.onAddEditCompleted(rowID);

                        }
                    };
                    System.out.println("test13");
                    saveContactTask.execute((Object[]) null);
                }

else
                {
                    DialogFragment errorsaving = new DialogFragment(){
                        @Override
                        public Dialog onCreateDialog(Bundle savedInstanceState)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage(R.string.errormessage);
                            builder.setPositiveButton(R.string.ok, null);
                            return builder.create();

                        }
                    };
                }


            }
    };


    private void saveContact() throws SQLException {
        activity activity = new activity(getActivity());
        if(contactInfoBundle == null)
        {
            rowID = activity.insertContact(
                    nameEditText.getText().toString(),
                    phoneEditText.getText().toString(),
                    emailEditText.getText().toString(),
                    streetEditText.getText().toString(),
                    cityEditText.getText().toString(),
                    stateEditText.getText().toString(),
                    zipEditText.getText().toString()
            );
        }
        else
        {
            activity.updateContact(rowID,
                    nameEditText.getText().toString(),
                    phoneEditText.getText().toString(),
                    emailEditText.getText().toString(),
                    streetEditText.getText().toString(),
                    cityEditText.getText().toString(),
                    stateEditText.getText().toString(),
                    zipEditText.getText().toString());
        }
    }
}
