package org.maru.memoryform.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HobbyRepository: JpaRepository<Hobby, Long> {

    fun findAllBySecretHobbyNot(exclude: String): List<Hobby>
}