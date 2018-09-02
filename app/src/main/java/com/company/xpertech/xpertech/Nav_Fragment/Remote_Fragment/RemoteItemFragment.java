package com.company.xpertech.xpertech.Nav_Fragment.Remote_Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.company.xpertech.xpertech.R;

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

import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RemoteItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RemoteItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RemoteItemFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String[] detail_list;
    String desc;
    String img;
    View view;
    private ListView listView;
    Context ctx;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RemoteItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RemoteItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RemoteItemFragment newInstance(String param1, String param2) {
        RemoteItemFragment fragment = new RemoteItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_remote_item, container, false);
        RemoteItemFragment.RemoteDetailTask remoteDetailTask = new RemoteItemFragment.RemoteDetailTask(getContext());
        remoteDetailTask.execute("remote_detail");
        return view;
    }

    void setDetail(){
        TextView textView = (TextView) view.findViewById(R.id.remote_detail);
       GifImageView imageView = (GifImageView) view.findViewById(R.id.remote_img);
        int imgInt;
        textView.setText(desc);

        imgInt = getResources().getIdentifier(img.replaceAll("\\s+",""), "drawable", ctx.getPackageName());
        Log.d("IMG",""+imgInt);
        imageView.setImageResource(imgInt);

    }

    void display(){
        listView = (ListView) view.findViewById(R.id.remote_detail_list);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                detail_list
        );

        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RemoteItemFragment.RemoteDescTask remoteDescTask = new RemoteItemFragment.RemoteDescTask(getContext());
                remoteDescTask.execute("remote_desc", (position+1)+"");
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class RemoteDetailTask extends AsyncTask<String,Void,String> {
        Context ctx;
        AlertDialog alertDialog;

        public RemoteDetailTask(Context ctx)
        {
            this.ctx =ctx;
        }

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(ctx).create();
            alertDialog.setTitle("");
        }
        @Override
        protected String doInBackground(String... params) {
            String remote_detail_url = "https://uslsxpertech.000webhostapp.com/xpertech/remote_detail.php";
            String method = params[0];
            if(method.equals("remote_detail")){
                try {
                    URL url = new URL(remote_detail_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                    String response = "";
                    String line = "";
                    line = bufferedReader.readLine();
                    String[] title = line.split("\\$");
                    detail_list = new String[title.length];
                    detail_list = title;
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return response;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            display();
        }
    }

    public class RemoteDescTask extends AsyncTask<String,Void,String> {
        Context ctx;
        AlertDialog alertDialog;

        public RemoteDescTask(Context ctx)
        {
            this.ctx =ctx;
        }

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(ctx).create();
            alertDialog.setTitle("");
        }
        @Override
        protected String doInBackground(String... params) {
            String remote_desc_url = "https://uslsxpertech.000webhostapp.com/xpertech/remote_desc.php";
            String remote_img_url = "https://uslsxpertech.000webhostapp.com//xpertech/remote_img.php";
            String method = params[0];
            String remote_detail_id = params[1];
            if(method.equals("remote_desc")){
                try {
                    //Retreive Description
                    URL url = new URL(remote_desc_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String data = URLEncoder.encode("remote_detail_id","UTF-8")+"="+ URLEncoder.encode(remote_detail_id,"UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                    desc = bufferedReader.readLine();
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    //Retreive Image
                    url = new URL(remote_img_url);
                    httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    outputStream = httpURLConnection.getOutputStream();
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    data = URLEncoder.encode("remote_detail_id","UTF-8")+"="+ URLEncoder.encode(remote_detail_id,"UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    inputStream = httpURLConnection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                    img = bufferedReader.readLine();
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            setDetail();
        }
    }
}
