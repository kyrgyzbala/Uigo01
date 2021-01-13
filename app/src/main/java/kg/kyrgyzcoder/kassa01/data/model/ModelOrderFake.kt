package kg.kyrgyzcoder.kassa01.data.model

import java.io.Serializable

data class ModelOrderFake(
    val orderNumber: Long,
    val date: String,
    var status: String,
    val comment: String,
    val detail: List<ModelItem>
): Serializable