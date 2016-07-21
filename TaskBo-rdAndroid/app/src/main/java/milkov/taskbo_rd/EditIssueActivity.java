package milkov.taskbo_rd;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EditIssueActivity extends AppCompatActivity {

    protected boolean   isNew;
    protected IssueData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        Intent  thisIntent = getIntent();
        int issueUID = thisIntent.getIntExtra("issueUID", -1 );
        if ( UserData.issueForEdit != null && UserData.issueForEdit.getIssueUID() == issueUID ) {
            isNew   = false;
            data    = UserData.issueForEdit;
        }
        else
        {
            isNew   = true;
            data    = new IssueData();
            data.setGroupUID( thisIntent.getIntExtra( "groupUID", -1 ) );
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarIssue);
        setSupportActionBar(toolbar);

        Button resolveButton = (Button)findViewById(R.id.buttonResolve);
        resolveButton.setVisibility( View.GONE );

        EditText isssueName = (EditText) findViewById(R.id.editIssueName);
        isssueName.setText( data.getName() );

        EditText isssueDescription = (EditText) findViewById(R.id.editDescription);
        isssueDescription.setText( data.getDescription() );

        TextView textList = (TextView) findViewById(R.id.textChecklist);

        if ( data.getCheckItemList().isEmpty() )
        {
            textList.setVisibility( View.GONE );
        }
        else {
            textList.setText( "Редактиране и изтриване от списъка:" );
            ListView checkListView = (ListView) findViewById(R.id.listChecks);
            CheckListItemAdapter adaptCehcks = new CheckListItemAdapter(EditIssueActivity.this, data.getCheckItemList());
            checkListView.setAdapter(adaptCehcks);
            IssueActivity.SetListViewHeightBasedOnChildren(checkListView);
        }

        Button  addCheck = (Button) findViewById(R.id.buttonNewCheck);
        addCheck.setVisibility( View.GONE );

        EditText newCehckText = (EditText) findViewById(R.id.editNewCheck);
        newCehckText.setVisibility( View.GONE );

        TextView textParticipants = (TextView) findViewById(R.id.textParticipants );
        textParticipants.setText( "Изтриване и добавяне на изпълнители:" );

        ListView participansListView = (ListView) findViewById(R.id.listParticipants);
        ParticipantListItemAdapter adaptParticip = new ParticipantListItemAdapter( EditIssueActivity.this, data.getParticipantsList() );
        participansListView.setAdapter( adaptParticip );
        IssueActivity.SetListViewHeightBasedOnChildren( participansListView );

        List<String>  groupUsers = new ArrayList<>();
        for ( GroupInfo curr :  UserData.groupsList ) {
            if (curr.getIDnumber() == data.getGroupUID())
            {
                for ( UserInfo userInf : curr.getUsers() )
                {
                    groupUsers.add( userInf.getUserName() );
                    groupUsers.add( userInf.getDisplayName() );
                }
                break;
            }
        }
        AutoCompleteTextView participantText = (AutoCompleteTextView) findViewById(R.id.autoCompleteUser);
        ArrayAdapter<String> textAdapter = new ArrayAdapter<String>( EditIssueActivity.this, android.R.layout.simple_dropdown_item_1line, groupUsers );
        participantText.setAdapter( textAdapter );

        Button  addParticipant = (Button) findViewById(R.id.buttonNewParticipant);
        addParticipant.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v) {
                AutoCompleteTextView participantText = (AutoCompleteTextView) findViewById(R.id.autoCompleteUser);
                String      name        = participantText.getText().toString();
                UserInfo    foundUser   = null;
                if ( name.length() > 2 ) {
                    for (GroupInfo curr : UserData.groupsList) {
                        if (curr.getIDnumber() == data.getGroupUID()) {
                            for (UserInfo userInf : curr.getUsers()) {
                                if (name.equals(userInf.getUserName()) || name.equals(userInf.getDisplayName())) {
                                    foundUser = userInf;
                                }
                            }
                            break;
                        }
                    }
                }

                if ( foundUser != null )
                {
                    boolean exsists = false;
                    String  userUID = foundUser.getUserName();
                    for (UserInfo curr : data.getParticipantsList() ) {
                        if ( userUID.equals( curr.getUserName() ) )
                        {
                            exsists = true;
                            break;
                        }
                    }

                    if ( ! exsists ) {
                        ListView participansListView = (ListView) findViewById(R.id.listParticipants);
                        ParticipantListItemAdapter adaptParticip = (ParticipantListItemAdapter) participansListView.getAdapter();
                        adaptParticip.add( foundUser );
                        IssueActivity.SetListViewHeightBasedOnChildren(participansListView);
                    }
                    else
                    {
                        participantText.setError("Потребителят вече е добавен.");
                    }
                }
                else
                {
                    participantText.setError("Не съществува такъв потребител");
                }
                participantText.setText("");
            }
        } );
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarIssue);
        if ( isNew )
            toolbar.setTitle("Добавяне на задача");
        else
            toolbar.setTitle("Редактиране на задача");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        EditText isssueName = (EditText) findViewById(R.id.editIssueName);
        data.setName( isssueName.getText().toString() );

        EditText isssueDescription = (EditText) findViewById(R.id.editDescription);
        data.setDescription( isssueDescription.getText().toString() );

        ListView checkListView = (ListView) findViewById(R.id.listChecks);
        CheckListItemAdapter adapter = (CheckListItemAdapter) checkListView.getAdapter();
        List<CheckItem> arrayChecks = new ArrayList<>();
        int checkNum = adapter.getCount();
        for( int i = 0; i< checkNum; i++ )
        {
            CheckItem   checkItem    = adapter.getItem( i );
            if ( ! checkItem.isForDelete )
            {
                arrayChecks.add( checkItem );
            }
        }
        data.setCheckItemList( arrayChecks );

        ListView participansListView = (ListView) findViewById(R.id.listParticipants);
        ParticipantListItemAdapter adaptParticip = (ParticipantListItemAdapter) participansListView.getAdapter();
        List<UserInfo> paricipantList = new ArrayList<>();
        int adaptParticipCount = adaptParticip.getCount();
        for( int i = 0; i< adaptParticipCount; i++ )
        {
            UserInfo   checkItem    = adaptParticip.getItem( i );
            if ( ! checkItem.isForDelete ) {
                paricipantList.add( checkItem );
            }
        }
        data.setParticipantsList( paricipantList );
        new UpdateDataTaskTask().execute();

        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setMessage("Сигурни ли сте, че искате да прекратите редактирането?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Да",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        builder.setNegativeButton(
                "Не",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private class CheckListItemAdapter extends ArrayAdapter<CheckItem>
    {

        public CheckListItemAdapter(Context context, List<CheckItem> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_edti_check, parent, false);
            }

            CheckItem    info        = getItem( position );
            EditText    checkText   = (EditText)convertView.findViewById(R.id.editTextCheck);;
            checkText.setText( info.description );
            checkText.setTag( info );
            checkText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus){
                        EditText    checkText   = (EditText) v;
                        CheckItem   item        = (CheckItem) checkText.getTag();

                        item.description = checkText.getText().toString();
                    }
                }
            });

            CheckBox    checkBoxDel = (CheckBox)convertView.findViewById(R.id.checkBoxDelete);
            checkBoxDel.setTag( info );
            checkBoxDel.setOnClickListener( new View.OnClickListener() {

                public void onClick(View v) {
                    CheckBox    checkBox    = (CheckBox) v;
                    CheckItem   item        = (CheckItem) checkBox.getTag();

                    if ( item.isForDelete != checkBox.isChecked() )
                    {
                        item.isForDelete = checkBox.isChecked();
                    }
                }
            } );

            return convertView;
        }
    }

    private class ParticipantListItemAdapter extends ArrayAdapter<UserInfo>
    {

        public ParticipantListItemAdapter(Context context, List<UserInfo> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.check_item, parent, false);
            }

            UserInfo    info    = getItem( position );
            CheckBox    checkBox = (CheckBox)convertView.findViewById(R.id.checkBox);
            checkBox.setText( info.getDisplayName() );
            checkBox.setTag( info );
            checkBox.setOnClickListener( new View.OnClickListener() {

                public void onClick(View v) {
                    CheckBox   checkBox    = (CheckBox) v;
                    UserInfo   item        = (UserInfo) checkBox.getTag();

                    if ( item.isForDelete != checkBox.isChecked() )
                    {
                        item.isForDelete = checkBox.isChecked();
                    }
                }
            } );

            return convertView;
        }
    }

    public class UpdateDataTaskTask extends AsyncTask<Void, Void, Boolean> {

        protected Boolean doInBackground(Void... params) {
            return ConnectionManager.UpdateIsseData( data );
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                ArrayList<IssueInfo>    infos = UserData.issuesMap.get( data.getGroupUID() );
                if ( isNew )
                {
                    infos.add( data );
                }
                else
                {
                    for( IssueInfo curr : infos )
                    {
                        if ( curr.getIssueUID() == data.getIssueUID() )
                        {
                            curr.setName( data.getName() );
                            curr.setDescription( data.getDescription() );
                            break;
                        }
                    }
                }
                finish();
            }
        }

    }
}
