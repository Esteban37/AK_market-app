package com.mitocode.marketappmitocodegrupo2.domain.usescase

import com.mitocode.marketappmitocodegrupo2.data.repositories.CategoryRepository
import javax.inject.Inject

class SaveCategories  @Inject constructor(private val categoryRepository: CategoryRepository) {

    suspend operator fun invoke() = categoryRepository.requestCategories()

}