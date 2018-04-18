package shahin.recipesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shahin.recipesapp.adapters.RecipeRecyclerAdapter;
import shahin.recipesapp.models.Recipe;
import shahin.recipesapp.network.RetrofitApiClient;
import shahin.recipesapp.network.ApiRetrofitInterface;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_recipes) RecyclerView rv_recipes;

    private RecipeRecyclerAdapter recipeRecyclerAdapter;
    private ArrayList<Recipe> recipesList;

    private ApiRetrofitInterface apiRetrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_recipes.setLayoutManager(layoutManager);
        recipesList = new ArrayList<>();

        loadRecipes();
    }

    public void loadRecipes(){

        apiRetrofitInterface = RetrofitApiClient.getClient().create(ApiRetrofitInterface.class);

        Call<ArrayList<Recipe>> call = apiRetrofitInterface.getRecipe();

        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                recipesList = response.body();
                recipeRecyclerAdapter = new RecipeRecyclerAdapter(recipesList);
                rv_recipes.setAdapter(recipeRecyclerAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.failed_message, Toast.LENGTH_SHORT).show();
            }
        });


    }
}