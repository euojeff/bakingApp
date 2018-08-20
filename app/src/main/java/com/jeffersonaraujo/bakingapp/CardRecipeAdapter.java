package com.jeffersonaraujo.bakingapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeffersonaraujo.bakingapp.helper.RecipeHelper;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jeff on 15/08/2018.
 */
class CardRecipeAdapter extends RecyclerView.Adapter <CardRecipeAdapter.CardRecipeHolder> {

    static class CardRecipeHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_name)
        TextView mTextView;
        @BindView(R.id.card_filme)
        CardView mCard;

        public CardRecipeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface RecipeOnclickHandler {
        void onCardClick(String jsonFilme);
    }

    private ArrayList<String> recipesList;
    private RecipeOnclickHandler mHandler;

    public void setRecipesList(ArrayList<String> recipesList) {
        this.recipesList = recipesList;
    }

    public CardRecipeAdapter(RecipeOnclickHandler handler, Context context){
        this.mHandler = handler;
        this.recipesList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    @Override
    public CardRecipeAdapter.CardRecipeHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.card_recipe, viewGroup, false);

        return new CardRecipeAdapter.CardRecipeHolder(view);
    }

    @Override
    public void onBindViewHolder(CardRecipeAdapter.CardRecipeHolder holder, final int i) {

        try {

            RecipeHelper recipe = new RecipeHelper(recipesList.get(i));

            holder.mCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHandler.onCardClick(recipesList.get(i));
                }
            });

            holder.mTextView.setText(recipe.getName());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}