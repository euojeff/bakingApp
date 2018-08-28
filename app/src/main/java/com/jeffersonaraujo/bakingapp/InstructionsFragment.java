package com.jeffersonaraujo.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeffersonaraujo.bakingapp.helper.IngredientJsonHelper;
import com.jeffersonaraujo.bakingapp.helper.RecipeJsonHelper;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnInstructionInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InstructionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstructionsFragment extends Fragment implements CardInstructionsAdapter.RecipeOnclickHandler {

    private static final String ARG_JSON = "ARG_JSON";

    private RecipeJsonHelper mDataHelper;

    private OnInstructionInteractionListener mListener;

    private Unbinder mUnbinder;

    @BindView(R.id.ingredients_instruction_tv)
    public TextView mIngredientsTv;
    @BindView(R.id.instructions_recycle)
    public RecyclerView mInstructionsRV;

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
            mIngredientsTv.setText( mIngredientsTv.getText() + ingredient.getFormatedString() + "\n");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        populateUi();
        configRecycler();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void configRecycler(){

        LinearLayoutManager lm = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        mInstructionsRV.setLayoutManager(lm);

        CardInstructionsAdapter mAdapter = new CardInstructionsAdapter(this);
        mInstructionsRV.setAdapter(mAdapter);

        mAdapter.setRecipesList(mDataHelper.getSteps());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInstructionInteractionListener) {
            mListener = (OnInstructionInteractionListener) context;
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
    public void onCardClick(String json) {
        if (mListener != null) {
            mListener.onInstructionSelected(json);
        }
    }

    public interface OnInstructionInteractionListener {
        void onInstructionSelected(String json);
    }
}
