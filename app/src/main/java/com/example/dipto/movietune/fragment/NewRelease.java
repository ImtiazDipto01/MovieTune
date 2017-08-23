package com.example.dipto.movietune.fragment;

/**
 * Created by Dipto on 8/22/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dipto.movietune.Constant;
import com.example.dipto.movietune.EndlessRecyclerViewScrollListener;
import com.example.dipto.movietune.R;
import com.example.dipto.movietune.activity.MovieDetailsActivity;
import com.example.dipto.movietune.adapter.NewReleaseAdapter;
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

import retrofit2.Call;

public class NewRelease extends Fragment implements NewReleaseAdapter.ClickListener {

    public NewRelease(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    RecyclerView new_release_recyler;
    NewReleaseAdapter newReleaseAdapter ;
    List<NewReleaseModel> list ;
    int page_int = 1, network_flag = 0 ;
    String new_release_page ;
    GridLayoutManager gridlayoutmanager ;
    AlertDialog.Builder internet_connection_failed ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_new_release, container, false);
        new_release_recyler = (RecyclerView) view.findViewById(R.id.new_release_recyler);
        Constant constant = new Constant(getActivity()) ;
        network_flag = constant.isNetworkActive() ;
        /*if(network_flag == 0){
            internetConnectFailedMessage();
            AlertDialog alertDialog = internet_connection_failed.create() ;
            alertDialog.show();
        }*/
        return view ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list = new ArrayList<>() ;
        Constant constant = new Constant(getActivity()) ;
        network_flag = constant.isNetworkActive() ;

        gridlayoutmanager = new GridLayoutManager(getActivity(), 2);
        new_release_recyler.setLayoutManager(gridlayoutmanager);
        newReleaseAdapter = new NewReleaseAdapter(getActivity(), list) ;
        newReleaseAdapter.setClickListener(this);
        new_release_recyler.setAdapter(newReleaseAdapter);

        new_release_recyler.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridlayoutmanager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                page_int ++ ;
                new_release_page = Integer.toString(page_int) ;
                Log.d("++++TAG+++", "asche");
                String api_key = Constant.API_KEY ;
                ApiTaskNewRelease apiTaskNewRelease = new ApiTaskNewRelease(getActivity()) ;
                apiTaskNewRelease.execute(api_key, new_release_page);
            }
        });

        if(network_flag == 1)
        {
            new_release_page = Integer.toString(page_int) ;
            Log.d("++++TAG+++", "asche");
            String api_key = Constant.API_KEY ;
            ApiTaskNewRelease apiTaskNewRelease = new ApiTaskNewRelease(getActivity()) ;
            apiTaskNewRelease.execute(api_key, new_release_page);
        }
        else{

            internetConnectFailedMessage();
            AlertDialog alertDialog = internet_connection_failed.create() ;
            alertDialog.show();
        }
    }

    @Override
    public void itemClicked(View view, int position) {
        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class) ;
        NewReleaseModel newReleaseModel = list.get(position) ;
        intent.putExtra("id", newReleaseModel.getMovie_id()) ;
        startActivity(intent);
    }

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
                    String movie_id = new_movie_obj.getString("id") ;
                    NewReleaseModel newReleaseModel = new NewReleaseModel() ;
                    newReleaseModel.setMovie_poster(movie_poster);
                    newReleaseModel.setMovie_id(movie_id);
                    list.add(newReleaseModel) ;
                }

                newReleaseAdapter.notifyItemRangeInserted(newReleaseAdapter.getItemCount(), list.size()-1);

            }
            catch (JSONException e) {
                e.printStackTrace();
                Log.d("JSONException :", String.valueOf(e)) ;
            }
        }
    }

    private void internetConnectFailedMessage(){
        internet_connection_failed = new AlertDialog.Builder(getActivity());
        internet_connection_failed.setTitle("Warning!") ;
        internet_connection_failed.setMessage("Please Check Your Internet Connection") ;
        internet_connection_failed.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }
}
