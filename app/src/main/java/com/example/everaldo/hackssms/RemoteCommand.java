package com.example.everaldo.hackssms;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by everaldo on 6/24/15.
 */
public class RemoteCommand extends AsyncTask<Void, Void, Boolean>{
    private String command;
    private Callback cb;
    private Preference prefs;

    public RemoteCommand(Context context, String command){

        prefs = new Preference(context);
        this.command = command;
    }

    public void setCallbackFunction(Callback cb){
        this.cb = cb;
    }


    @Override
    protected void onPostExecute(Boolean s) {
        this.cb.done(s);
        super.onPostExecute(s);
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        boolean exitFlag = true;
        String url = this.prefs.getUrl();
        ArrayList<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("command", this.command));

        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 50000);
        HttpConnectionParams.setSoTimeout(httpParams, 20000);
        HttpClient client = new DefaultHttpClient(httpParams);
        HttpPost post = new HttpPost(url);
        try {
            post.setEntity(new UrlEncodedFormEntity(data));
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            JSONObject json = new JSONObject(result);
            if (json == null || json.length() == 0)
                return false;
            else{
                if (json.getInt("status") != 0 )
                    exitFlag = false;
            }
        }
        catch (Exception e) {
            Log.d("==EVERALDO==", "Exception on RemoteCommand method: " + e);
            exitFlag = false;
        }
        return exitFlag;
    }

    @Override
    public String toString() {
        return "RemoteCommand{" +
                "command='" + command + '\'' +
                '}';
    }
}
