package com.arthurnagy.workoutlog

import android.app.Application
import com.arthurnagy.workoutlog.core.injection.appModule
import com.arthurnagy.workoutlog.core.injection.dbModule
import com.arthurnagy.workoutlog.core.injection.repositoryModule
import com.arthurnagy.workoutlog.feature.account.accountMenuModule
import com.arthurnagy.workoutlog.feature.workout.workoutModule
import com.arthurnagy.workoutlog.feature.workouts.workoutsModule
import org.koin.android.ext.android.startKoin

class WorkoutLogApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, dbModule, repositoryModule, accountMenuModule, workoutModule, workoutsModule))
    }

}