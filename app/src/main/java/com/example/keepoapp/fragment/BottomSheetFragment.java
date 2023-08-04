package com.example.keepoapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.keepoapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    public interface IMessage{
        void message(TextView tvMessage);
    }
    public interface IButtonListener{
        void buttonListener();
    }
    public interface ITitle{
        void title(TextView tvTitle);
    }
    public IMessage iMessage;
    public IButtonListener iButtonListener;
    public ITitle iTitle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.basic_bottom_sheet_layout, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = view.findViewById(R.id.btnOK);
        button.setOnClickListener(new OKButtonListener());
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvMessage = view.findViewById(R.id.tvMessage);
        iTitle.title(tvTitle);
        iMessage.message(tvMessage);
    }
    class OKButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            iButtonListener.buttonListener();
        }
    }
}
