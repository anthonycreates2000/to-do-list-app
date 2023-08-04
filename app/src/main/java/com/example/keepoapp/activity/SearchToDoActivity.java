package com.example.keepoapp.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.keepoapp.R;
import com.example.keepoapp.adapter.BasicSearchToDoAdapter;
import com.example.keepoapp.adapter.SearchToDoAdapter;
import com.example.keepoapp.data.ToDo;
import com.example.keepoapp.databinding.ActivitySearchToDoBinding;
import com.example.keepoapp.viewmodel.SearchToDoViewModel;

import java.util.ArrayList;

public class SearchToDoActivity extends SearchActivity {
    private ActivitySearchToDoBinding binding;
    private SearchToDoAdapter adapter;
    private SearchToDoViewModel searchToDoViewModel;
    String searchText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_to_do);
        searchBinding = binding.searchLayout;
        searchBinding.edtSearch.setHint(R.string.search_todo);
        searchToDoViewModel = new ViewModelProvider(this).get(SearchToDoViewModel.class);
        initializeViewModel();
        setRecyclerView();
        setListeners();
    }
    void initializeViewModel(){
        searchToDoViewModel.iBeforeConnect = () -> {
            closeSoftKeyboard();
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.tvNoData.setVisibility(View.GONE);
            binding.tvResultFor.setVisibility(View.GONE);
        };
        searchToDoViewModel.getErrorLiveData().observe(this, (error) -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
        searchToDoViewModel.getBottomDialogMessageLiveData().observe(this, (error) -> {
            binding.progressBar.setVisibility(View.GONE);
            showErrorBottomSheetFragment(error);
        });
        searchToDoViewModel.getSearchToDoDataLiveData().observe(this, (searchToDoData) -> {
            binding.tvResultFor.setVisibility(View.VISIBLE);
            String resultFor = getString(R.string.result_for) + " \"" + searchText + "\"";
            binding.tvResultFor.setText(resultFor);
            binding.progressBar.setVisibility(View.GONE);
            ArrayList<ToDo> toDos = searchToDoData.getData();
            if (toDos.size() == 0){
                binding.tvNoData.setVisibility(View.VISIBLE);
            }
            adapter.updateAllItems(toDos);
        });
    }
    void setRecyclerView(){
        adapter = new BasicSearchToDoAdapter();
        adapter.iClickListener = (todo) -> {
            goToNextActivity(todo);
        };
        binding.recyclerToDo.setAdapter(adapter);
        binding.recyclerToDo.setLayoutManager(new LinearLayoutManager(this));
    }
    void goToNextActivity(ToDo todo){
        Intent intent = new Intent(this, DetailToDoActivity.class);
        intent.putExtra(DetailToDoActivity.KEY_USER_ID, todo.getUser_id());
        intent.putExtra(DetailToDoActivity.KEY_TODO_ID, todo.getTodo_id());
        startActivity(intent);
    }
    @Override
    protected void searchClickListener() {
        searchText = searchBinding.edtSearch.getText().toString();
        CheckBox checkByToDo = binding.chkToDo;
        CheckBox checkByUser = binding.chkUser;
        searchToDoViewModel.validateThenSearch(searchText, new CheckBox[]{checkByToDo, checkByUser});
    }
}