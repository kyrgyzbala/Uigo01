package kg.kyrgyzcoder.kassa01.data.model

import java.io.Serializable

data class ModelItem(
    val itemName: String,
    val quantity: Int,
    val cost: Double,
    val total: Double
): Serializable