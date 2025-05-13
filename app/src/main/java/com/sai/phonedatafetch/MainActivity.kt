package com.sai.phonedatafetch

import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sai.phonedatafetch.database.PhoneModel
import com.sai.phonedatafetch.database.repository
import com.sai.phonedatafetch.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mobilesList: List<PhoneModel> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = repository(application)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        setDB(this,repo)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val year: Int = Calendar.getInstance().get(Calendar.YEAR)
                    lifecycleScope.launch(Dispatchers.IO) {
                        val isBrand = repo.isBranded("%$it%")
                        mobilesList = if (isBrand) {
                            repo.getMobiles(MOBILE_BRAND, "%$it%", year)
                        } else {
                            repo.getMobiles(MOBILE_NAME, "%$it%", year)
                        }

                        withContext(Dispatchers.Main) {
                            if (mobilesList.isNotEmpty()) {
                                Toast.makeText(this@MainActivity, "Got data", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@MainActivity, "Empty", Toast.LENGTH_SHORT).show()
                            }
                            binding.recyclerView.adapter = SearchAdapter(this@MainActivity, mobilesList)
                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.searchView.setOnSearchClickListener {
            val params = binding.searchView.layoutParams as ConstraintLayout.LayoutParams
            params.width = ConstraintLayout.LayoutParams.MATCH_PARENT
            binding.searchView.layoutParams = params
        }

        binding.searchView.setOnCloseListener {
            val params = binding.searchView.layoutParams as ConstraintLayout.LayoutParams
            params.width = ConstraintLayout.LayoutParams.WRAP_CONTENT
            binding.searchView.layoutParams = params
            false
        }
        repo.getMobileBrandsLD().observe(this@MainActivity) { brands ->
            binding.text.text = brands.joinToString(separator = "\n")
        }
    }
    fun setDB(context: Context,repo:repository){
        val prefs = context.getSharedPreferences("app_prefs", MODE_PRIVATE)
        val isFirstRun = prefs.getBoolean("first_run_done", false)

        if (!isFirstRun) {
            CoroutineScope(Dispatchers.IO).launch {
                // Insert your data
                val mobiles = readCSV(context)

                repo.addMobiles(mobiles)

                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Devices inserted successfully", Toast.LENGTH_SHORT).show()
                }
                // Set the flag so this block won't run again
                prefs.edit { putBoolean("first_run_done", true) }
            }
        }
    }
}
