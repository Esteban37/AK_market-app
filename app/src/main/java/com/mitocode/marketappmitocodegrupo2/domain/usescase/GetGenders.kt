package com.mitocode.marketappmitocodegrupo2.domain.usescase

import com.mitocode.marketappmitocodegrupo2.data.repositories.GenderRepository
import javax.inject.Inject

class GetGenders @Inject constructor(private val genderRepository: GenderRepository) {

    suspend operator fun invoke() = genderRepository.getGenders()

}