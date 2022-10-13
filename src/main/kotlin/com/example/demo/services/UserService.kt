package com.example.demo.services

import com.example.demo.repositories.UserProjection
import com.example.demo.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getUserById(userId: String) = userRepository.findById(userId).orElse(null)
    fun getAllUsers() = userRepository.findAll()
}