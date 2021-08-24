package br.com.cvargas.whitelabel.data

import android.net.Uri
import br.com.cvargas.whitelabel.domain.model.Product
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val dataSource: ProductDataSource
) {
    suspend fun getProducts(): List<Product> = dataSource.getProducts()

    suspend fun uploadProductImage(imageUri: Uri): String =
        dataSource.uploadProductImage(imageUri)

    suspend fun createProduct(product: Product) =
        dataSource.createProduct(product)

    suspend fun findProduct(id: String) =
        dataSource.findProduct(id)
}