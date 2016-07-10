package milkov.taskbo_rd;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class IssueActivity extends AppCompatActivity {

    private IssueData   data;
    private boolean     isEdistable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        Intent  intent = getIntent();
        isEdistable = intent.getBooleanExtra( "editable", false );

        List<IssueInfo> infoList = UserData.issuesMap.get(intent.getIntExtra("groupUID", -1));
        new RequestIssuesTask( infoList.get(intent.getIntExtra("position", -1) ) ).execute();

        Button  addCheck = (Button) findViewById(R.id.buttonNewCheck);
        addCheck.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v) {
                EditText newCehckText = (EditText) findViewById(R.id.editNewCheck);
                String  text = newCehckText.getText().toString();
                if ( text.length() > 1 ) {
                    CheckItem item = new CheckItem( text );
                    newCehckText.setText("");
                    new AddCheckTask( item ).execute();
                }
            }
        });

        Button  addParticipant = (Button) findViewById(R.id.buttonNewParticipant);
        addParticipant.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v) {
                AutoCompleteTextView participantText = (AutoCompleteTextView) findViewById(R.id.autoCompleteUser);
                String      name        = participantText.getText().toString();
                UserInfo    foundUser   = null;
                if ( name.length() > 3 ) {
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

                if ( foundUser != null ) {
                    participantText.setText("");
                    new AddParticipantTask( foundUser ).execute();
                }
                else
                {
                    participantText.setError("Не съществува такъв потребител");
                }
            }
        } );

    }

    protected void  SetResovlveButtonText()
    {
        Button  resolveButton = (Button)findViewById(R.id.buttonResolve);
        if ( data.isResolved() ) {
            resolveButton.setText("Отвори");
        }
        else
        {
            resolveButton.setText("Приключи");
        }
    }

    protected void  FillIssueData()
    {
        EditText isssueName = (EditText) findViewById(R.id.editIssueName);
        isssueName.setText( data.getName() );
        isssueName.setEnabled( isEdistable );

        EditText isssueDescription = (EditText) findViewById(R.id.editDescription);
        isssueDescription.setText( data.getDescription() );
        isssueDescription.setEnabled( isEdistable );

        SetResovlveButtonText();
        Button  resolveButton = (Button)findViewById(R.id.buttonResolve);
        resolveButton.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v) {
                data.setResolved( !data.isResolved() );
                new UpdateIssueStateTask().execute();
            }
        } );

        ListView checkListView = (ListView) findViewById(R.id.listChecks);
        CheckListItemAdapter adaptCehcks = new CheckListItemAdapter(IssueActivity.this, data.getCheckItemList());
        checkListView.setAdapter(adaptCehcks);
        SetListViewHeightBasedOnChildren(checkListView);

        ListView participansListView = (ListView) findViewById(R.id.listParticipants);
        ParticipantListItemAdapter adaptParticip = new ParticipantListItemAdapter( IssueActivity.this, data.getParticipantsList() );
        participansListView.setAdapter( adaptParticip );
        SetListViewHeightBasedOnChildren( participansListView );

        List<String>  groupUsers = new ArrayList<>();
        for ( GroupInfo curr :  UserData.groupsList ) {
            if (curr.getIDnumber() == data.getGroupUID()) {

                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarIssue);
//        setSupportActionBar(toolbar);
                toolbar.setTitle(curr.getName());

                for ( UserInfo userInf : curr.getUsers() )
                {
                    groupUsers.add( userInf.getUserName() );
                    groupUsers.add( userInf.getDisplayName() );
                }
                break;
            }
        }
        AutoCompleteTextView participantText = (AutoCompleteTextView) findViewById(R.id.autoCompleteUser);
        ArrayAdapter<String> textAdapter = new ArrayAdapter<String>( IssueActivity.this, android.R.layout.simple_dropdown_item_1line, groupUsers );
        participantText.setAdapter( textAdapter );
    }

    private static void SetListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private class CheckListItemAdapter extends ArrayAdapter<CheckItem>
    {

        public CheckListItemAdapter(Context context, List<CheckItem> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.check_item, parent, false);
            }

            CheckItem   item = getItem( position );
            CheckBox    checkBox = (CheckBox)convertView.findViewById(R.id.checkBox);
            checkBox.setText( item.description );
            checkBox.setChecked( item.isChecked );

            checkBox.setTag( item );

            checkBox.setOnClickListener( new View.OnClickListener() {

                public void onClick(View v) {
                    CheckBox    checkBox    = (CheckBox) v;
                    CheckItem   item        = (CheckItem) checkBox.getTag();

                    if ( item.isChecked != checkBox.isChecked() )
                    {
                        item.isChecked = checkBox.isChecked();
                        new UpdateCheckTask( item ).execute();
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.participant_item, parent, false);
            }

            UserInfo    info    = getItem( position );
            TextView    name    = (TextView) convertView.findViewById(R.id.textViewParticipant);
            name.setText( info.getDisplayName() );

            return convertView;
        }
    }

    public class RequestIssuesTask extends AsyncTask<Void, Void, Boolean> {

        private IssueInfo issueInfo;

        public RequestIssuesTask(IssueInfo issueInfo) {
            this.issueInfo = issueInfo;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            data = ConnectionManager.RequestIssueData( issueInfo );
            return data != null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            FillIssueData();
        }

    }

    public class AddCheckTask extends AsyncTask<Void, Void, Boolean> {

        private CheckItem checkInfo;

        public AddCheckTask(CheckItem checkInfo) {
            this.checkInfo = checkInfo;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            return ConnectionManager.AddCheckData( checkInfo, data.getIssueUID() );
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if ( aBoolean ) {
                ListView checkListView = (ListView) findViewById(R.id.listChecks);
                CheckListItemAdapter adapter = (CheckListItemAdapter) checkListView.getAdapter();
                adapter.add(checkInfo);
                SetListViewHeightBasedOnChildren(checkListView);
            }
        }

    }

    public class UpdateCheckTask extends AsyncTask<Void, Void, Boolean> {
        private CheckItem checkInfo;

        public UpdateCheckTask(CheckItem checkInfo) {
            this.checkInfo = checkInfo;
        }

        protected Boolean doInBackground(Void... params) {
            return ConnectionManager.UpdateCheckData( checkInfo.chekcID, checkInfo.isChecked );
        }

    }

    public class AddParticipantTask extends AsyncTask<Void, Void, Boolean> {

        private UserInfo userInfo;
        private int result;

        public AddParticipantTask(UserInfo userInfo) {
            this.userInfo = userInfo;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            result = ConnectionManager.AddParticipantData( userInfo.getUserName(), data.getIssueUID() );
            return result == 2;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if ( aBoolean ) {
                ListView participansListView = (ListView) findViewById(R.id.listParticipants);
                ParticipantListItemAdapter adaptParticip = (ParticipantListItemAdapter) participansListView.getAdapter();
                adaptParticip.add( userInfo );
                SetListViewHeightBasedOnChildren(participansListView);
            }
            else
            {
                AutoCompleteTextView participantText = (AutoCompleteTextView) findViewById(R.id.autoCompleteUser);
                if ( result == 1 )
                {
                    participantText.setError("Потребителят вече е добавен.");
                }
                else if ( result == 0 )
                {
                    participantText.setError("Възникна неочаквана грешка.");
                }
            }
        }
    }

    public class UpdateIssueStateTask extends AsyncTask<Void, Void, Boolean> {

        public UpdateIssueStateTask() {
        }

        protected Boolean doInBackground(Void... params) {
            return ConnectionManager.UpdateIssueStatus( data.getIssueUID(), data.isResolved() );
        }

        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if ( aBoolean ) {
                SetResovlveButtonText();
            }
            else
            {
                data.setResolved( ! data.isResolved() );
            }
        }
    }
}
