package org.horoyoii.horoyochat.main_Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.horoyoii.horoyochat.Activity.MainActivity;
import org.horoyoii.horoyochat.R;


/*
 * setViewContent를 호출할 수 없기에 call back함수인 oncCreateView에서 inflate를 한다.
 */

public class Fragment_Friend extends Fragment {

    MainActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (MainActivity)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_friend, container, false);


//        Button button = (Button)rootView.findViewById(R.id.btn_menu);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //activity.onFragmentChange(0);
//            }
//        });

        return rootView;
    }


}
