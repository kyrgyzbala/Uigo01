package kg.kyrgyzcoder.kassa01.data.network.item.model

import java.io.Serializable

data class ModelActive(
    val id: Int,
    val itemglobal: ModelGlobal,
    val cost: Float,
    val salecost: Float?,
    val quantity: Int
) : Serializable