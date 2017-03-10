package com.ashish.smsreaderverify;

/**
 * Created by Ashish on 03-03-2017.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;



public class IncomingSms extends BroadcastReceiver {
    // Get the object of SmsManager, named it sms
    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {

// Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);

                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;

                    String message = currentMessage.getDisplayMessageBody();

                    String digits="";
                    for(int j=0;j<message.length();j++)
                    {
                     char ch=message.charAt(j);
                     if(Character.isDigit(ch))//to check numeric digits from sms
                     {
                         digits=digits+ch;
                     }
                    }

                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);

                    Intent myIntent = new Intent("otp");

                    myIntent.putExtra("message",message);

                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    try
                    {
                            MainActivity.recivedSms(digits );
                    }
                    catch(Exception e){}

                } // end of for loop
            }

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }
}