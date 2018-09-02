package com.company.xpertech.xpertech.Nav_Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.company.xpertech.xpertech.Method.Troubleshoot;
import com.company.xpertech.xpertech.Nav_Fragment.Channel_Packages_Fragment.PackagesFragment;
import com.company.xpertech.xpertech.Nav_Fragment.Manual_Fragment.ManualListFragment;
import com.company.xpertech.xpertech.Nav_Fragment.Remote_Fragment.RemoteListFragment;
import com.company.xpertech.xpertech.Nav_Fragment.Self_Install_Fragment.SelfInstallFragment;
import com.company.xpertech.xpertech.Nav_Fragment.Troubleshoot_Fragment.TroubleshootFragment;
import com.company.xpertech.xpertech.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements TroubleshootFragment.OnListFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn_troubleshoot = (Button) view.findViewById(R.id.btn_troubleshoot);
        btn_troubleshoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle BUNDLE_SESSION = getArguments();
//                String BOX_NUMBER_SESSION = BUNDLE_SESSION.getString("BOX_NUMBER_SESSION");
                TroubleshootFragment tf = new TroubleshootFragment();

                tf.setArguments(BUNDLE_SESSION);
                /*Bundle bundle = getArguments();
                String boxNumber = bundle.getString("boxNumber");
                TroubleshootFragment fragment = new TroubleshootFragment();
                bundle.putString("boxNumber", boxNumber);
                fragment.setArguments(bundle);*/
                getFragmentManager().beginTransaction().replace(R.id.content_main, tf).addToBackStack("tag").commit();
            }
        });

        Button btn_package = (Button) view.findViewById(R.id.btn_package);
        btn_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content_main, new PackagesFragment()).addToBackStack("tag").commit();
            }
        });

        Button btn_selfinstall = (Button) view.findViewById(R.id.btn_selfinstall);
        btn_selfinstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content_main, new SelfInstallFragment()).addToBackStack("tag").commit();
            }
        });

        Button btn_manual = (Button) view.findViewById(R.id.btn_manual);
        btn_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content_main, new ManualListFragment()).addToBackStack("tag").commit();
            }
        });

        Button btn_remote = (Button) view.findViewById(R.id.btn_remote);
        btn_remote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content_main, new RemoteListFragment()).addToBackStack("tag").commit();
            }
        });

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        // Inflate the layout for this fragment
        getActivity().setTitle("Home");
        return inflater.inflate(R.layout.fragment_home, container, false);
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

    @Override
    public void onListFragmentInteraction(Troubleshoot item) {

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
}
