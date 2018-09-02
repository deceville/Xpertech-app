package com.company.xpertech.xpertech.Nav_Fragment.StatisticsFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.xpertech.xpertech.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Statistics_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Statistics_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Statistics_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    int loginPCnt = 0;
    int loginFCnt = 0;
    int callPCnt = 0;
    int callFCnt = 0;
    int troublePCnt = 0;
    int troubleFCnt = 0;
    View view;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Statistics_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Statistics_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Statistics_Fragment newInstance(String param1, String param2) {
        Statistics_Fragment fragment = new Statistics_Fragment();
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

        getActivity().setTitle("Statistics");


    }

    void call(){
        PieChart mChart;
        String[] xValues = {"Called", "Declined"};

        mChart = (PieChart)view.findViewById(R.id.call_piechart);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(callPCnt, 0));
        entries.add(new Entry(callFCnt, 0));

        PieDataSet dataSet =  new PieDataSet(entries, "");
        PieData data = new PieData(xValues, dataSet);
        dataSet.setColors(new int[]{Color.BLUE, Color.RED});
        dataSet.setSliceSpace(5f);
        dataSet.setValueTextSize(15f);
        mChart.setUsePercentValues(true);
        mChart.setDrawHoleEnabled(false);
        mChart.setData(data);
        mChart.invalidate();
    }

    void login(){
        PieChart mChart;
        String[] xValues = {"Successfully", "Failed"};

        mChart = (PieChart)view.findViewById(R.id.login_piechart);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(loginPCnt, 0));
        entries.add(new Entry(loginFCnt, 0));

        PieDataSet dataSet =  new PieDataSet(entries, "");
        PieData data = new PieData(xValues, dataSet);
        dataSet.setColors(new int[]{Color.YELLOW, Color.RED});
        dataSet.setSliceSpace(5f);
        dataSet.setValueTextSize(15f);
        mChart.setUsePercentValues(true);
        mChart.setDrawHoleEnabled(false);
        mChart.setData(data);
        mChart.invalidate();
    }

    void trouble(){
        PieChart mChart;
        String[] xValues = {"Fixed", "Failed"};

        mChart = (PieChart)view.findViewById(R.id.troubleshoot_piechart);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(troublePCnt, 0));
        entries.add(new Entry(troubleFCnt, 0));

        PieDataSet dataSet =  new PieDataSet(entries, "");
        PieData data = new PieData(xValues, dataSet);
        dataSet.setColors(new int[]{Color.GREEN, Color.RED});
        dataSet.setSliceSpace(5f);
        dataSet.setValueTextSize(15f);
        mChart.setUsePercentValues(true);
        mChart.setDrawHoleEnabled(false);
        mChart.setData(data);
        mChart.invalidate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_statistics_, container, false);

        BackgroundTask loginPass = new BackgroundTask(getContext());
        loginPass.execute("cnt");
//        loginPCnt =340;
//        loginFCnt = 120;
//        login();

        return view;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class BackgroundTask extends AsyncTask<String, Void, String> {
        AlertDialog alertDialog;
        Context ctx;
        public String boxNumber = null;

        public BackgroundTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(ctx).create();
            alertDialog.setTitle("Login Information....");
        }

        @Override
        protected String doInBackground(String... params) {
            String login_url = "https://uslsxpertech.000webhostapp.com/xpertech/count.php";
            String method = params[0];
            if (method.equals("cnt")) {
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode("pass", "UTF-8");
                    data += "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("login", "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        line = line.replaceAll("\\s+", "");
                        loginPCnt = Integer.parseInt(line);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    url = new URL(login_url);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    outputStream = httpURLConnection.getOutputStream();
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    data = URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode("fail", "UTF-8");
                    data += "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("login", "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    inputStream = httpURLConnection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    while ((line = bufferedReader.readLine()) != null) {
                        line = line.replaceAll("\\s+", "");
                        loginFCnt = Integer.parseInt(line);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    url = new URL(login_url);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    outputStream = httpURLConnection.getOutputStream();
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    data = URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode("pass", "UTF-8");
                    data += "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("call", "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    inputStream = httpURLConnection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    while ((line = bufferedReader.readLine()) != null) {
                        line = line.replaceAll("\\s+", "");
                        callPCnt = Integer.parseInt(line);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    url = new URL(login_url);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    outputStream = httpURLConnection.getOutputStream();
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    data = URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode("fail", "UTF-8");
                    data += "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("call", "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    inputStream = httpURLConnection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    while ((line = bufferedReader.readLine()) != null) {
                        line = line.replaceAll("\\s+", "");
                        callFCnt = Integer.parseInt(line);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    url = new URL(login_url);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    outputStream = httpURLConnection.getOutputStream();
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    data = URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode("pass", "UTF-8");
                    data += "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("troubleshoot", "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    inputStream = httpURLConnection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    while ((line = bufferedReader.readLine()) != null) {
                        line = line.replaceAll("\\s+", "");
                        Log.d("LOG", line);
                        troublePCnt = Integer.parseInt(line);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    url = new URL(login_url);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    outputStream = httpURLConnection.getOutputStream();
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    data = URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode("fail", "UTF-8");
                    data += "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("troubleshoot", "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    inputStream = httpURLConnection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    while ((line = bufferedReader.readLine()) != null) {
                        line = line.replaceAll("\\s+", "");
                        Log.d("LOG", line);
                        troubleFCnt = Integer.parseInt(line);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d("STAT", e.toString());
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
            login();
            call();
            trouble();
        }
    }

}