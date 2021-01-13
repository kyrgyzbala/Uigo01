package kg.kyrgyzcoder.kassa01.data.network.item.model

import java.io.Serializable

data class ModelCategory(
    val id: Int,
    val totalCost: Float,
    val totalQuantity: Float,
    val name: String,
    val storeid: Int
): Serializable