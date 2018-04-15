package shahin.recipesapp.remote;

import retrofit2.Call;
import shahin.recipesapp.models.Recipe;

public interface SOService {
    Call<Recipe> getName();
}
