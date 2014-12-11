package com.flatironschool.intents_services;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.flatironschool.intents_services.Services.PollService;

import org.apache.http.protocol.HTTP;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.send_message_button);

        button.setOnClickListener(mOnClickListener);
    }


    private View.OnClickListener mOnClickListener =
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
            EditText phoneNumberEditText = (EditText)findViewById(R.id.phone_edit_text);
            EditText messageEditText = (EditText)findViewById(R.id.message_edit_text);

            String phoneNumber = phoneNumberEditText.getText().toString();
            String message = messageEditText.getText().toString();

            Intent sendIntent = new Intent();

            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra("sms_body", message);
            sendIntent.putExtra("address", phoneNumber);
            sendIntent.setType(HTTP.PLAIN_TEXT_TYPE);

            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(sendIntent);
            }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem toggleItem = (MenuItem) menu.findItem(R.id.menu_item_toggle_polling);
        if (PollService.isServiceAlarmOn(this)){
            toggleItem.setTitle("Stop Polling");
        } else {
            toggleItem.setTitle("Start Polling");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.menu_item_toggle_polling) {
            boolean shouldStartAlarm = !PollService.isServiceAlarmOn(this);
            PollService.setServiceAlarm(this, shouldStartAlarm);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                invalidateOptionsMenu();

                return true;
            }
        }


        return super.onOptionsItemSelected(item);
    }
}
