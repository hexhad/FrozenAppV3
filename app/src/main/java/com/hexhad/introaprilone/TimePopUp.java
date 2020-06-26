package com.hexhad.introaprilone;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class TimePopUp extends DialogFragment {

    Button btn_set_time, btn_close;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView selected_view;
    RadioButton btn_1130, btn_1200, btn_1230, btn_0100, btn_0130, btn_0200, btn_0230, btn_0300, btn_0330, btn_0400, btn_0430, btn_0500, btn_0530, btn_0600;

    private DatabaseReference reff;

    private static final String TAG = "TimePopUp";

    public interface OnSetTimeSelected {
        void sendState(String input);
    }

    public OnSetTimeSelected onSetTimeSelected;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.custompopup_time, container, false);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ret_data_form_db();

        btn_set_time = view.findViewById(R.id.btn_set_time);
        btn_close = view.findViewById(R.id.btn_close_list);
        selected_view = view.findViewById(R.id.selected_view);
        radioGroup = view.findViewById(R.id.radio_group);

        btn_1130 = view.findViewById(R.id.btn_1130);
        btn_1200 = view.findViewById(R.id.btn_1200);
        btn_1230 = view.findViewById(R.id.btn_1230);
        btn_0100 = view.findViewById(R.id.btn_100);
        btn_0130 = view.findViewById(R.id.btn_130);
        btn_0200 = view.findViewById(R.id.btn_200);
        btn_0230 = view.findViewById(R.id.btn_230);
        btn_0300 = view.findViewById(R.id.btn_300);
        btn_0330 = view.findViewById(R.id.btn_330);
        btn_0400 = view.findViewById(R.id.btn_400);
        btn_0430 = view.findViewById(R.id.btn_430);
        btn_0500 = view.findViewById(R.id.btn_500);
        btn_0530 = view.findViewById(R.id.btn_530);
        btn_0600 = view.findViewById(R.id.btn_600);


        btn_set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() != -1) {
                    int radioId = radioGroup.getCheckedRadioButtonId();
                    radioButton = getView().findViewById(radioId);
                    //selected_view.setText(radioButton.getText());

                    String state = radioButton.getText().toString();
//30314673906881
                    if (state.equals("")) {
                        getDialog().dismiss();
                    } else {
                        onSetTimeSelected.sendState(radioButton.getText().toString());
                        getDialog().dismiss();
                    }
                } else {
                    selected_view.setText("Please Select A Time");
                }
            }
        });


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onSetTimeSelected = (OnSetTimeSelected) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach" + e.getMessage());
        }
    }

    private void ret_data_form_db() {
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        reff = FirebaseDatabase.getInstance().getReference().child("Orders").child(currentDate);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String time_1130st = dataSnapshot.child("11:30").child("Status").getValue().toString();
                String time_1200st = dataSnapshot.child("12:00").child("Status").getValue().toString();
                String time_1230st = dataSnapshot.child("12:30").child("Status").getValue().toString();
                String time_0100st = dataSnapshot.child("01:00").child("Status").getValue().toString();
                String time_0130st = dataSnapshot.child("01:30").child("Status").getValue().toString();
                String time_0200st = dataSnapshot.child("02:00").child("Status").getValue().toString();
                String time_0230st = dataSnapshot.child("02:30").child("Status").getValue().toString();
                String time_0300st = dataSnapshot.child("03:00").child("Status").getValue().toString();
                String time_0330st = dataSnapshot.child("03:30").child("Status").getValue().toString();
                String time_0400st = dataSnapshot.child("04:00").child("Status").getValue().toString();
                String time_0430st = dataSnapshot.child("04:30").child("Status").getValue().toString();
                String time_0500st = dataSnapshot.child("05:00").child("Status").getValue().toString();
                String time_0530st = dataSnapshot.child("05:30").child("Status").getValue().toString();
                String time_0600st = dataSnapshot.child("06:00").child("Status").getValue().toString();

                time_check_visibility(time_1130st, btn_1130);
                time_check_visibility(time_1200st, btn_1200);
                time_check_visibility(time_1230st, btn_1230);
                time_check_visibility(time_0100st, btn_0100);
                time_check_visibility(time_0130st, btn_0130);
                time_check_visibility(time_0200st, btn_0200);
                time_check_visibility(time_0230st, btn_0230);
                time_check_visibility(time_0300st, btn_0300);
                time_check_visibility(time_0330st, btn_0330);
                time_check_visibility(time_0400st, btn_0400);
                time_check_visibility(time_0430st, btn_0430);
                time_check_visibility(time_0500st, btn_0500);
                time_check_visibility(time_0530st, btn_0530);
                time_check_visibility(time_0600st, btn_0600);

                local_validity("11:15 ",btn_1130);
                local_validity("11:45 ",btn_1200);
                local_validity("12:15 ",btn_1230);
                local_validity("12:45 ",btn_0100);
                local_validity("13:15 ",btn_0130);
                local_validity("13:45 ",btn_0200);
                local_validity("14:15 ",btn_0230);
                local_validity("14:45 ",btn_0300);
                local_validity("15:15 ",btn_0330);
                local_validity("15:45 ",btn_0400);
                local_validity("16:15 ",btn_0430);
                local_validity("16:45 ",btn_0500);
                local_validity("17:15 ",btn_0530);
                local_validity("17:45 ",btn_0600);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void local_validity(String time, RadioButton radioB) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
        String getCurrentDate = sdf.format(c.getTime());
        //String myTime = "11:28 AM";
        //Toast.makeText(getContext(),getCurrentDate + " " + time , Toast.LENGTH_SHORT).show();

        if (getCurrentDate.compareTo(time) < 0){
            //Toast.makeText(getContext(),"Younger", Toast.LENGTH_SHORT).show();
            radioB.setEnabled(true);
        } else {
            radioB.setEnabled(false);
            //Toast.makeText(getContext(),"Older", Toast.LENGTH_SHORT).show();
        }
    }

    private void time_check_visibility(String f_b_time, RadioButton btn) {
        if (f_b_time.equals("Booked")) {
            btn.setEnabled(false);
        } else {
            btn.setEnabled(true);
        }

    }



}
