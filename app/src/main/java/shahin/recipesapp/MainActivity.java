package shahin.recipesapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shahin.recipesapp.Utilities.RecyclerViewTouchListener;
import shahin.recipesapp.adapters.RecipeRecyclerAdapter;
import shahin.recipesapp.models.Ingredient;
import shahin.recipesapp.models.Recipe;
import shahin.recipesapp.models.Step;
import shahin.recipesapp.network.RetrofitApiClient;
import shahin.recipesapp.network.ApiRetrofitInterface;

import static shahin.recipesapp.Utilities.Constants.INGREDIENT_INTENT_KEY;
import static shahin.recipesapp.Utilities.Constants.STEPS_INTENT_KEY;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_recipes) RecyclerView rv_recipes;

    private RecipeRecyclerAdapter recipeRecyclerAdapter;
    private ArrayList<Recipe> recipesArrayList;
    private ArrayList<Ingredient> ingredientArrayList;
    private ArrayList<Step> stepArrayList;

    private ApiRetrofitInterface apiRetrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_recipes.setLayoutManager(layoutManager);
        recipesArrayList = new ArrayList<>();

        loadRecipes();

        rv_recipes.addOnItemTouchListener(new RecyclerViewTouchListener(this, rv_recipes, new RecyclerViewTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Recipe recipe = recipesArrayList.get(position);
                ingredientArrayList = recipe.getIngredients();
                stepArrayList = recipe.getSteps();
                Context context = getApplicationContext();
                Intent intent = new Intent(context, StepsActivity.class);
                intent.putParcelableArrayListExtra(INGREDIENT_INTENT_KEY, ingredientArrayList);
                intent.putParcelableArrayListExtra(STEPS_INTENT_KEY, stepArrayList);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void loadRecipes(){

        apiRetrofitInterface = RetrofitApiClient.getClient().create(ApiRetrofitInterface.class);

        Call<ArrayList<Recipe>> call = apiRetrofitInterface.getRecipe();

        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                recipesArrayList = response.body();
                recipeRecyclerAdapter = new RecipeRecyclerAdapter(recipesArrayList);
                rv_recipes.setAdapter(recipeRecyclerAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.failed_message, Toast.LENGTH_SHORT).show();
            }
        });

    }

}