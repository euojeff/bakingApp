package com.jeffersonaraujo.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.jeffersonaraujo.bakingapp.helper.StepJsonHelper;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InstructionDetailFragment.OnInstructionDetailInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InstructionDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstructionDetailFragment extends Fragment {
    private static final String ARG_JSON = "ARG_JSON";

    private StepJsonHelper mDataHelper;
    private Unbinder mUnbinder;

    private SimpleExoPlayer player;
    private DefaultTrackSelector trackSelector;
    private BandwidthMeter bandwidthMeter;
    private DataSource.Factory mediaDataSourceFactory;

    private OnInstructionDetailInteractionListener mListener;

    @BindView(R.id.instruction_detail_tv)
    TextView instructionDetailsTV;

    @BindView(R.id.video_exoplayer_view)
    PlayerView mPlayerView;

    public InstructionDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param json Parameter 1.
     * @return A new instance of fragment InstructionDetailFragment.
     */
    public static InstructionDetailFragment newInstance(String json) {
        InstructionDetailFragment fragment = new InstructionDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_JSON, json);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            try {
                mDataHelper = new StepJsonHelper(getArguments().getString(ARG_JSON));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instruction_detail, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        instructionDetailsTV.setText(mDataHelper.getDescription());

        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(view.getContext(),
                Util.getUserAgent(view.getContext(), "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);

        if(mDataHelper.getVideoURL() != null
                && !mDataHelper.getVideoURL().trim().equals("")){
            initializePlayer(view, mDataHelper.getVideoURL());
        }else{
            mPlayerView.setVisibility(View.GONE);
        };

        return view;
    }

    @OnClick(R.id.btn_previous)
    public void clickPrevious(){
        if(mListener != null){
            mListener.onClickPrevious();
        }
    }

    @OnClick(R.id.btn_next)
    public void clickNext(){
        if(mListener != null){
            mListener.onClickNext();
        }
    }

    /**
     *
     *
     * @param view
     * @param url
     */
    private void initializePlayer(View view, String url) {

        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);

        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(view.getContext(), trackSelector);

        mPlayerView.setPlayer(player);

        player.setPlayWhenReady(true);


        MediaSource mediaSource = new ExtractorMediaSource.Factory(mediaDataSourceFactory)
                .createMediaSource(Uri.parse(url));

        player.prepare(mediaSource, true, false);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInstructionDetailInteractionListener) {
            mListener = (OnInstructionDetailInteractionListener) context;
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
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        if(player != null){
            player.release();
        }
    }

    public interface OnInstructionDetailInteractionListener {
        void onClickPrevious();
        void onClickNext();
    }
}
