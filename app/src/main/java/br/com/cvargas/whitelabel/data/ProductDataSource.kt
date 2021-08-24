package br.com.cvargas.whitelabel.data

import android.net.Uri
import br.com.cvargas.whitelabel.domain.model.Product

sealed interface ProductDataSource {
    suspend fun findProduct(id: String): Product?
    suspend fun getProducts(): List<Product>
    suspend fun uploadProductImage(imageUri: Uri): String
    suspend fun createProduct(product: Product): Product
}