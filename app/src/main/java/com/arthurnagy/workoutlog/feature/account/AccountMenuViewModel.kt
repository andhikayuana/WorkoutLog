package com.arthurnagy.workoutlog.feature.account

import android.util.Log
import androidx.databinding.ObservableField
import com.arthurnagy.workoutlog.core.WorkoutLogViewModel
import com.arthurnagy.workoutlog.core.model.User
import com.arthurnagy.workoutlog.core.storage.user.UserRepository
import com.arthurnagy.workoutlog.feature.shared.dependantObservabelField
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.Dispatchers
import me.arthurnagy.kotlincoroutines.Result

class AccountMenuViewModel(private val userRepository: UserRepository) : WorkoutLogViewModel() {

    val user = ObservableField<User>()
    val isUserLoggedIn = dependantObservabelField(user) { user.get() != null }

    init {
        launchWithParent(Dispatchers.Main) {
            val userResult = userRepository.getUser()
            when (userResult) {
                is Result.Success -> user.set(userResult.value)
                is Result.Error -> Log.d("AccountMenuViewModel", "error retrieving user: ${userResult.exception}")
            }
        }
    }

    fun signIn(authCredential: AuthCredential) {
        launchWithParent(Dispatchers.Main) {
            val result = userRepository.createUser(authCredential)
            when (result) {
                is Result.Success -> user.set(result.value)
                is Result.Error -> Log.d("AccountMenuViewModel", "error: ${result.exception}")
            }
        }
    }

}