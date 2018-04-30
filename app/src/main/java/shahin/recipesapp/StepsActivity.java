package shahin.recipesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import shahin.recipesapp.utilities.RecyclerViewTouchListener;
import shahin.recipesapp.adapters.StepRecyclerAdapter;
import shahin.recipesapp.fragments.StepDetailsFragment;
import shahin.recipesapp.models.Ingredient;
import shahin.recipesapp.models.Step;
import shahin.recipesapp.widget.RecipeWidgetProvider;

import static shahin.recipesapp.utilities.Constants.INGREDIENTS_INTENT_KEY;
import static shahin.recipesapp.utilities.Constants.RECIPE_SHARED_PREF_KEY;
import static shahin.recipesapp.utilities.Constants.STEPS_INTENT_KEY;
import static shahin.recipesapp.utilities.Constants.STEP_DETAILS_PARC_KEY;

public class StepsActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.rv_steps) RecyclerView rv_steps;
    @Nullable
    @BindView(R.id.linear_layout_checkbox) LinearLayout linear_layout_checkbox;

    ArrayList<Ingredient> ingredientArrayList;
    ArrayList<Step> stepsArrayList;

    StepRecyclerAdapter stepRecyclerAdapter;

    private boolean tabletMode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ButterKnife.bind(this);

        if(findViewById(R.id.step_details_container)!=null){
            tabletMode = true;
        }

        Intent intentThatStartedThisActivity = getIntent();

        if(intentThatStartedThisActivity.hasExtra(INGREDIENTS_INTENT_KEY)){

            ingredientArrayList =  intentThatStartedThisActivity.getParcelableArrayListExtra(INGREDIENTS_INTENT_KEY);

            for(int i = 0; i < ingredientArrayList.size(); i++){
                CheckBox checkBox = new CheckBox(this);
                checkBox.setId(i);
                checkBox.setTextSize(12);
                String measure = ingredientArrayList.get(i).getQuantity()+ " " +
                        ingredientArrayList.get(i).getMeasure()+ " of " +
                        ingredientArrayList.get(i).getIngredient();
                checkBox.setText(measure);
                linear_layout_checkbox.addView(checkBox);
            }

        }

        if(intentThatStartedThisActivity.hasExtra(STEPS_INTENT_KEY)){
            stepsArrayList = intentThatStartedThisActivity.getParcelableArrayListExtra(STEPS_INTENT_KEY);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rv_steps.setLayoutManager(layoutManager);

            stepRecyclerAdapter = new StepRecyclerAdapter(stepsArrayList, tabletMode, this);
            rv_steps.setAdapter(stepRecyclerAdapter);

            rv_steps.addOnItemTouchListener(new RecyclerViewTouchListener(this, rv_steps, new RecyclerViewTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    if(tabletMode){
                        Step step = stepsArrayList.get(position);
                        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(STEP_DETAILS_PARC_KEY, step);
                        stepDetailsFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.step_details_container, stepDetailsFragment)
                                .commit();
                    }else{
                        Step step = stepsArrayList.get(position);
                        Context context = view.getContext();
                        Intent intent = new Intent(context, StepDetailsActivity.class);
                        intent.putExtra(STEP_DETAILS_PARC_KEY, step);
                        context.startActivity(intent);
                    }
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.step_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }else if( id == R.id.action_widget){
            makeData();
            sendBroadcast();
            Toast.makeText(getApplicationContext(), R.string.widget_now, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void makeData() {

        Gson gson = new Gson();
        String json = gson.toJson(ingredientArrayList);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(RECIPE_SHARED_PREF_KEY, json).apply();
    }

    private void sendBroadcast() {
        Intent intent = new Intent(this, RecipeWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE\"");
        sendBroadcast(intent);
    }
}