package com.example.dipto.movietune.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dipto.movietune.EndlessRecyclerViewScrollListener;
import com.example.dipto.movietune.R;
import com.example.dipto.movietune.adapter.NewReleaseAdapter;
import com.example.dipto.movietune.adapter.TestPaginationAdapter;
import com.example.dipto.movietune.model.NewReleaseModel;
import com.example.dipto.movietune.model.TestPaginationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dipto on 8/23/2017.
 */

public class TestPagination extends Fragment {
    public TestPagination(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    RecyclerView test_recyler;
    TestPaginationAdapter testPaginationAdapter ;
    List<TestPaginationModel> list ;
    LinearLayoutManager layoutManager ;
    GridLayoutManager gridLayoutManager ;
    String api_key = "c37d3b40004717511adb2c1fbb15eda4" ;
    int page_int = 1 ;
    String new_release_page ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_pagination, container, false);
        test_recyler = (RecyclerView) view.findViewById(R.id.test_recyler);
        return view ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("++++TAG+++", "activity asche");
        list = new ArrayList<>() ;
        //layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        //testPaginationAdapter = new TestPaginationAdapter(getActivity(), getData()) ;
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        test_recyler.setLayoutManager(gridLayoutManager);
        testPaginationAdapter = new TestPaginationAdapter(getActivity(), list) ;
        test_recyler.setAdapter(testPaginationAdapter);

        new_release_page = Integer.toString(page_int) ;
        Log.d("++++TAG+++", "asche");
        ApiTaskNewRelease apiTaskNewRelease = new ApiTaskNewRelease(getActivity()) ;
        apiTaskNewRelease.execute(api_key, new_release_page);

        /**/

        test_recyler.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                page_int ++ ;
                new_release_page = Integer.toString(page_int) ;
                Log.d("++++TAG+++", "asche");
                ApiTaskNewRelease apiTaskNewRelease = new ApiTaskNewRelease(getActivity()) ;
                apiTaskNewRelease.execute(api_key, new_release_page);
            }
        });

    }

    /*private List<TestPaginationModel> getData(){

        String[] text_array = {"my text", "my Text", "my Text", "my Text", "my Text", "my Text"} ;
        List<TestPaginationModel> list = new ArrayList<>() ;

        for(int i = 0 ; i < text_array.length ; i++){

            TestPaginationModel testPaginationModel = new TestPaginationModel() ;
            testPaginationModel.setPagination(text_array[i]);
            list.add(testPaginationModel) ;
        }
        return  list ;
    }*/

    public class ApiTaskNewRelease extends AsyncTask<String, Void, String> {

        String new_release_url = "https://api.themoviedb.org/3/movie/now_playing" ;
        Context context ;
        ProgressDialog progressDialog ;

        public ApiTaskNewRelease(Context context){
            this.context = context ;
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            progressDialog = new ProgressDialog(context) ;
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Loading Please Wait...");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(new_release_url) ;
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection() ;
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);


                OutputStream outputStream = httpURLConnection.getOutputStream() ;
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8")) ;

                String api_key = params[0] ;
                String page = params[1] ;

                String data = URLEncoder.encode("api_key", "UTF-8")+"="+URLEncoder.encode(api_key, "UTF-8")+"&"+
                        URLEncoder.encode("page", "UTF-8")+"="+URLEncoder.encode(page, "UTF-8") ;

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream() ;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)) ;
                StringBuilder stringBuilder = new StringBuilder() ;
                String line = "" ;

                while((line = bufferedReader.readLine()) != null){

                    stringBuilder.append(line + "\n") ;
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim() ;
            }
            catch (MalformedURLException e) {
                Log.d("MalformedURLException :", String.valueOf(e)) ;
            }
            catch (IOException e) {
                Log.d("IOException :", String.valueOf(e)) ;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String json) {
            try {
                progressDialog.dismiss();
                JSONObject jsonObject = new JSONObject(json) ;
                JSONArray main_array = jsonObject.getJSONArray("results") ;

                for(int i = 0 ; i < main_array.length() ; i++){

                    JSONObject new_movie_obj = main_array.getJSONObject(i) ;
                    String movie_poster = new_movie_obj.getString("poster_path") ;
                    TestPaginationModel testPaginationmodel = new TestPaginationModel() ;
                    testPaginationmodel.setPagination(movie_poster);
                    list.add(testPaginationmodel) ;
                }

                /*testPaginationAdapter = new TestPaginationAdapter(context, list) ;
                test_recyler.setAdapter(testPaginationAdapter);*/
                testPaginationAdapter.notifyItemRangeInserted(testPaginationAdapter.getItemCount(), list.size()-1);

            }
            catch (JSONException e) {
                e.printStackTrace();
                Log.d("JSONException :", String.valueOf(e)) ;
            }
        }
    }
}
