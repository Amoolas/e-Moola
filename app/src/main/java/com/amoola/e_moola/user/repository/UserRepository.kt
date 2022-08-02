package com.amoola.e_moola.user.repository

import com.amoola.e_moola.user.model.Response
import com.amoola.e_moola.user.model.User
import kotlinx.coroutines.flow.Flow


interface UserRepository {

    fun getUsersFromFirestore(): Flow<Response<List<User>>>

    suspend fun addUserToFirestore(user: User): Flow<Any>

    suspend fun deleteUserFromFirestore(userId: String): Flow<Any>
}