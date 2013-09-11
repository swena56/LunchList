package com.andy.lunchlist;

import android.app.TabActivity;
import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends Activity {

    //create list
    List<LunchListDataModel> listOfLunchLocationDataModels = new ArrayList<LunchListDataModel>();

    //create list adapter for list
    myAdapter adapter = null;
    Button save;
    Button delete;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("onCreate","set content view to activity_main");

        //access listview from xml
        list = (ListView) findViewById(R.id.listView);

        //create button and define situation if button is pressed
         save = (Button) findViewById(R.id.buttonSave);
        delete = (Button) findViewById(R.id.buttonDelete);

        //make delete button invisible until its needed
        delete.setEnabled(false);

        //set On click listeners
        save.setOnClickListener(onClick);
        delete.setOnClickListener(onClick);

      //create adapter object
        adapter = new myAdapter();
        //set adapter
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EditText title = (EditText) findViewById(R.id.editTextName);
                EditText address = (EditText) findViewById(R.id.editTextAddress);

                LunchListDataModel dataModel = listOfLunchLocationDataModels.get(i);

                title.setText(dataModel.getLocationName());
                address.setText(dataModel.getLocationAddress());

               delete.setEnabled(true);
            }
        });

        //Tell the user that load is complete
        Toast mytoast = Toast.makeText(this,"Intial Loading Complete",5000);
        mytoast.show();
    }

    //if a click is detected
    private View.OnClickListener onClick = new View.OnClickListener()
    {
        public void onClick(View v)
        {
           // Log.d("save to list", "Data gathered from Form -> "+dataModel.toString() );
            //access text fields from xml
            EditText LocationName = (EditText) findViewById(R.id.editTextName);
            EditText LocationAddress = (EditText) findViewById(R.id.editTextAddress);

            //create new data model object
            LunchListDataModel dataModel = new LunchListDataModel();

            //radio group to set data model type
            RadioGroup radioGroupType = (RadioGroup) findViewById(R.id.radioGroupType);
            switch(radioGroupType.getCheckedRadioButtonId())
            {
                case R.id.radioButtonSitDown:
                    dataModel.setLocationType("SitDown");
                    break;
                case R.id.radioButtonTakeOut:
                    dataModel.setLocationType("TakeOut");
                    break;
                case R.id.radioButtonDelivery:
                    dataModel.setLocationType("Delivery");
                    break;
                default: dataModel.setLocationType("None");
            }

            //use text fields to set data model
            dataModel.setLocationName(LocationName.getText().toString());
            dataModel.setLocationAddress(LocationAddress.getText().toString());

            int i = 0;
            boolean isMatch = false;
            while(i<listOfLunchLocationDataModels.size()&&(listOfLunchLocationDataModels.size() != 0))
            {
                LunchListDataModel tempModel = null;
                tempModel = listOfLunchLocationDataModels.get(i);

                if(tempModel.isTheSameAs(dataModel))
                {
                    isMatch = true;
                    break;
                }
                i++;
            }

            //are the fields empty
            boolean hasErrors = false;
            if(LocationName.getText().toString().equals("")|| (LocationAddress.getText().toString().equals("")))
            {
                hasErrors = true;
            }

            //clear textViews
            LocationName.setText("");
            LocationAddress.setText("");

            //if the save button was pressed
            Log.d("save to list", "save button was pressed");
            if(v.getId() ==  R.id.buttonSave)
            {
                if(hasErrors == false)
                {
                        if(isMatch == false)
                        {
                            adapter.add(dataModel);
                            Toast confirmationThatItemWasAdded = Toast.makeText(getApplicationContext(),dataModel.toString()+"- Was added",5000);
                            confirmationThatItemWasAdded.show();
                        } else
                        {

                                Toast d = Toast.makeText(getApplicationContext(),"Entry is Already In Table",5000);
                                d.show();

                        }
                }
                else
                {
                    Toast d = Toast.makeText(getApplicationContext(),"One or More Form Fields are Empty",5000);
                    d.show();
                }
            }
            //if the delete button is pressed
            if(v.getId() == R.id.buttonDelete)
            {

                if(isMatch == true)
                {
                    listOfLunchLocationDataModels.remove(i);
                    Toast confirmationThatItemWasDeleted = Toast.makeText(getApplicationContext(),dataModel.toString()+"- Was Deleted",5000);
                    confirmationThatItemWasDeleted.show();
                }else
                {
                    Toast d = Toast.makeText(getApplicationContext(),"Nothing was deleted because entry was not in list",5000);
                    d.show();
                }

                //refresh list
                //access listview from xml
                list = (ListView) findViewById(R.id.listView);
                //create adapter object
                adapter = new myAdapter();
                //set adapter
                list.setAdapter(adapter);

            }

        }
    };


    private class myAdapter extends ArrayAdapter<LunchListDataModel>
    {
        myAdapter() {
            super(MainActivity.this, R.layout.list_item, listOfLunchLocationDataModels);
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View row = convertView;
            RestaurantHolder holder = null;

            if(row == null)
            {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.list_item,null);
                holder = new RestaurantHolder(row);
                row.setTag(holder);
            }else
            {
                holder = (RestaurantHolder)row.getTag();
            }
            holder.populateForm(listOfLunchLocationDataModels.get(position));

            return row;
        }
    }  //end of class

    //used to fill the form
    static class RestaurantHolder
    {
        private TextView name = null;
        private TextView address = null;
        private ImageView icon = null;

        RestaurantHolder(View row)
        {
            name = (TextView) row.findViewById(R.id.ListItemTitle);
            address = (TextView) row.findViewById(R.id.ListItemAddress);
            icon = (ImageView) row.findViewById(R.id.imageView);
        }
        void populateForm(LunchListDataModel dataModel)
        {
            name.setText(dataModel.getLocationName());
            address.setText(dataModel.getLocationAddress());

            //set icon in data
            if(dataModel.getLocationType().equals("SitDown"))
            {
                icon.setImageResource(R.drawable.icon_food);
            }
            else if (dataModel.getLocationType().equals("TakeOut"))
            {
                icon.setImageResource(R.drawable.takeout);
            }
            else if (dataModel.getLocationType().equals("Delivery"))
            {
                icon.setImageResource(R.drawable.delivery);
            }
            else if (dataModel.getLocationType().equals("None"))
            {
                icon.setImageResource(R.drawable.unknown);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
