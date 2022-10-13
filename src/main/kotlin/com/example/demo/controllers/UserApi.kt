package com.example.demo.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody

interface UserApi {
    @ResponseBody
    @GetMapping("api/v1/users/{userId}")
    fun userBy(
        @PathVariable("userId")
        userId: String
    ): ResponseEntity<QueryUserResource>

    @ResponseBody
    @GetMapping("api/v1/users/")
    fun userAll(): ResponseEntity<QueryUserResources>
}