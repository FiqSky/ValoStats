@file:Suppress("DEPRECATION")

package com.fiqsky.valostats

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var regionSpinner: Spinner
    private lateinit var nicknameEditText: EditText
    private lateinit var tagEditText: EditText
    private lateinit var fetchButton: Button
    private lateinit var viewModel: ValorantViewModel
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(ValorantViewModel::class.java)

        regionSpinner = findViewById(R.id.regionSpinner)
        nicknameEditText = findViewById(R.id.nicknameEditText)
        tagEditText = findViewById(R.id.tagEditText)
        fetchButton = findViewById(R.id.fetchButton)

        val regions = arrayOf("na", "eu", "ap", "kr", "latam", "br")
        val regionAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, regions)
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        regionSpinner.adapter = regionAdapter

        fetchButton.setOnClickListener {
            val region = regionSpinner.selectedItem.toString()
            val nickname = nicknameEditText.text.toString()
            val tag = tagEditText.text.toString()

            if (nickname.isNotEmpty() && tag.isNotEmpty()) {
                fetchValorantRank(region, nickname, tag)

                // Bersihkan inputan setelah melakukan permintaan
//                nicknameEditText.text.clear()
//                tagEditText.text.clear()
                showProgressDialog()
            } else {
                Toast.makeText(this, "Please enter a nickname and tag.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchValorantRank(region: String, nickname: String, tag: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val rankData = viewModel.fetchValorantRank(region, nickname, tag)
            runOnUiThread {
                hideProgressDialog()
                if (rankData != null) {
                    // Start ResultActivity to display the data
                    val intent = Intent(this@MainActivity, ResultActivity::class.java)
                    intent.putExtra("rank", rankData.rank)
                    intent.putExtra("rr", rankData.rr)
                    intent.putExtra("mmrElo", rankData.mmrElo)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "Failed to fetch Valorant rank.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage("Fetching data...")
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    private fun hideProgressDialog() {
        progressDialog?.dismiss()
    }
}
