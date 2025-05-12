package com.sai.phonedatafetch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.sai.phonedatafetch.database.repository
import com.sai.phonedatafetch.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = repository(application)



        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let{
                    binding.text.text = it
                    if (it.equals("load")){
                        lifecycleScope.launch(Dispatchers.IO) {
                            val mobiles = readCSV(this@MainActivity)

                            repo.addMobiles(mobiles)

                            withContext(Dispatchers.Main) {
                                Toast.makeText(applicationContext, "Devices inserted successfully", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

       lifecycleScope.launch(Dispatchers.IO) {
           val brands = repo.getMobileBrands()
           withContext(Dispatchers.Main) {
               binding.text.text = brands.joinToString(separator = "\n")
           }

       }
    }
}