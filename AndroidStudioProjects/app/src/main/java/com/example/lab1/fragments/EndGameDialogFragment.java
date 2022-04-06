package com.example.lab1.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.lab1.MainActivity;
import com.example.lab1.activity.EndGameActivity;

public class EndGameDialogFragment extends DialogFragment{

    private Switcher sw_interface;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sw_interface = (Switcher) context;
    }

    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Hello")
                .setMessage("Игра закончена. Сохранить результаты?")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("ОК", (dialogInterface, i) -> {
                    sw_interface.getToActivity();
                })
                .show()
                ;
    }

    @Override
    public void onDetach() {
        sw_interface = null;
        super.onDetach();
    }
}
