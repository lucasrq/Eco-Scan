package br.com.fiap.ecoscan.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import br.com.fiap.ecoscan.api.RetrofitClient
import br.com.fiap.ecoscan.model.Product
import br.com.fiap.ecoscan.model.ProductResponse
import com.google.zxing.integration.android.IntentIntegrator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startScan()
    }

    private fun startScan() {
        val integrator = IntentIntegrator(this)
        integrator.setPrompt("Escaneie o código ou pressione voltar para digitar manualmente")
        integrator.setBeepEnabled(true)
        integrator.setOrientationLocked(false)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                fetchProduct(result.contents)
            } else {
                showManualInputDialog()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun fetchProduct(barcode: String, retry: Int = 0) {
        Toast.makeText(this, "Buscando produto...", Toast.LENGTH_SHORT).show()
        RetrofitClient.instance.getProduct(barcode).enqueue(object : Callback<ProductResponse> {

            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                val product = response.body()?.product
                if (response.isSuccessful && product != null) {
                    openResult(product)
                } else if (retry < 1) {
                    fetchProduct(barcode, retry + 1)
                } else {
                    Toast.makeText(this@CameraActivity, "Produto não encontrado", Toast.LENGTH_SHORT).show()
                    showManualInputDialog()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                t.printStackTrace()
                if (retry < 1) {
                    fetchProduct(barcode, retry + 1)
                } else {
                    Toast.makeText(this@CameraActivity, "Erro de rede ou API. Tente novamente", Toast.LENGTH_SHORT).show()
                    showManualInputDialog()
                }
            }
        })
    }

    private fun openResult(product: Product) {
        val intent = Intent(this, ResultActivity::class.java).apply {

            putExtra("product_name", product.product_name ?: "Desconhecido")
            putExtra("brand",        product.brands       ?: "Desconhecida")
            putExtra("ecoscore",     product.ecoscore_grade ?: "unknown")


            putExtra("nova_group", product.nova_group?.toInt() ?: -1)


            val tags = product.ingredients_analysis_tags?.let { ArrayList(it) } ?: arrayListOf()
            putStringArrayListExtra("ingredients_analysis_tags", tags)


            putExtra("traces", product.traces ?: "")


            putExtra("fat_level",    product.nutrient_levels?.fat    ?: "")
            putExtra("salt_level",   product.nutrient_levels?.salt   ?: "")
            putExtra("sugars_level", product.nutrient_levels?.sugars ?: "")


            putExtra("kcal_100g",     product.nutriments?.energyKcal_100g ?: -1f)
            putExtra("fiber_100g",    product.nutriments?.fiber_100g      ?: -1f)
            putExtra("proteins_100g", product.nutriments?.proteins_100g   ?: -1f)
        }
        startActivity(intent)
    }

    private fun showManualInputDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Código de barras")
        builder.setMessage("Digite o código manualmente:")

        val input = EditText(this)
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        builder.setView(input)

        builder.setPositiveButton("Buscar") { _, _ ->
            val barcode = input.text.toString().trim()
            if (barcode.isNotEmpty()) fetchProduct(barcode)
            else Toast.makeText(this, "Código vazio", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }
}