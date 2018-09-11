package com.jeffersonaraujo.bakingapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeffersonaraujo.bakingapp.helper.RecipeJsonHelper;
import com.jeffersonaraujo.bakingapp.helper.StepJsonHelper;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jeff on 21/08/2018.
 */
class CardInstructionsAdapter extends RecyclerView.Adapter <CardInstructionsAdapter.CardInstructionHolder> {

    private ArrayList<StepJsonHelper> mStepsList;
    private RecipeOnclickHandler mHandler;

    static class CardInstructionHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.instruction_card_name)
        TextView mTextView;
        @BindView(R.id.card_instruction)
        CardView mCard;

        public CardInstructionHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface RecipeOnclickHandler {
        void onCardClick(int index);
    }

    public void setRecipesList(ArrayList<StepJsonHelper> mStepsList) {
        this.mStepsList = mStepsList;
    }

    public CardInstructionsAdapter(RecipeOnclickHandler handler){
        this.mHandler = handler;
        this.mStepsList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return mStepsList.size();
    }

    @Override
    public CardInstructionsAdapter.CardInstructionHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.card_instruction, viewGroup, false);

        return new CardInstructionsAdapter.CardInstructionHolder(view);
    }

    @Override
    public void onBindViewHolder(CardInstructionsAdapter.CardInstructionHolder holder, final int i) {

        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.onCardClick(i);
            }
        });

        holder.mTextView.setText(mStepsList.get(i).getShortDescription());
    }
}