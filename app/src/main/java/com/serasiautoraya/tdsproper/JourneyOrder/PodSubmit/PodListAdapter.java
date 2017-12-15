package com.serasiautoraya.tdsproper.JourneyOrder.PodSubmit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.serasiautoraya.tdsproper.CustomView.SquareImageView;
import com.serasiautoraya.tdsproper.R;

import java.util.ArrayList;

/**
 * Created by Randi Dwi Nandra on 27/09/2017.
 * randi.dwinandra@gmail.com
 */

public class PodListAdapter extends BaseAdapter {

    private Context mContext;
    private static LayoutInflater inflater=null;
    private PodItemOnClickListener podItemOnClickListener;

    // Keep all Images in array
//    public Integer[] mThumbIds = {
//            R.drawable.add_foto_pod,
//            R.drawable.add_foto_pod,
//            R.drawable.add_foto_pod,
//            R.drawable.add_foto_pod,
//            R.drawable.add_foto_pod,
//            R.drawable.add_foto_pod,
//            R.drawable.add_foto_pod,
//            R.drawable.add_foto_pod,
//            R.drawable.add_foto_pod,
//            R.drawable.add_foto_pod,
//            R.drawable.add_foto_pod,
//            R.drawable.add_foto_pod,
//            R.drawable.add_foto_pod,
//            R.drawable.add_foto_pod
//    };

    public ArrayList<PodItemModel> podItemModels = new ArrayList<PodItemModel>();

    // Constructor
    public PodListAdapter(Context c, PodItemOnClickListener podItemOnClickListener){
        mContext = c;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.podItemOnClickListener = podItemOnClickListener;
    }

    public void addItem(PodItemModel podItemModel){
        podItemModels.add(podItemModel);
    }

    public void remove(int position){
        podItemModels.remove(position);
    }

    public ArrayList<PodItemModel> getPodItemModels() {
        return podItemModels;
    }

    @Override
    public int getCount() {
        return podItemModels.size();
    }

    @Override
    public PodItemModel getItem(int position) {
        return podItemModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int fPosition = position;
        View rowView = inflater.inflate(R.layout.single_pod_thumbnail, null);
        final SquareImageView squareImageView = (SquareImageView) rowView.findViewById(R.id.pod_iv_prove);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                podItemOnClickListener.onCapturePhoto(fPosition, squareImageView);
            }
        });

        final ImageButton imageButton = (ImageButton) rowView.findViewById(R.id.pod_ib_close);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                podItemOnClickListener.onCloseThumbnail(fPosition, imageButton);
            }
        });

        ProgressBar progressBar = (ProgressBar) rowView.findViewById(R.id.pod_pb_progress);

        podItemModels.get(position).setImageButton(imageButton);
        podItemModels.get(position).setSquareImageView(squareImageView);
        podItemModels.get(position).setAdapterIndex(position);
        podItemModels.get(position).setProgressBar(progressBar);

        if(podItemModels.get(position).getBitmap() != null){
            squareImageView.setImageBitmap(podItemModels.get(position).getBitmap());
            if(podItemModels.get(position).isCloseButtonAppear()){
                podItemModels.get(position).getImageButton().setVisibility(View.VISIBLE);
            }else{
                podItemModels.get(position).getImageButton().setVisibility(View.GONE);
            }
        }else{
            podItemModels.get(position).getImageButton().setVisibility(View.GONE);
        }
        return rowView;
    }

}
