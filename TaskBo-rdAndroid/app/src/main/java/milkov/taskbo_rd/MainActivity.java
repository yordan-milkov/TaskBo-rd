package milkov.taskbo_rd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new RequestGroupsTask().execute();

        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object item = listView.getItemAtPosition(position);
                GroupInfo   group   = (GroupInfo)item;

                Intent intent = new Intent( MainActivity.this, GroupActivity.class);
                intent.putExtra( "groupID", group.getIDnumber() );
                startActivity(intent);
            }


        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    public class RequestGroupsTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            ConnectionManager.RequestGroups();
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            ListView listView = (ListView) findViewById(R.id.listView);
            ListItemAdapter adapter = new ListItemAdapter( MainActivity.this, UserData.groupsList );
            listView.setAdapter( adapter );
        }
    }

    private class ListItemAdapter extends ArrayAdapter<GroupInfo>
    {

        public ListItemAdapter(Context context, List<GroupInfo> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            TextView textName = (TextView) convertView.findViewById(R.id.textName);
            TextView textDescription = (TextView) convertView.findViewById(R.id.textDescription);

            GroupInfo   group = getItem(position);
            textName.setText( group.getName() );
            textDescription.setText( group.getDescription() );

            return convertView;
        }
    }

}
