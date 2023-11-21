package hu.bme.aut.android.fbiwanted.feature.details

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.android.fbiwanted.databinding.ActivityDetailsBinding
import hu.bme.aut.android.fbiwanted.model.WantedPersonData
import hu.bme.aut.android.fbiwanted.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity(), WantedPersonDataHolder {
    private lateinit var binding: ActivityDetailsBinding
    private var personId: String? = null
    private var wantedPersonData: WantedPersonData? = null

    companion object {
        private const val TAG = "DetailsActivity"
        const val EXTRA_PERSON_ID = "extra.person_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        personId = intent.getStringExtra(EXTRA_PERSON_ID)
    }

    override fun onResume() {
        super.onResume()
        val detailsPagerAdapter = DetailsPagerAdapter(this)
        binding.mainViewPager.adapter = detailsPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.mainViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Profile"
                1 -> "Details"
                else -> ""
            }
        }.attach()
        loadWantedPersonData()
    }

    override fun getWantedPersonData(): WantedPersonData? {
        return wantedPersonData
    }

    private fun displayWantedPersonData(receivedWantedPersonData: WantedPersonData?) {
        wantedPersonData = receivedWantedPersonData
        val detailsPagerAdapter = DetailsPagerAdapter(this)
        binding.mainViewPager.adapter = detailsPagerAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadWantedPersonData() {
        NetworkManager.getWantedPerson(personId)?.enqueue(object : Callback<WantedPersonData?> {
            override fun onResponse(
                call: Call<WantedPersonData?>,
                response: Response<WantedPersonData?>
            ) {
                Log.d(TAG, "onResponse: " + response.code())
                if (response.isSuccessful) {
                    displayWantedPersonData(response.body())
                    supportActionBar?.let { actionBar ->
                        wantedPersonData?.title?.let {
                            actionBar.title = it
                        }
                        actionBar.setDisplayHomeAsUpEnabled(true)
                    }
                } else {
                    Toast.makeText(
                        this@DetailsActivity,
                        "Error: " + response.message(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(
                call: Call<WantedPersonData?>,
                throwable: Throwable
            ) {
                throwable.printStackTrace()
                Toast.makeText(
                    this@DetailsActivity,
                    "Network request error occurred, check LOG",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}