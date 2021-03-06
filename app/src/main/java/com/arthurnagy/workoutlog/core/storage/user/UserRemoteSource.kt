package com.arthurnagy.workoutlog.core.storage.user

import com.arthurnagy.workoutlog.core.model.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import me.arthurnagy.kotlincoroutines.Result
import me.arthurnagy.kotlincoroutines.awaitGetResult
import me.arthurnagy.kotlincoroutines.awaitResult
import me.arthurnagy.kotlincoroutines.awaitSetResult

class UserRemoteSource(
    private val firebaseAuth: FirebaseAuth,
    private val userCollection: CollectionReference
) {

    suspend fun createUser(authenticationCredential: AuthCredential): Result<User> {
        val authResult = firebaseAuth.signInWithCredential(authenticationCredential).awaitResult()
        when (authResult) {
            is Result.Success -> {
                val firebaseUser = authResult.value.user
                val user = User(
                    id = firebaseUser.uid,
                    displayName = firebaseUser.displayName,
                    email = firebaseUser.email,
                    profilePictureUrl = firebaseUser.photoUrl.toString()
                )
                val userResult = userCollection.document(user.id).awaitSetResult(user)
                return when (userResult) {
                    is Result.Success -> Result.Success(user)
                    is Result.Error -> userResult
                }
            }
            is Result.Error -> return authResult
        }
    }

    suspend fun getUser(): Result<User> = firebaseAuth.currentUser?.let {
        userCollection.document(it.uid).awaitGetResult<User>()
    } ?: Result.Error(Exception("No logged in user"))

}
