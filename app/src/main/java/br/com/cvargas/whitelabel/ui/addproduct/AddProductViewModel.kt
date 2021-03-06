package br.com.cvargas.whitelabel.ui.addproduct

import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import br.com.cvargas.whitelabel.R
import br.com.cvargas.whitelabel.domain.usecase.CreateProductUseCase
import br.com.cvargas.whitelabel.util.fromCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val createProductUseCase: CreateProductUseCase
) : ViewModel() {

    private val _imageUriErrorResId = MutableLiveData<Int>()
    val imageUriErrorResId: LiveData<Int> = _imageUriErrorResId

    private val _descriptionFieldErrorResId = MutableLiveData<Int?>()
    val descriptionFieldErrorResId: LiveData<Int?> = _descriptionFieldErrorResId

    private val _priceFieldErrorResId = MutableLiveData<Int?>()
    val priceFieldErrorResId: LiveData<Int?> = _priceFieldErrorResId

    private var isFormValid: Boolean = false

    fun createProduct(description: String, price: String, imageUri: Uri?) = viewModelScope.launch {
        isFormValid = true

        _imageUriErrorResId.value = getDrawableResIdIfNull(imageUri)
        _descriptionFieldErrorResId.value = getErrorStringResIdIfEmpty(description)
        _priceFieldErrorResId.value = getErrorStringResIdIfEmpty(price)

        if (isFormValid){
            try {
                val product = createProductUseCase.invoke(description, price.fromCurrency(), imageUri!!)
            }catch (e: Exception){
                Log.e("createProduct", e.toString())
            }
        }
    }

    private fun getErrorStringResIdIfEmpty(value: String): Int?{
        return if (value.isEmpty()){
            isFormValid = false
            R.string.add_product_field_error
        }else null
    }

    private fun getDrawableResIdIfNull(value: Uri?): Int{
        return if (value == null){
            isFormValid = false
            R.drawable.background_product_image_error
        }else R.drawable.background_product_image
    }
}