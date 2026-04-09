package br.com.fiap.ecoscan.api

import br.com.fiap.ecoscan.model.ProductResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/v0/product/{barcode}.json")
    fun getProduct(
        @Path("barcode") barcode: String
    ): Call<ProductResponse>
}