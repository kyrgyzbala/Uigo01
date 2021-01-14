package kg.kyrgyzcoder.kassa01.data.network.sell.model

data class ModelPostTransaction(
    val clientitem: List<ModelSellItem>,
    var cashier: Int
)