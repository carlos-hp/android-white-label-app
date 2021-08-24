package br.com.cvargas.whitelabel.domain.usecase

import br.com.cvargas.whitelabel.data.ProductRepository
import br.com.cvargas.whitelabel.domain.model.Product
import javax.inject.Inject

class GetProductUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : GetProductUseCase {
    override suspend fun invoke(id: String): Product? {
        return productRepository.findProduct(id)
    }

}