package shahin.recipesapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import shahin.recipesapp.R;
import shahin.recipesapp.StepsActivity;
import shahin.recipesapp.models.Step;

public class StepRecyclerAdapter extends RecyclerView.Adapter<StepRecyclerAdapter.StepHolder>{

    private ArrayList<Step> stepArrayList;
    private boolean tabletMode;
    private StepsActivity stepsActivity;

    public StepRecyclerAdapter(ArrayList<Step> stepArrayList, boolean tabletMode, StepsActivity stepsActivity){
        this.stepArrayList = stepArrayList;
        this.tabletMode = tabletMode;
        this.stepsActivity = stepsActivity;
    }

    @NonNull
    @Override
    public StepHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_recycler_item,parent,false);
        return new StepHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StepHolder holder, int position) {
        Step step = stepArrayList.get(position);
        holder.tv_step.setText(step.getDescription());
    }

    @Override
    public int getItemCount() {
        return stepArrayList.size();
    }


    public class StepHolder extends RecyclerView.ViewHolder{

        private TextView tv_step;

        public StepHolder(View itemView) {
            super(itemView);
            tv_step = itemView.findViewById(R.id.tv_step);
        }
    }
}
