package com.amoola.e_moola.user.repository


import com.amoola.e_moola.core.Constants.FIRSTNAME
import com.amoola.e_moola.user.model.Response
import com.amoola.e_moola.user.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(): UserRepository {
    private val db = FirebaseFirestore.getInstance()
    var userRef  = db.collection("user")
    override fun getUsersFromFirestore() = callbackFlow {
        val snapshotListener = userRef.orderBy(FIRSTNAME).addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val user = snapshot.toObjects(User::class.java)
                Response.Success(user)
            } else {
                Response.Error(e?.message ?: e.toString())
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun  addUserToFirestore(user:User): Flow<Any> = flow {
        try {
            emit(Response.Loading)
            val id = userRef.document().id
            user.userId=id
            val addition = userRef.document(id).set(user).await()
           emit(Response.Success(addition))
        } catch (e: Exception) {
            emit(Error(e.message ?: e.toString()))
        }
    }

    override suspend fun deleteUserFromFirestore(bookId: String): Flow<Any> = flow {
        try {
            emit(Response.Loading)
            val deletion = userRef.document(bookId).delete().await()
            emit(Response.Success(deletion))
        } catch (e: Exception) {
            emit(Error(e.message ?: e.toString()))
        }
    }

}