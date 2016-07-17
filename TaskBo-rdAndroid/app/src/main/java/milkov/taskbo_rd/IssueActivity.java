package milkov.taskbo_rd;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_issue);

        Intent  intent = getIntent();
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
        addParticipant.setVisibility( View.GONE );

        AutoCompleteTextView participantText = (AutoCompleteTextView) findViewById(R.id.autoCompleteUser);
        participantText.setVisibility( View.GONE );

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        UserData.issueForEdit   = data;
        Intent intent = new Intent( IssueActivity.this, EditIssueActivity.class);
        intent.putExtra( "issueUID", data.getIssueUID() );
        startActivityForResult(intent, 8);

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FillIssueData();
    }

  //  public void onComposeAction(MenuItem mi) {
       // isEdistable = ! isEdistable;
  //  }

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
        for ( GroupInfo curr :  UserData.groupsList ) {
            if (curr.getIDnumber() == data.getGroupUID()) {

                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarIssue);
                setSupportActionBar(toolbar);
                toolbar.setTitle(curr.getName());
            }
        }

        EditText isssueName = (EditText) findViewById(R.id.editIssueName);
        isssueName.setText( data.getName() );
        isssueName.setEnabled( false );

        EditText isssueDescription = (EditText) findViewById(R.id.editDescription);
        isssueDescription.setText( data.getDescription() );
        isssueDescription.setEnabled( false );

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
    }

    public static void SetListViewHeightBasedOnChildren(ListView listView) {
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

  /*  public class AddParticipantTask extends AsyncTask<Void, Void, Boolean> {

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

        }
    }*/

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
