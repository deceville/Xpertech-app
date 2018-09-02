package com.company.xpertech.xpertech.Nav_Fragment.Troubleshoot_Fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.xpertech.xpertech.Method.Troubleshoot;
import com.company.xpertech.xpertech.R;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link TroubleshootFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTroubleshootRecyclerViewAdapter extends RecyclerView.Adapter<MyTroubleshootRecyclerViewAdapter.ViewHolder>
        implements TroubleeshootItemFragment.OnFragmentInteractionListener {

    private List<Troubleshoot> mValues;
    private final TroubleshootFragment.OnListFragmentInteractionListener mListener;

    Context ctx;
    String steps = "Steps: \n";
    Drawable gif = null;
    FragmentActivity actvty;
    View tru_view;

    public MyTroubleshootRecyclerViewAdapter(List<Troubleshoot> items, TroubleshootFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx = parent.getContext())
                .inflate(R.layout.fragment_troubleshoot, parent, false);
        this.tru_view = view;
        return new ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        /*holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);*/
        holder.mContentView.setText(mValues.get(position).getTitle());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                actvty = (FragmentActivity) ctx;
                Bundle bundle = new Bundle();
                String line = null;
                bundle.putString("BOX_NUMBER_SESSION", TroubleshootFragment.BOX_NUMBER_SESSION);
                bundle.putInt("position",position);
                IntroFragment introf = new IntroFragment();
                introf.setArguments(bundle);
                actvty.getSupportFragmentManager().beginTransaction().replace(R.id.content_main, introf).commit();

                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Troubleshoot mItem;
        public GifImageView gifImageView;

//        ArrayList<Troubleshoot> tItem = new ArrayList<Troubleshoot>();
//        Context ctx;

        public ViewHolder(View view) {
            super(view);
//            this.tItem = tItem;
//            this.ctx = ctx;
            mView = view;
            gifImageView = (GifImageView) view.findViewById(R.id.gif_imageView);
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public void filterList(ArrayList<Troubleshoot> list){
        mValues = (List)list;
        notifyDataSetChanged();

    }
}
