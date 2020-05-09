package org.dclm.live.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import org.dclm.live.R;
import org.dclm.live.databinding.FragmentLanguageBinding;
import org.dclm.live.ui.listen.RadioFragment;
import org.dclm.live.ui.listen.RadioViewModel;

import java.util.Objects;

public class LanguageSelection extends DialogFragment {

    private static final String TAG = "Language Change";



    public static boolean fromVideo = false;
    private String language;
    private FragmentLanguageBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       binding = DataBindingUtil.inflate(inflater, R.layout.fragment_language, container, false);
        setStyle(DialogFragment.STYLE_NO_TITLE,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);

        if (fromVideo){
            binding.yoruba.setVisibility(View.GONE);
            binding.igbo.setVisibility(View.GONE);
            binding.hausa.setVisibility(View.GONE);
            binding.portugal.setVisibility(View.GONE);
        } else {
            binding.yoruba.setVisibility(View.VISIBLE);
            binding.igbo.setVisibility(View.VISIBLE);
            binding.hausa.setVisibility(View.VISIBLE);
            binding.portugal.setVisibility(View.VISIBLE);
        }


        binding.english.setOnClickListener(v -> {
            RadioFragment.whereFrom = true;
            language = (requireActivity().getResources().getString(R.string.select_english_language));
            LanguageSelectionDirections.ActionLanguageSelectionToRadioFragment action = LanguageSelectionDirections.actionLanguageSelectionToRadioFragment(requireActivity().getResources().getString(R.string.select_english_language), " ");
            NavHostFragment.findNavController(LanguageSelection.this).navigate(action);
            getDialog().dismiss();
        });

        binding.yoruba.setOnClickListener(v -> {
            RadioFragment.whereFrom = true;
          language = (requireActivity().getResources().getString(R.string.select_yoruba_language));
            LanguageSelectionDirections.ActionLanguageSelectionToRadioFragment action = LanguageSelectionDirections.actionLanguageSelectionToRadioFragment(requireActivity().getResources().getString(R.string.select_yoruba_language), " ");
            NavHostFragment.findNavController(LanguageSelection.this).navigate(action);
            getDialog().dismiss();
        });

        binding.igbo.setOnClickListener(v -> {
            RadioFragment.whereFrom = true;
            LanguageSelectionDirections.ActionLanguageSelectionToRadioFragment action = LanguageSelectionDirections.actionLanguageSelectionToRadioFragment(requireActivity().getResources().getString(R.string.select_igbo_language), " ");
            NavHostFragment.findNavController(LanguageSelection.this).navigate(action);
          language = (requireActivity().getResources().getString(R.string.select_igbo_language));
            getDialog().dismiss();
        });

        binding.hausa.setOnClickListener(v -> {
            RadioFragment.whereFrom = true;
         language = (requireActivity().getResources().getString(R.string.select_hausa_language));
            LanguageSelectionDirections.ActionLanguageSelectionToRadioFragment action = LanguageSelectionDirections.actionLanguageSelectionToRadioFragment(requireActivity().getResources().getString(R.string.select_hausa_language), " ");
            NavHostFragment.findNavController(LanguageSelection.this).navigate(action);
            getDialog().dismiss();
        });

        binding.portugal.setOnClickListener(v -> {
            RadioFragment.whereFrom = true;
         language = (requireActivity().getResources().getString(R.string.select_portugal_language));
            LanguageSelectionDirections.ActionLanguageSelectionToRadioFragment action = LanguageSelectionDirections.actionLanguageSelectionToRadioFragment(requireActivity().getResources().getString(R.string.select_portugal_language), " ");
            NavHostFragment.findNavController(LanguageSelection.this).navigate(action);
            getDialog().dismiss();
        });

