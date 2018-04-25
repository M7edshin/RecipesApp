package shahin.recipesapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import shahin.recipesapp.R;
import shahin.recipesapp.models.Recipe;
import shahin.recipesapp.models.Step;

import static shahin.recipesapp.utilities.Constants.BROWNIES_IMAGE_URL;
import static shahin.recipesapp.utilities.Constants.CHEESECAKE_IMAGE_URL;
import static shahin.recipesapp.utilities.Constants.NUTELLA_IMAGE_URL;
import static shahin.recipesapp.utilities.Constants.YELLOWCAKE_IMAGE_URL;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.RecipeHolder>{

    private ArrayList<Recipe> recipeArrayList;

    private String url;

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

        Context context = holder.iv_recipe.getContext();

        switch (recipe.getName()){
            case "Nutella Pie":
                url = NUTELLA_IMAGE_URL;
                Glide.with(context)
                        .load(url)
                        .error(R.mipmap.ic_recipe)
                        .into(holder.iv_recipe);
                break;

            case "Brownies":
                url = BROWNIES_IMAGE_URL;
                Glide.with(context)
                        .load(url)
                        .error(R.mipmap.ic_recipe)
                        .into(holder.iv_recipe);
                break;

            case "Yellow Cake":
                url = YELLOWCAKE_IMAGE_URL;
                Glide.with(context)
                        .load(url)
                        .error(R.mipmap.ic_recipe)
                        .into(holder.iv_recipe);
                break;

            case "Cheesecake":
                url = CHEESECAKE_IMAGE_URL;
                Glide.with(context)
                        .load(url)
                        .error(R.mipmap.ic_recipe)
                        .into(holder.iv_recipe);
                break;

                default:
                    holder.iv_recipe.setImageResource(R.mipmap.ic_recipe);
        }


    }

    @Override
    public int getItemCount() {
        return recipeArrayList.size();
    }


    public class RecipeHolder extends RecyclerView.ViewHolder{

        private TextView tv_recipe_name;
        private ImageView iv_recipe;

        public RecipeHolder(View itemView) {
            super(itemView);
            tv_recipe_name = itemView.findViewById(R.id.tv_recipe_name);
            iv_recipe = itemView.findViewById(R.id.iv_recipe);
        }
    }
}