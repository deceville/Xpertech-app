package com.company.xpertech.xpertech.Nav_Fragment.Channel_Packages_Fragment;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.xpertech.xpertech.Method.Channels;
import com.company.xpertech.xpertech.R;

import java.util.List;

/**
 * Created by Skylar Gail on 8/15/2018.
 */

public class ChannelRecyclerView extends RecyclerView.Adapter<ChannelRecyclerView.ViewHolder> implements ChannelFragment.OnFragmentInteractionListener {

    private List<Channels> mChannels;
    private ChannelFragment.OnFragmentInteractionListener mListener;
    private Context ctx;

    public ChannelRecyclerView(List<Channels> mChannels, ChannelFragment.OnFragmentInteractionListener mListener) {
        this.mChannels =   mChannels;
        this.mListener =  mListener;
    }

    @Override
    public ChannelRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx = parent.getContext())
                .inflate(R.layout.fragment_channel, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChannelRecyclerView.ViewHolder holder, int position) {
        holder.mTitleView.setText(mChannels.get(position).getTitle());
        //holder.mChannelNumberView.setText(mChannels.get(position).getChannelNumber());
    }

    @Override
    public int getItemCount() {
        return mChannels.size();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mChannelNumberView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mTitleView = (TextView) itemView.findViewById(R.id.channel_title);
            mChannelNumberView = (TextView) itemView.findViewById(R.id.channel_num);
        }
    }
}