        binding.french.setOnClickListener(v -> {
            RadioFragment.whereFrom = true;
         language = (requireActivity().getResources().getString(R.string.select_french_language));
            LanguageSelectionDirections.ActionLanguageSelectionToRadioFragment action = LanguageSelectionDirections.actionLanguageSelectionToRadioFragment(requireActivity().getResources().getString(R.string.select_french_language), " ");
            NavHostFragment.findNavController(LanguageSelection.this).navigate(action);
            getDialog().dismiss();
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //RadioViewModel viewModel = new ViewModelProvider(this).get(RadioViewModel.class);
     /*   binding.english.setOnClickListener(v -> {
            RadioFragment.whereFrom = true;
            language = (requireActivity().getResources().getString(R.string.select_english_language));
         *//*   LanguageSelectionDirections.ActionLanguageSelectionToRadioFragment action = LanguageSelectionDirections.actionLanguageSelectionToRadioFragment(requireActivity().getResources().getString(R.string.select_english_language), " ");
            NavHostFragment.findNavController(LanguageSelection.this).navigate(action);*//*
         viewModel.link.postValue(language);
            getDialog().dismiss();
        });

        binding.yoruba.setOnClickListener(v -> {
            RadioFragment.whereFrom = true;
            language = (requireActivity().getResources().getString(R.string.select_yoruba_language));
           *//* LanguageSelectionDirections.ActionLanguageSelectionToRadioFragment action = LanguageSelectionDirections.actionLanguageSelectionToRadioFragment(requireActivity().getResources().getString(R.string.select_yoruba_language), " ");
            NavHostFragment.findNavController(LanguageSelection.this).navigate(action);*//*
            viewModel.link.postValue(language);
            getDialog().dismiss();
        });

        binding.igbo.setOnClickListener(v -> {
            RadioFragment.whereFrom = true;
          *//*  LanguageSelectionDirections.ActionLanguageSelectionToRadioFragment action = LanguageSelectionDirections.actionLanguageSelectionToRadioFragment(requireActivity().getResources().getString(R.string.select_igbo_language), " ");
            NavHostFragment.findNavController(LanguageSelection.this).navigate(action);*//*
            language = (requireActivity().getResources().getString(R.string.select_igbo_language));
            viewModel.link.postValue(language);
            getDialog().dismiss();
        });

        binding.hausa.setOnClickListener(v -> {
            RadioFragment.whereFrom = true;
            language = (requireActivity().getResources().getString(R.string.select_hausa_language));
         *//*   LanguageSelectionDirections.ActionLanguageSelectionToRadioFragment action = LanguageSelectionDirections.actionLanguageSelectionToRadioFragment(requireActivity().getResources().getString(R.string.select_hausa_language), " ");
            NavHostFragment.findNavController(LanguageSelection.this).navigate(action);*//*
            viewModel.link.postValue(language);
            getDialog().dismiss();
        });

        binding.portugal.setOnClickListener(v -> {
            RadioFragment.whereFrom = true;
            language = (requireActivity().getResources().getString(R.string.select_portugal_language));
            *//*LanguageSelectionDirections.ActionLanguageSelectionToRadioFragment action = LanguageSelectionDirections.actionLanguageSelectionToRadioFragment(requireActivity().getResources().getString(R.string.select_portugal_language), " ");
            NavHostFragment.findNavController(LanguageSelection.this).navigate(action);*//*
            viewModel.link.postValue(language);
            getDialog().dismiss();
        });

        binding.french.setOnClickListener(v -> {
            RadioFragment.whereFrom = true;
            language = (requireActivity().getResources().getString(R.string.select_french_language));
            *//*LanguageSelectionDirections.ActionLanguageSelectionToRadioFragment action = LanguageSelectionDirections.actionLanguageSelectionToRadioFragment(requireActivity().getResources().getString(R.string.select_french_language), " ");
            NavHostFragment.findNavController(LanguageSelection.this).navigate(action);*//*
            viewModel.link.postValue(language);
            getDialog().dismiss();
        });*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
