package org.maru.memoryform.nullable

import org.maru.memoryform.model.Hobby
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HobbyController(
    private val whichSideAreYou: WhichSideAreYou
) {

    @GetMapping("/hobbies")
    fun allHobbies(): List<Hobby> {
        return whichSideAreYou.allHobbies()
    }

    @GetMapping("/hobbies-not")
    fun allHobbies(exclude: String): List<Hobby> {
        return whichSideAreYou.hobbiesNot(exclude)
    }
}