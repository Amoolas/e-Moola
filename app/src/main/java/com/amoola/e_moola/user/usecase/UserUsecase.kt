package com.amoola.e_moola.user.usecase

import com.amoola.e_moola.user.model.User
import com.amoola.e_moola.user.repository.UserRepository


class UserUsecase(private val repo: UserRepository) {
    suspend operator fun invoke(user: User) = repo.addUserToFirestore(user)
    suspend operator fun invoke(userId: String) = repo.deleteUserFromFirestore(userId)
    operator fun invoke() = repo.getUsersFromFirestore()
}