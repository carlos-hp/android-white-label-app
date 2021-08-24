package br.com.cvargas.whitelabel.domain.usecase

import android.net.Uri

sealed interface UploadProductImageUseCase {
    suspend operator fun invoke(imageUri: Uri): String
}