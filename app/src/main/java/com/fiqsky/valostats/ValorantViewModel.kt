package com.fiqsky.valostats

import android.util.Log
import androidx.lifecycle.ViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
author Fiqih
Copyright 2023, FiqSky Project
 **/
class ValorantViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.kyroskoh.xyz/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val valorantAPI = retrofit.create(ValorantAPI::class.java)

    suspend fun fetchValorantRank(region: String, nickname: String, tag: String): ValorantRank? {
        val url = "valorant/v1/mmr/$region/$nickname/$tag?show=all&display=0"
        Log.d("ValorantAPI", "URL: $url")

        return try {
            val response = valorantAPI.getValorantRank(region, nickname, tag)
            if (response.isSuccessful) {
                val responseText = response.body()?.string()
                // Proses responseText jika diperlukan
                // Contoh: "Gold 3 - 0RR. MMR Elo: 1100."
                val regex = Regex("""(\w+)\s*(\d+)?\s*-\s*(\d+)RR\..*?MMR Elo:\s*(\d+)""")
                val matchResult = responseText?.let { regex.find(it) }

                if (matchResult != null) {
                    val (rank, rankValue, rr, mmrElo) = matchResult.destructured
                    val formattedRank = if (rankValue.isNotEmpty()) {
                        "$rank $rankValue"
                    } else {
                        rank
                    }
                    ValorantRank(formattedRank, rr.toIntOrNull() ?: 0, mmrElo.toIntOrNull() ?: 0)
                } else {
                    null
                }
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
