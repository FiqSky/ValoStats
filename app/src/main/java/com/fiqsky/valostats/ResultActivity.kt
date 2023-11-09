package com.fiqsky.valostats

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.Shader
import android.os.Bundle
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val rankImageView = findViewById<ImageView>(R.id.rankImageView)
        val rankTextView = findViewById<TextView>(R.id.rankTextView)
        val rrTextView = findViewById<TextView>(R.id.rrTextView)
        val mmrEloTextView = findViewById<TextView>(R.id.mmrEloTextView)
        val nicknameTextView = findViewById<TextView>(R.id.nicknameTextView)

        // Mendapatkan nilai-nilai dari intent
        val rank = intent.getStringExtra("rank") ?: ""
        val rr = intent.getIntExtra("rr", 0)
        val mmrElo = intent.getIntExtra("mmrElo", 0)
        val nickname = intent.getStringExtra("nickname") ?: ""
        val tag = intent.getStringExtra("tag") ?: ""

        val colors = intArrayOf(
            Color.RED, Color.GREEN, Color.BLUE, Color.BLACK, Color.MAGENTA
        )

        val colorAnimator = ValueAnimator()
        colorAnimator.setIntValues(*colors)
        colorAnimator.setEvaluator(ArgbEvaluator())
        colorAnimator.duration = 10000
        colorAnimator.repeatMode = ValueAnimator.REVERSE
        colorAnimator.repeatCount = ValueAnimator.INFINITE
        colorAnimator.interpolator = AccelerateDecelerateInterpolator()

        colorAnimator.addUpdateListener { animator ->
            val animatedColor = animator.animatedValue as Int
            nicknameTextView.setTextColor(animatedColor)
        }

        colorAnimator.start()

        // Set teks pada TextView
        rankTextView.text = "Rank: $rank"
        rrTextView.text = "RR: $rr"
        mmrEloTextView.text = "MMR Elo: $mmrElo"
        nicknameTextView.text = "$nickname#$tag"

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

        applySharpeningEffect(rankImageView, rankImageResource)
    }

    fun applySharpeningEffect(imageView: ImageView, resourceId: Int) {
        val bitmap = BitmapFactory.decodeResource(resources, resourceId)

        // Membuat BitmapShader dari gambar
        val shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        // Menerapkan efek sharpening menggunakan ColorMatrix
        val paint = Paint()
        paint.shader = shader

        val sharpenMatrix = ColorMatrix()
        sharpenMatrix.set(floatArrayOf(
            -1f, -1f, -1f, -1f, -1f,
            -1f, 9f, -1f, -1f, -1f,
            -1f, -1f, -1f, -1f, -1f,
            -1f, -1f, -1f, 16f, -1f
        ))

        val colorMatrixColorFilter = ColorMatrixColorFilter(sharpenMatrix)
        paint.colorFilter = colorMatrixColorFilter

        // Menerapkan gambar yang telah diberi efek ke ImageView
        imageView.setImageBitmap(bitmap)
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
}
