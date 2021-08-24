package br.com.cvargas.whitelabel.domain.usecase

import br.com.cvargas.whitelabel.data.ProductRepository
import br.com.cvargas.whitelabel.domain.model.Product
import javax.inject.Inject

class GetProductsUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : GetProductsUseCase {
    override suspend fun invoke(id: String): List<Product> {
        return productRepository.getProducts()
    }
}