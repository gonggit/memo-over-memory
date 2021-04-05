package org.maru.memoryform.model

import javax.persistence.*

@Entity
@Table(name = "hobbies")
data class Hobby(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,

    @Column(name = "`user_name`")
    val userName: String,

    @Column(name = "`secret_hobby`")
    val secretHobby: String?
)