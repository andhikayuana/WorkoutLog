package me.arthurnagy.kotlincoroutines.firestore

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source

suspend inline fun <reified T> Query.awaitList(source: Source = Source.DEFAULT): List<T> = this.get(source).awaitList()