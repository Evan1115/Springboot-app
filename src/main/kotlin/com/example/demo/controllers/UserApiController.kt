package com.example.demo.controllers


import com.example.demo.repositories.UserProjection
import com.example.demo.repositories.UserRepository
import com.example.demo.services.UserService
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.util.*
import javax.annotation.PostConstruct

@RestController
class StudentApiController(
    private val userRepository: UserRepository,
    private val userService: UserService
) : UserApi {

    @PostConstruct
    fun onApplicationEvent() {
        fetchUserCommentData()
    }

    override fun userBy(userId: String): ResponseEntity<QueryUserResource> {
        return userService.getUserById(userId = userId)?.let {
            QueryUserResource(it.userId, it.title, it.firstName, it.lastName, it.picture, it.message)
        }.let {
            ResponseEntity.ok(it)
        }
    }

    override fun userAll(): ResponseEntity<QueryUserResources> {
        return QueryUserResources(userResources = userService.getAllUsers().map {
            QueryUserResource(
                it.userId,
                it.title,
                it.firstName,
                it.lastName,
                it.picture,
                it.message
            )
        }).let { ResponseEntity.ok(it) }
    }

    @Scheduled(cron = "0 */5 * ? * *") //every 5 minutes get the data
    fun fetchUserCommentData() {
        var limit = 50
        var page = 0
        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.add("app-id", "6347d812d56768dab3a1d6a2")
        val entity = HttpEntity<Objects>(headers)

        do {
            val url = "https://dummyapi.io/data/v1/comment?" + "page=" + page + "&" + "limit=" + limit
            val response = restTemplate.exchange(url, HttpMethod.GET, entity,
                object : ParameterizedTypeReference<Data>() {})
            val result = response.body

            result?.data?.forEach {
                it.message
                userRepository.save(
                    UserProjection(
                        it.id,
                        it.owner.title,
                        it.owner.firstName,
                        it.owner.lastName,
                        it.owner.picture,
                        it.message
                    )
                )
            }
            page++
        } while (result?.data?.isNotEmpty() == true)
    }
}
