package com.jeffersonaraujo.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeffersonaraujo.bakingapp.helper.IngredientJsonHelper;
import com.jeffersonaraujo.bakingapp.helper.RecipeJsonHelper;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InstructionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InstructionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstructionsFragment extends Fragment {

    private static final String ARG_JSON = "ARG_JSON";

    private RecipeJsonHelper mDataHelper;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.instruction_tv)
    public TextView mInstructionsTv;

    public InstructionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recipeJson json representation of a Recipe
     * @return A new instance of fragment InstructionsFragment.
     */
    public static InstructionsFragment newInstance(String recipeJson) {
        InstructionsFragment fragment = new InstructionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_JSON, recipeJson);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            try {
                mDataHelper = new RecipeJsonHelper(getArguments().getString(ARG_JSON));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void populateUi(){

        for(IngredientJsonHelper ingredient: mDataHelper.getIngredients()){
            mInstructionsTv.setText( mInstructionsTv.getText() + ingredient.getFormatedString() + "\n");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);

        ButterKnife.bind(this, view);

        populateUi();

        return view;
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
}
