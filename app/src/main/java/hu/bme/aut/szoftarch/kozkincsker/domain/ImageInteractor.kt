package hu.bme.aut.szoftarch.kozkincsker.domain

import hu.bme.aut.szoftarch.kozkincsker.data.datasource.FirebaseDataSource
import javax.inject.Inject

class ImageInteractor @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {
    suspend fun uploadImageToStorage(byteArray: ByteArray) : String? {
        val data = firebaseDataSource.uploadImageToStorage(byteArray)
        return data
    }

    suspend fun downloadImageFromStorage(byteArray: ByteArray) : String? {
        val data = firebaseDataSource.uploadImageToStorage(byteArray)
        return data
    }
}