package com.fiqsky.valostats

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val rankImageView = findViewById<ImageView>(R.id.rankImageView)
        val rankTextView = findViewById<TextView>(R.id.rankTextView)
        val rrTextView = findViewById<TextView>(R.id.rrTextView)
        val mmrEloTextView = findViewById<TextView>(R.id.mmrEloTextView)

        // Mendapatkan nilai-nilai dari intent
        val rank = intent.getStringExtra("rank") ?: ""
        val rr = intent.getIntExtra("rr", 0)
        val mmrElo = intent.getIntExtra("mmrElo", 0)

        // Set teks pada TextView
        rankTextView.text = "Rank: $rank"
        rrTextView.text = "RR: $rr"
        mmrEloTextView.text = "MMR Elo: $mmrElo"

        Log.d("RankDebug", "Received rank: $rank")

        // Menentukan gambar rank sesuai dengan rank
        val rankImageResource = when (rank) {
            "Iron 1" -> R.drawable.iron1
            "Iron 2" -> R.drawable.iron2
            "Iron 3" -> R.drawable.iron3
            "Bronze 1" -> R.drawable.bronze1
            "Bronze 2" -> R.drawable.bronze2
            "Bronze 3" -> R.drawable.bronze3
            "Silver 1" -> R.drawable.silver1
            "Silver 2" -> R.drawable.silver2
            "Silver 3" -> R.drawable.silver3
            "Gold 1" -> R.drawable.gold1
            "Gold 2" -> R.drawable.gold2
            "Gold 3" -> R.drawable.gold3
            "Platinum 1" -> R.drawable.platinum1
            "Platinum 2" -> R.drawable.platinum2
            "Platinum 3" -> R.drawable.platinum3
            "Diamond 1" -> R.drawable.diamond1
            "Diamond 2" -> R.drawable.diamond2
            "Diamond 3" -> R.drawable.diamond3
            "Ascendant 1" -> R.drawable.ascendant1
            "Ascendant 2" -> R.drawable.ascendant2
            "Ascendant 3" -> R.drawable.ascendant3
            "Immortal 1" -> R.drawable.immortal1
            "Immortal 2" -> R.drawable.immortal2
            "Immortal 3" -> R.drawable.immortal3
            "Radiant" ->  R.drawable.radiant
            // Tambahkan gambar rank untuk semua rank lainnya
            else -> R.drawable.radiant // Gambar default jika tidak ada yang cocok
        }

        rankImageView.setImageResource(rankImageResource)
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
}
