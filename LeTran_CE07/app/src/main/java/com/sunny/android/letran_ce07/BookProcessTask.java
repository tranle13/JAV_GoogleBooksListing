
    // Name: Tran Le
    // JAV1 - 1808
    // File name: BookProcessTask.java

package com.sunny.android.letran_ce07;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class BookProcessTask extends AsyncTask<Void, Void, ArrayList<Book>> {

    // Member variables
    final private Context hostContext;
    final private OnFinished finishedInterface;
    private ProgressDialog progress = null;

    // Create an interface
    interface OnFinished {
        void onFinished(ArrayList<Book> library);
    }

    BookProcessTask(Context hostContext, OnFinished finishedInterface) {
        this.hostContext = hostContext;
        this.finishedInterface = finishedInterface;
    }

    // Code to show ProgressDialog before the tasks starts
    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(hostContext);
        progress.setMessage("Wait while books are being downloaded...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
    }

    @Override
    protected ArrayList<Book> doInBackground(Void... voids) {
        URL url = null;
        HttpURLConnection connection = null;
        InputStream stream = null;

        ArrayList<Book> library = new ArrayList<>();

        // Open connection
        try {
            String webAddress = "https://www.googleapis.com/books/v1/volumes?q=android;";
            url = new URL(webAddress);
            connection = (HttpURLConnection)url.openConnection();
            connection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Open stream and close after done getting data
        try {
            stream = connection.getInputStream();
            String result = IOUtils.toString(stream, "UTF-8");

            JSONObject outerObj = new JSONObject(result);
            JSONArray array = outerObj.getJSONArray("items");
            for (int i = 0; i < array.length(); i++) {
                JSONObject innerObj = array.getJSONObject(i);
                JSONObject volumeObj = innerObj.getJSONObject("volumeInfo");
                JSONObject imageObj = volumeObj.getJSONObject("imageLinks");
                library.add(new Book(imageObj.getString("thumbnail"), volumeObj.getString("title")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                connection.disconnect();
                IOUtils.close(connection);
            }
        }

        return library;
    }

    // Dismiss ProgressDialog and populate data when task finishes
    @Override
    protected void onPostExecute(ArrayList<Book> books) {
        if (finishedInterface != null) {
            if (progress != null) {
                progress.dismiss();
            }
            finishedInterface.onFinished(books);
        }
    }
}
