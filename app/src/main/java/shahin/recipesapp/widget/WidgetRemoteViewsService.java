package shahin.recipesapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import shahin.recipesapp.R;
import shahin.recipesapp.models.Ingredient;

import static shahin.recipesapp.utilities.Constants.RECIPE_SHARED_PREF_KEY;

public class WidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRemoteViewsFactory(this.getApplicationContext());
    }

    class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context context;
        private ArrayList<Ingredient> ingredientArrayList;

        public WidgetRemoteViewsFactory(Context context) {
            this.context = context;

            ingredientArrayList = new ArrayList<>();
            ingredientArrayList.add(new Ingredient(0.00, "N/A", "N/A"));
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String json = preferences.getString(RECIPE_SHARED_PREF_KEY, "");
            if (!json.equals("")) {
                Gson gson = new Gson();
                ingredientArrayList = gson.fromJson(json, new TypeToken<ArrayList<Ingredient>>() {
                }.getType());
            }
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (ingredientArrayList != null) {
                return ingredientArrayList.size();
            } else return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);

            String ingredient = ingredientArrayList.get(position).getQuantity() +
            ingredientArrayList.get(position).getMeasure() + " of " +
                    ingredientArrayList.get(position).getIngredient();

            rv.setTextViewText(R.id.tv_recipe_ingredient, ingredient);
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
