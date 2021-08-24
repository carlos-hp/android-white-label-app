package br.com.cvargas.whitelabel.data


import android.net.Uri
import br.com.cvargas.whitelabel.BuildConfig
import br.com.cvargas.whitelabel.domain.model.Product
import br.com.cvargas.whitelabel.util.COLLECTION_PRODUCTS
import br.com.cvargas.whitelabel.util.COLLECTION_ROOT
import br.com.cvargas.whitelabel.util.STORAGE_IMAGES
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.internal.InjectedFieldSignature
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class FirebaseProductDataSource @Inject constructor (
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) : ProductDataSource {

    private val documentReference: DocumentReference = firebaseFirestore
        .document("${COLLECTION_ROOT}/${BuildConfig.FIREBASE_FAVOR_COLLECTION}")

    private val storeReference = firebaseStorage.reference

    override suspend fun findProduct(id: String): Product? {
        return suspendCoroutine { continuation ->
            val productsReference = documentReference
                .collection(COLLECTION_PRODUCTS)
                .document(id)
            productsReference.get().addOnSuccessListener { document ->
                document.toObject(Product::class.java).run {
                    continuation.resumeWith(Result.success(this))
                }
            }.addOnFailureListener { exception ->
                continuation.resumeWith(Result.failure(exception))
            }
        }
    }

    override suspend fun getProducts(): List<Product> {
        return suspendCoroutine { continuation ->
            val productsReference = documentReference.collection(COLLECTION_PRODUCTS)
            productsReference.get().addOnSuccessListener { documents ->
                val products = mutableListOf<Product>()
                for (document in documents) {
                    document.toObject(Product::class.java).run {
                        products.add(this)
                    }
                }
                continuation.resumeWith(Result.success(products))
            }
            productsReference.get().addOnFailureListener { exception ->
                continuation.resumeWith(Result.failure(exception))
            }
        }
    }

    override suspend fun uploadProductImage(imageUri: Uri): String {
        return suspendCoroutine { continuation ->
            val randomKey = UUID.randomUUID()
            val childReference = storeReference.child(
                "$STORAGE_IMAGES/${BuildConfig.FIREBASE_FAVOR_COLLECTION}/$randomKey"
            )
            childReference.putFile(imageUri).addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    val path = uri.path ?: ""
                    continuation.resumeWith(Result.success(path))
                }
            }.addOnFailureListener { exception ->
                continuation.resumeWith(Result.failure(exception))
            }
        }
    }

    override suspend fun createProduct(product: Product): Product {
        return suspendCoroutine { continuation ->
            documentReference
                .collection(COLLECTION_PRODUCTS)
                .document(System.currentTimeMillis().toString())
                .set(product).addOnSuccessListener {
                    continuation.resumeWith(Result.success(product))
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }
        }
    }
}