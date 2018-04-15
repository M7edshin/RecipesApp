package shahin.recipesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shahin.recipesapp.adapters.RecipeRecyclerAdapter;
import shahin.recipesapp.models.Recipe;
import shahin.recipesapp.remote.ApiUtils;
import shahin.recipesapp.remote.SOService;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_recipes)
    RecyclerView rv_recipes;

    private RecipeRecyclerAdapter recipeRecyclerAdapter;
    private List<Recipe> recipesList;

    private SOService soService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //recipeRecyclerAdapter = new RecipeRecyclerAdapter(recipesList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_recipes.setLayoutManager(layoutManager);

        soService = ApiUtils.getSOServie();
    }

    public void loadRecipes(){

        soService.getName().enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if(response.isSuccessful()){
                    String recipe = response.body().getName();
                }else{
                    Toast.makeText(getApplicationContext(), String.valueOf("Error: " + response.code()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error loading the data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}