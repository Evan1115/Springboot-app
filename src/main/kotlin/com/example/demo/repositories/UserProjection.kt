package com.example.demo.repositories

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
@Data
data class UserProjection(

    @Id
    val userId: String = "",
    val title: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val picture: String = "",
    val message: String = ""
)
