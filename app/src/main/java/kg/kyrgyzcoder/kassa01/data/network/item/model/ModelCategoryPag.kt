package kg.kyrgyzcoder.kassa01.data.network.item.model

data class ModelCategoryPag(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<ModelCategory>
)