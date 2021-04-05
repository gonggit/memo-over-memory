package org.maru.memoryform.nullable

import org.maru.memoryform.model.Hobby
import org.maru.memoryform.model.HobbyRepository
import org.springframework.stereotype.Service

/**
 * Hey, null! which side are you on?
 * */
@Service
class WhichSideAreYou(
    private val hobbyRepository: HobbyRepository
) {

    fun allHobbies(): List<Hobby> {
        return hobbyRepository.findAll()
    }

    fun hobbiesNot(hobby: String): List<Hobby> {
        return hobbyRepository.findAllBySecretHobbyNot(hobby)
    }
}