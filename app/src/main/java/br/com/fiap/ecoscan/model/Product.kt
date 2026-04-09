package br.com.fiap.ecoscan.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    val product: Product?
)

data class Product(
    val product_name: String?,
    val ecoscore_grade: String?,
    val brands: String?,
    val ingredients_text: String?,


    val nova_group: Double?,

    val ingredients_analysis_tags: List<String>?,
    val nutrient_levels: NutrientLevels?,
    val nutriments: Nutriments?,
    val traces: String?,
    val categories: String?
)

data class NutrientLevels(
    val fat: String?,
    val salt: String?,
    val sugars: String?,
    val saturated_fat: String?
)

data class Nutriments(
    val energy_100g: Float?,

    @SerializedName("energy-kcal_100g")
    val energyKcal_100g: Float?,

    val carbohydrates_100g: Float?,
    val proteins_100g: Float?,
    val fat_100g: Float?,
    val fiber_100g: Float?,
    val salt_100g: Float?,
    val sugars_100g: Float?
)