package br.com.cvargas.whitelabel.domain.usecase

import br.com.cvargas.whitelabel.domain.model.Product

sealed interface GetProductsUseCase {
    suspend operator fun invoke(id: String): List<Product>
}