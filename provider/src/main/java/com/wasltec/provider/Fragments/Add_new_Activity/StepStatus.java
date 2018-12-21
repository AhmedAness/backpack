package com.wasltec.provider.Fragments.Add_new_Activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wasltec.provider.Activities.Add_new_activity;
import com.wasltec.provider.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepStatus extends Fragment {


    private TextView mCompleteFlag;
    private ImageButton mStep1;
    private ImageButton mStep2;
    private ImageButton mStep3;
    private ImageButton mStep4;
    private ImageButton mStep5;
    private TextView mCompleteFlag2;
    private ImageButton mStep6;
    private ImageButton mStep7;
    private ImageButton mStep8;
    private ImageButton mStep9;
    private ImageButton mStep10;

    public StepStatus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step_status, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Add_new_activity.dialog.hide();
        Add_new_activity.add_new_toolbar.setTitle(getString(R.string.add_new_activity));
        Add_new_activity.next_step.setText(R.string.continue_txt);
        mCompleteFlag = view.findViewById(R.id.complete_flag);
        Add_new_activity.steper.setVisibility(View.GONE);
        mStep1 = view.findViewById(R.id.step1);
        mStep2 = view.findViewById(R.id.step2);
        mStep3 = view.findViewById(R.id.step3);
        mStep4 = view.findViewById(R.id.step4);
        mStep5 = view.findViewById(R.id.step5);
        mCompleteFlag2 = view.findViewById(R.id.complete_flag2);
        mStep6 = view.findViewById(R.id.step6);
        mStep7 = view.findViewById(R.id.step7);
        mStep8 = view.findViewById(R.id.step8);
        mStep9 = view.findViewById(R.id.step9);
        mStep10 = view.findViewById(R.id.step10);

        if (getArguments() != null) {
            if (getArguments().getInt("step", -1) != -1) {
                int step = getArguments().getInt("step");
                for (int i = 0; i < step; i++) {
                    if (i == 1)
                        mStep1.setVisibility(View.VISIBLE);
                    else if (i == 2)
                        mStep2.setVisibility(View.VISIBLE);
                    else if (i == 3)
                        mStep3.setVisibility(View.VISIBLE);
                    else if (i == 4)
                        mStep4.setVisibility(View.VISIBLE);
                    else if (i == 5) {
                        mStep5.setVisibility(View.VISIBLE);
                        mCompleteFlag.setVisibility(View.VISIBLE);
                    }
                    else if (i == 6)
                        mStep6.setVisibility(View.VISIBLE);
                    else if (i == 7)
                        mStep7.setVisibility(View.VISIBLE);
                    else if (i == 8)
                        mStep8.setVisibility(View.VISIBLE);
                    else if (i == 9)
                        mStep9.setVisibility(View.VISIBLE);
                    else if (i == 10) {
                        mStep10.setVisibility(View.VISIBLE);
                        mCompleteFlag2.setVisibility(View.VISIBLE);
                    }
                }
                getArguments().clear();
                setArguments(null);
            } else {

                mStep1.setVisibility(View.VISIBLE);
                mStep2.setVisibility(View.VISIBLE);
                mStep3.setVisibility(View.VISIBLE);
                mStep4.setVisibility(View.VISIBLE);
                mStep5.setVisibility(View.VISIBLE);
                mCompleteFlag.setVisibility(View.VISIBLE);

            }
        } else {

            mStep1.setVisibility(View.VISIBLE);
            mStep2.setVisibility(View.VISIBLE);
            mStep3.setVisibility(View.VISIBLE);
            mStep4.setVisibility(View.VISIBLE);
            mStep5.setVisibility(View.VISIBLE);
            mCompleteFlag.setVisibility(View.VISIBLE);

        }

    }

    public void show_fn(int step_number) {
        switch (step_number) {
            case 0:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step1));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step1).commit();
                break;
            case 1:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step1));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step1).commit();
                break;
            case 2:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step2));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step2).commit();

                break;
            case 3:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step3));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step3).commit();

                break;
            case 4:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step4));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step4).commit();
                break;
            case 5:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step5));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step5).commit();
                break;
            case 6:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step1));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step6).commit();
                break;
            case 7:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step2));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step7).commit();
                break;
            case 8:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step3));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step8).commit();
                break;
            case 9:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step4));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step9).commit();
                break;
            case -1:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.stepStatus).commit();
                break;
            case 10:
                Add_new_activity.steper.setImageDrawable(this.getResources().getDrawable(R.drawable.step5));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container1, Add_new_activity.step10).commit();
                break;

        }


    }

    public void valid() {
        if (getArguments() != null) {
            show_fn(getArguments().getInt("step", 1));
            Add_new_activity.step_number = getArguments().getInt("step") == 0 ? 1 : getArguments().getInt("step");
            } else {
            Add_new_activity.steper.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.step1));
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container1, Add_new_activity.step6).commit();
            Add_new_activity.step_number = 6;
        }
        Add_new_activity.next_step.setText(R.string.next_txt);
        Add_new_activity.steper.setVisibility(View.VISIBLE);

    }
}
