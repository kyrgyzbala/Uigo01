package kg.kyrgyzcoder.kassa01.data.network.item.model

data class ModelCreateCategory(
    val totalCost: Float,
    val totalQuantity: Float,
    val name: String,
    var storeid: Int
)