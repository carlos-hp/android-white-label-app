package br.com.cvargas.whitelabel.domain.usecase

import android.net.Uri
import br.com.cvargas.whitelabel.domain.model.Product

sealed interface CreateProductUseCase {
    suspend operator fun invoke(description: String, price: Double, imageUri: Uri): Product
}