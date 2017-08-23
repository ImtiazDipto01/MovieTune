package com.example.dipto.movietune.fragment;

/**
 * Created by Dipto on 8/22/2017.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dipto.movietune.Constant;
import com.example.dipto.movietune.EndlessRecyclerViewScrollListener;
import com.example.dipto.movietune.R;
import com.example.dipto.movietune.adapter.UpcomingAdapter;
import com.example.dipto.movietune.model.UpcomingModel;

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

public class Upcoming extends Fragment implements UpcomingAdapter.ClickListener {

    public Upcoming(){

    }

    RecyclerView upcoming_recyler;
    UpcomingAdapter upcomingAdapter ;
    List<UpcomingModel> list ;
    int page_int = 1 ;
    String upcoimg_page ;
    GridLayoutManager gridlayoutmanager ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_upcoming, container, false);
        upcoming_recyler = (RecyclerView) view.findViewById(R.id.upcoimg_recyler);
        return view ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list = new ArrayList<>() ;
        gridlayoutmanager = new GridLayoutManager(getActivity(), 2);
        upcoming_recyler.setLayoutManager(gridlayoutmanager);
        upcomingAdapter = new UpcomingAdapter(getActivity(), list) ;
        upcomingAdapter.setClickListener(this);
        upcoming_recyler.setAdapter(upcomingAdapter);

        upcoming_recyler.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridlayoutmanager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                page_int ++ ;
                upcoimg_page = Integer.toString(page_int) ;
                Log.d("++++TAG+++", "asche");
                String api_key = Constant.API_KEY ;
                ApiUpcoming apiTaskTopRated = new ApiUpcoming(getActivity()) ;
                apiTaskTopRated.execute(api_key, upcoimg_page);
            }
        });

        upcoimg_page = Integer.toString(page_int) ;
        Log.d("++++TAG+++", "asche");
        String api_key = Constant.API_KEY ;
        ApiUpcoming apiUpcoming = new ApiUpcoming(getActivity()) ;
        apiUpcoming.execute(api_key, upcoimg_page);
    }

    @Override
    public void itemClicked(View view, int position) {

    }

    public class ApiUpcoming extends AsyncTask<String, Void, String> {

        String new_release_url = "https://api.themoviedb.org/3/movie/upcoming" ;
        Context context ;
        ProgressDialog progressDialog ;

        public ApiUpcoming(Context context){
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
                    UpcomingModel upcomingModel = new UpcomingModel() ;
                    upcomingModel.setMovie_poster(movie_poster);
                    upcomingModel.setMovie_id(movie_id);
                    list.add(upcomingModel) ;
                }

                upcomingAdapter.notifyItemRangeInserted(upcomingAdapter.getItemCount(), list.size()-1);

            }
            catch (JSONException e) {
                e.printStackTrace();
                Log.d("JSONException :", String.valueOf(e)) ;
            }
        }
    }
}
