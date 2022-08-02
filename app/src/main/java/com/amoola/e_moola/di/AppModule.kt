package com.amoola.e_moola.di

import com.amoola.e_moola.core.Constants.USER
import com.amoola.e_moola.loginsignup.repository.AuthRepositoryImpl
import com.amoola.e_moola.loginsignup.ui.BaseAuthenticator
import com.amoola.e_moola.loginsignup.repository.firebase.FirebaseAuthenticatorImpl
import com.amoola.e_moola.user.repository.UserRepository
import com.amoola.e_moola.user.repository.UserRepositoryImpl
import com.amoola.e_moola.user.usecase.UserUsecase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**All of our application dependencies shall be provided here*/

    //this means that anytime we need an authenticator Dagger will provide a Firebase authenticator.
    //in future if you want to swap out Firebase authentication for your own custom authenticator
    //you will simply come and swap here.
    @Singleton
    @Provides
    fun provideAuthenticator() : BaseAuthenticator {
        return  FirebaseAuthenticatorImpl()
    }

    //this just takes the same idea as the authenticator. If we create another repository class
    //we can simply just swap here
    @Singleton
    @Provides
    fun provideRepository(
        authenticator : BaseAuthenticator
    ) : AuthRepositoryImpl {
        return AuthRepositoryImpl(authenticator)
    }

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideUserRef(
        db: FirebaseFirestore
    ) = db.collection(USER)

    @Provides
    fun provideUserRepository(
        userRef: CollectionReference
    ): UserRepository = UserRepositoryImpl()

    @Provides
    fun provideUseCases(
        repo: UserRepository
    ) = UserUsecase(repo)
}