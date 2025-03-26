package com.cr.studentregistry.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {

    single <FirebaseFirestore> {
        Firebase.firestore
    }

    single <CollectionReference> (named("students")) {
        get<FirebaseFirestore>().collection("students")

    }

}