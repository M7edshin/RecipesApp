package shahin.recipesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import shahin.recipesapp.fragments.StepDetailsFragment;

import static shahin.recipesapp.utilities.Constants.STEP_DETAILS_PARC_KEY;

public class StepDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_details);

        if(savedInstanceState == null){
            Bundle bundle = new Bundle();
            bundle.putParcelable(STEP_DETAILS_PARC_KEY, getIntent().getParcelableExtra(STEP_DETAILS_PARC_KEY));
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_details_container, stepDetailsFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

}
