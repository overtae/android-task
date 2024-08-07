package com.example.androidtask.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.androidtask.data.repository.SearchRepositoryImpl
import com.example.androidtask.network.NetworkClient

class SearchViewModelFactory : ViewModelProvider.Factory {
    private val repository = SearchRepositoryImpl(NetworkClient.kakaoNetwork)

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T = SearchViewModel(
        repository
    ) as T
}