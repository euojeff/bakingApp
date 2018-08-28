package com.jeffersonaraujo.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeffersonaraujo.bakingapp.helper.StepJsonHelper;

import org.json.JSONException;

import butterknife.ButterKnife;
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

    private StepJsonHelper dataHelper;
    private Unbinder mUnbinder;

    private OnInstructionDetailInteractionListener mListener;

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
                dataHelper = new StepJsonHelper(getArguments().getString(ARG_JSON));
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

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
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
    }

    public interface OnInstructionDetailInteractionListener {
        void onFragmentInteraction();
    }
}
