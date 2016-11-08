package com.example.lenovot540p.addressbook;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;


public class ContactList extends Activity implements ContactListFragment.ContactListFragmentListener, contact_details.contact_detailsListener, add_edit_activity.add_edit_activityListener{


    public static final String Num = "row_id";

    ContactListFragment contactListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        System.out.println("test1");
        if(savedInstanceState != null)
        {
           return;
        }
        System.out.println("test3");
        if(findViewById(R.id.listView) != null)
        {
            System.out.println("test2a");
            contactListFragment = new ContactListFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.listView, contactListFragment);
               transaction.commit();
        }
        System.out.println("test2");
    }

    @Override
    public void onResume()
    {
        System.out.println("test4");
        super.onResume();


        System.out.println("test5");
    }


    public void onContactSelected(long numberRows)
    {
        if(findViewById(R.id.listView)!= null)
        {
            displayContact(numberRows, R.id.listView);
        }

    }


    public void displayContact(long numberRows, int viewID)
    {
        System.out.println("test");
        contact_details contact_detail = new contact_details();
        Bundle arguments = new Bundle();
        arguments.putLong(Num, numberRows);
        contact_detail.setArguments(arguments);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(viewID, contact_detail);

        transaction.addToBackStack(null);
        transaction.commit();
    }



    @Override
    public void onAddContact()
    {

            displayadd_edit_activity(R.id.listView, null);


    }

    private void displayadd_edit_activity(int viewID, Bundle arguments)
    {
        add_edit_activity add_edit_activity = new add_edit_activity();
        if(arguments != null)
        {
            add_edit_activity.setArguments(arguments);
        }

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(viewID, add_edit_activity);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onContactDeleted()
    {
        getFragmentManager().popBackStack();
        if(findViewById(R.id.listView)== null)
        {
            contactListFragment.updateContactList();
        }
    }

    @Override
    public void onEditContact(Bundle arguments)
    {

            displayadd_edit_activity(R.id.listView, arguments);


    }
    @Override
    public void onAddEditCompleted(long numberRows)
    {
        getFragmentManager().popBackStack();

    }


}
