package shahin.recipesapp.remote;

public class ApiUtils {
    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    public static SOService getSOServie(){
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
