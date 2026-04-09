package br.com.fiap.ecoscan.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ResultActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productName    = intent.getStringExtra("product_name")    ?: "Desconhecido"
        val brand          = intent.getStringExtra("brand")           ?: "Desconhecida"
        val ecoScoreRaw    = intent.getStringExtra("ecoscore")        ?: "unknown"
        val novaGroup      = intent.getIntExtra("nova_group", -1)
        val ingredientTags = intent.getStringArrayListExtra("ingredients_analysis_tags") ?: arrayListOf()
        val traces         = intent.getStringExtra("traces")          ?: ""
        val fatLevel       = intent.getStringExtra("fat_level")       ?: ""
        val saltLevel      = intent.getStringExtra("salt_level")      ?: ""
        val sugarsLevel    = intent.getStringExtra("sugars_level")    ?: ""
        val kcal           = intent.getFloatExtra("kcal_100g", -1f)
        val fiber          = intent.getFloatExtra("fiber_100g", -1f)
        val proteins       = intent.getFloatExtra("proteins_100g", -1f)

        val ecoScore = ecoScoreRaw.uppercase()

        setContent {
            ResultScreen(
                productName    = productName,
                brand          = brand,
                ecoScore       = ecoScore,
                novaGroup      = novaGroup,
                ingredientTags = ingredientTags,
                traces         = traces,
                fatLevel       = fatLevel,
                saltLevel      = saltLevel,
                sugarsLevel    = sugarsLevel,
                kcal           = kcal,
                fiber          = fiber,
                proteins       = proteins
            ) { finish() }
        }
    }
}



fun ecoScoreColor(score: String): Color = when (score) {
    "A" -> Color(0xFF2E7D32)
    "B" -> Color(0xFF558B2F)
    "C" -> Color(0xFFF9A825)
    "D" -> Color(0xFFE65100)
    "E" -> Color(0xFFB71C1C)
    else -> Color(0xFF9E9E9E)
}

fun ecoScoreLabel(score: String): String = when (score) {
    "A" -> "Muito baixo"
    "B" -> "Baixo"
    "C" -> "Médio"
    "D" -> "Alto"
    "E" -> "Muito alto"
    else -> "Desconhecido"
}

fun novaLabel(group: Int): String = when (group) {
    1 -> "Não processado ou minimamente processado ✅"
    2 -> "Ingrediente culinário processado ⚠️"
    3 -> "Alimento processado ⚠️"
    4 -> "Ultraprocessado ❌"
    else -> "Desconhecido"
}

fun novaColor(group: Int): Color = when (group) {
    1 -> Color(0xFF2E7D32)
    2 -> Color(0xFFF9A825)
    3 -> Color(0xFFE65100)
    4 -> Color(0xFFB71C1C)
    else -> Color(0xFF9E9E9E)
}

fun levelEmoji(level: String): String = when (level.lowercase()) {
    "low"      -> "🟢 Baixo"
    "moderate" -> "🟡 Moderado"
    "high"     -> "🔴 Alto"
    else       -> "⚪ Desconhecido"
}



@Composable
fun ResultScreen(
    productName: String,
    brand: String,
    ecoScore: String,
    novaGroup: Int,
    ingredientTags: List<String>,
    traces: String,
    fatLevel: String,
    saltLevel: String,
    sugarsLevel: String,
    kcal: Float,
    fiber: Float,
    proteins: Float,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F8E9))
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = productName,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20)
        )
        Text(
            text = "Marca: $brand",
            fontSize = 15.sp,
            color = Color.DarkGray
        )

        Divider(color = Color(0xFFB0BEC5))


        InfoCard(title = "🌍 EcoScore — Impacto Ambiental") {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(ecoScoreColor(ecoScore), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(ecoScore, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(ecoScoreLabel(ecoScore), fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    if (ecoScore !in listOf("A", "B")) {
                        Text(
                            "💡 Prefira produtos com EcoScore A ou B",
                            fontSize = 13.sp,
                            color = Color(0xFFE65100)
                        )
                    }
                }
            }
        }


        if (novaGroup > 0) {
            InfoCard(title = "🏭 Grau de Processamento (NOVA $novaGroup)") {
                Text(
                    text = novaLabel(novaGroup),
                    fontSize = 15.sp,
                    color = novaColor(novaGroup),
                    fontWeight = FontWeight.Medium
                )
            }
        }


        if (ingredientTags.isNotEmpty()) {
            InfoCard(title = "🌿 Análise dos Ingredientes") {
                val tagMap = mapOf(
                    "en:vegan"          to "✅ Vegano",
                    "en:vegetarian"     to "✅ Vegetariano",
                    "en:palm-oil-free"  to "✅ Sem óleo de palma",
                    "en:palm-oil"       to "⚠️ Contém óleo de palma",
                    "en:non-vegan"      to "❌ Não vegano",
                    "en:non-vegetarian" to "❌ Não vegetariano"
                )
                tagMap.forEach { (tag, label) ->
                    if (ingredientTags.contains(tag)) {
                        Text(label, fontSize = 15.sp)
                    }
                }
            }
        }


        if (fatLevel.isNotEmpty() || saltLevel.isNotEmpty() || sugarsLevel.isNotEmpty()) {
            InfoCard(title = "🥗 Níveis de Nutrientes (por 100g)") {
                if (fatLevel.isNotEmpty())    Text("Gordura:  ${levelEmoji(fatLevel)}")
                if (saltLevel.isNotEmpty())   Text("Sal:       ${levelEmoji(saltLevel)}")
                if (sugarsLevel.isNotEmpty()) Text("Açúcares: ${levelEmoji(sugarsLevel)}")
            }
        }


        if (kcal > 0 || fiber > 0 || proteins > 0) {
            InfoCard(title = "📊 Dados Nutricionais (por 100g)") {
                if (kcal > 0)     Text("⚡ Energia:   ${kcal.toInt()} kcal")
                if (proteins > 0) Text("💪 Proteínas: ${proteins}g")
                if (fiber > 0)    Text("🌾 Fibras:    ${fiber}g")
            }
        }


        if (traces.isNotEmpty()) {
            InfoCard(title = "⚠️ Rastreabilidade / Contaminação Cruzada") {
                Text(traces, fontSize = 14.sp, color = Color(0xFFB71C1C))
            }
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
        ) {
            Text("Voltar ao início", fontSize = 16.sp)
        }
    }
}

@Composable
fun InfoCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF2E7D32))
            Divider(color = Color(0xFFE0E0E0))
            content()
        }
    }
}