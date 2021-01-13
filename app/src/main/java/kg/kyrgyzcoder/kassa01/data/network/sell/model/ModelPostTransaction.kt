package kg.kyrgyzcoder.kassa01.data.network.sell.model

data class ModelPostTransaction(
    val activeitems: List<ModelSellItem>,
    var cashier: Int
)