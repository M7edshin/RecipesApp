package shahin.recipesapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import shahin.recipesapp.R;
import shahin.recipesapp.models.Recipe;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.RecipeHolder>{

    private ArrayList<Recipe> recipeArrayList;

    public RecipeRecyclerAdapter(ArrayList<Recipe> recipeArrayList){
        this.recipeArrayList = recipeArrayList;
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_recycler_item,parent,false);
        return new RecipeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
        Recipe recipe = recipeArrayList.get(position);
        holder.tv_recipe_name.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        return recipeArrayList.size();
    }


    public class RecipeHolder extends RecyclerView.ViewHolder{

        private TextView tv_recipe_name;

        public RecipeHolder(View itemView) {
            super(itemView);
            tv_recipe_name = itemView.findViewById(R.id.tv_recipe_name);
        }
    }
}