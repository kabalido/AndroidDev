package com.example.everaldo.hackssms;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver implements  Callback{

    final SmsManager sms = SmsManager.getDefault();
    private String originNumber;

    public void onReceive(Context context, Intent intent) {

        RemoteCommand command = null;
        // Retrieves a map of extended data from the intent.
        Bundle bundle = intent.getExtras();
        String smsMessage;
        String commandOption = null;
        try {
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                smsMessage = this.getReceivedSms(pdusObj);
                Toast toast = Toast.makeText(context, "Sender: " + this.originNumber + ", SMS Message: " + smsMessage, Toast.LENGTH_LONG);
                toast.show();

                if (smsMessage.equalsIgnoreCase("desligar") || smsMessage.equalsIgnoreCase("reiniciar")){
                    if (smsMessage.equalsIgnoreCase("desligar"))
                        commandOption = "-h";
                    else
                        commandOption = "-r";
                    command =  new RemoteCommand(context, "sudo shutdown " + commandOption + " now");
                    command.setCallbackFunction(this);
                    command.execute();
                }
            }
        }
        catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver: " + e);
        }
    }

    public String getReceivedSms(final Object[] pdus){

        StringBuilder sb = new StringBuilder(40);
        SmsMessage message;
        for(Object pdu : pdus){
            message = SmsMessage.createFromPdu((byte[]) pdu);
            sb.append(message.getDisplayMessageBody());
            this.originNumber = message.getDisplayOriginatingAddress();
        }
        return sb.toString();
    }

    public void done(boolean s){

        String phoneNo = this.originNumber;
        try {
            SmsManager smsManager = SmsManager.getDefault();
            if(s)
                smsManager.sendTextMessage(phoneNo, null, "Shutdown performed successfully", null, null);
            else
                smsManager.sendTextMessage(phoneNo, null, "Shutdown FAILED", null, null);
            Log.d("==EVERALDO", "Result: " + s);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
