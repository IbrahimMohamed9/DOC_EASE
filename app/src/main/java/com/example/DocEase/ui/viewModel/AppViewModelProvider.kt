package com.example.DocEase.ui.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.DocEase.DocEaseApplication
import com.example.DocEase.ui.viewModel.screens.LoginRegistrationViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            LoginRegistrationViewModel(
                DocEaseApplication().container.doctorRepository
            )
        }
    }
}

fun CreationExtras.DocEaseApplication(): DocEaseApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as
            DocEaseApplication)
