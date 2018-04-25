package shahin.recipesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shahin.recipesapp.adapters.RecipeRecyclerAdapter;
import shahin.recipesapp.models.Ingredient;
import shahin.recipesapp.models.Recipe;
import shahin.recipesapp.models.Step;
import shahin.recipesapp.network.ApiRetrofitInterface;
import shahin.recipesapp.network.RetrofitApiClient;
import shahin.recipesapp.utilities.ColumnsFitting;
import shahin.recipesapp.utilities.RecyclerViewTouchListener;

import static shahin.recipesapp.utilities.Constants.GRID_LAYOUT_MANAGER_STATE_KEY;
import static shahin.recipesapp.utilities.Constants.INGREDIENTS_INTENT_KEY;
import static shahin.recipesapp.utilities.Constants.LINEAR_LAYOUT_MANAGER_STATE_KEY;
import static shahin.recipesapp.utilities.Constants.STEPS_INTENT_KEY;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_recipes) RecyclerView rv_recipes;
    @BindView(R.id.pb_loading) ProgressBar pb_loading;

    private RecipeRecyclerAdapter recipeRecyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    private ArrayList<Recipe> recipesArrayList;
    private ArrayList<Ingredient> ingredientArrayList;
    private ArrayList<Step> stepArrayList;

    private Parcelable linearLayoutManagerState;
    private Parcelable gridLayoutManagerState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pb_loading.setVisibility(View.VISIBLE);

        if (findViewById(R.id.relative_layout_tablet_mode) != null) {

            if (savedInstanceState != null) {
                gridLayoutManagerState = savedInstanceState.getParcelable(LINEAR_LAYOUT_MANAGER_STATE_KEY);
            }

            int numberOfColumns = ColumnsFitting.calculateNoOfColumns(this);
            gridLayoutManager = new GridLayoutManager(getApplicationContext(), numberOfColumns);
            rv_recipes.setLayoutManager(gridLayoutManager);

        } else {

            if (savedInstanceState != null) {
                linearLayoutManagerState = savedInstanceState.getParcelable(LINEAR_LAYOUT_MANAGER_STATE_KEY);
            }

            linearLayoutManager = new LinearLayoutManager(this);
            rv_recipes.setLayoutManager(linearLayoutManager);
        }

        recipesArrayList = new ArrayList<>();

        loadRecipes();

        rv_recipes.addOnItemTouchListener(new RecyclerViewTouchListener(this, rv_recipes, new RecyclerViewTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Passing some data to StepsActivity that comes from the call "Recipes"
                Recipe recipe = recipesArrayList.get(position);
                ingredientArrayList = recipe.getIngredients();
                stepArrayList = recipe.getSteps();
                Context context = view.getContext();
                Intent intent = new Intent(context, StepsActivity.class);
                intent.putParcelableArrayListExtra(INGREDIENTS_INTENT_KEY, ingredientArrayList);
                intent.putParcelableArrayListExtra(STEPS_INTENT_KEY, stepArrayList);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        getIdlingResource();

    }

    /**
     * Perform network call and load the recipes
     */
    public void loadRecipes() {
        ApiRetrofitInterface apiRetrofitInterface = RetrofitApiClient.getClient().create(ApiRetrofitInterface.class);
        Call<ArrayList<Recipe>> call = apiRetrofitInterface.getRecipe();
        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {

                recipesArrayList = response.body();
                recipeRecyclerAdapter = new RecipeRecyclerAdapter(recipesArrayList);
                rv_recipes.setAdapter(recipeRecyclerAdapter);

                if (linearLayoutManagerState != null ) {
                    linearLayoutManager.onRestoreInstanceState(linearLayoutManagerState);
                }
                if(gridLayoutManagerState != null){
                    gridLayoutManager.onRestoreInstanceState(gridLayoutManagerState);
                }

                pb_loading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.failed_message, Toast.LENGTH_SHORT).show();
                pb_loading.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            if (linearLayoutManager != null) {
                linearLayoutManagerState = linearLayoutManager.onSaveInstanceState();
                outState.putParcelable(LINEAR_LAYOUT_MANAGER_STATE_KEY, linearLayoutManagerState);
            }
            if(gridLayoutManager!=null){
                gridLayoutManagerState = gridLayoutManager.onSaveInstanceState();
                outState.putParcelable(GRID_LAYOUT_MANAGER_STATE_KEY, gridLayoutManagerState);
            }
        }
    }

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}