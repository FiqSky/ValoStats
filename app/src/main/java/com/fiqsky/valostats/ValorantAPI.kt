package com.fiqsky.valostats

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

/**
author Fiqih
Copyright 2023, FiqSky Project
 **/
interface ValorantAPI {
    @GET("valorant/v1/mmr/{region}/{nickname}/{tag}?show=all&display=0")
    @Streaming // Tambahkan @Streaming
    suspend fun getValorantRank(
        @Path("region") region: String,
        @Path("nickname") nickname: String,
        @Path("tag") tag: String
    ): Response<ResponseBody>

}
