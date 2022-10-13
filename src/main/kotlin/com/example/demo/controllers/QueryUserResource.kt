package com.example.demo.controllers

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class QueryUserResource(
    val userId: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val picture: String,
    val message: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Data(
    val data: List<UserComment>
)

data class UserComment(
    val id: String, val message: String, val owner: User, val post: String, val publishDate: String
)

data class User(
    val id: String, val title: String, val firstName: String, val lastName: String, val picture: String
)