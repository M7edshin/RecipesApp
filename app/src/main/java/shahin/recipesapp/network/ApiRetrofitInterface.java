package shahin.recipesapp.network;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import shahin.recipesapp.models.Recipe;

public interface ApiRetrofitInterface {
    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipe();
}
