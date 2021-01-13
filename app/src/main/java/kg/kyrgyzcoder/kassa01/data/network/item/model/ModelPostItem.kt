package kg.kyrgyzcoder.kassa01.data.network.item.model

data class ModelPostItem(
    val uniqueId: String,
    val cost: Float,
    val category: Int,
    val count: Int,
    var storeid: Int
)