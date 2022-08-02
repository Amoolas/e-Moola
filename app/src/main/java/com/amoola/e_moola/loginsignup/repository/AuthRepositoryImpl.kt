package com.amoola.e_moola.loginsignup.repository

import com.amoola.e_moola.loginsignup.ui.BaseAuthenticator
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

 class AuthRepositoryImpl @Inject constructor(
     private val authenticator : BaseAuthenticator
 ) : AuthRepository {
     override suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser? {
         return authenticator.signInWithEmailPassword(email , password)
     }

     override suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser? {
         return authenticator.signUpWithEmailPassword(email , password)
     }

     override fun signOut(): FirebaseUser? {
         return authenticator.signOut()
     }

     override fun getCurrentUser(): FirebaseUser? {
         return authenticator.getUser()
     }

     override suspend fun sendResetPassword(email: String): Boolean {
         authenticator.sendPasswordReset(email)
         return true
     }
 }