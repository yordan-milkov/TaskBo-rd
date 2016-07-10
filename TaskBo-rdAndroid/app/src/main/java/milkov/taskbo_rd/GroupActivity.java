package milkov.taskbo_rd;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class GroupActivity extends AppCompatActivity {

    int groupUID;
    GroupInfo   groupInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groupUID = getIntent().getIntExtra("groupID", -1);
        setContentView(R.layout.activity_main);

        for( GroupInfo info : UserData.groupsList  )
        {
            if ( info.getIDnumber() == groupUID )
            {
                groupInfo = info;
            }
        }

        if ( UserData.issuesMap.containsKey( groupUID ) ) {
            FillIssueListview();
        }
        else {
            new RequestIssuesTask(groupUID).execute();
        }

        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object item = listView.getItemAtPosition(position);
                IssueInfo   issue   = (IssueInfo) item;

                Intent intent = new Intent( GroupActivity.this, IssueActivity.class);
                intent.putExtra( "groupUID", issue.getGroupUID() );
                intent.putExtra( "position", position );
                intent.putExtra( "editable", false );
                startActivity(intent);
            }


        });
    }

    private void FillIssueListview()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      /*  setSupportActionBar(toolbar);*/
        toolbar.setTitle( groupInfo.getName() );

        ListView listView = (ListView) findViewById(R.id.listView);
        ListItemAdapter adapter = new ListItemAdapter(GroupActivity.this, UserData.issuesMap.get( groupUID ));
        listView.setAdapter(adapter);
    }

    public class RequestIssuesTask extends AsyncTask<Void, Void, Boolean> {

        private int groupUID;

        public RequestIssuesTask(int groupUID) {
            this.groupUID = groupUID;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ConnectionManager.RequestIssuesByGroup(groupUID);
            return  true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            FillIssueListview();
        }
    }

    private class ListItemAdapter extends ArrayAdapter<IssueInfo>
    {

        public ListItemAdapter(Context context, List<IssueInfo> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            TextView textName = (TextView) convertView.findViewById(R.id.textName);
            TextView textDescription = (TextView) convertView.findViewById(R.id.textDescription);

            IssueInfo   issue = getItem(position);
            textName.setText( issue.getName() );
            textDescription.setText( issue.getDescription() );

            return convertView;
        }
    }

}
