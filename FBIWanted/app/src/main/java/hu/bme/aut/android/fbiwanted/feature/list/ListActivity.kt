package hu.bme.aut.android.fbiwanted.feature.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.fbiwanted.databinding.ActivityListBinding
import hu.bme.aut.android.fbiwanted.feature.details.DetailsActivity
import hu.bme.aut.android.fbiwanted.model.WantedListData
import hu.bme.aut.android.fbiwanted.model.WantedPersonData
import hu.bme.aut.android.fbiwanted.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivity : AppCompatActivity(), ListAdapter.OnPersonSelectedListener {
    private lateinit var binding: ActivityListBinding
    private lateinit var adapter: ListAdapter
    private var page = 1
    private val pageSize = 20
    private var items = 0

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mainRecyclerView.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                if (page * pageSize < items + pageSize)
                    addDataToList()
            }
        })
        loadWantedListData()
    }

    private fun loadWantedListData() {
        NetworkManager.getWantedList(pageSize, page)?.enqueue(object : Callback<WantedListData?> {
            override fun onResponse(
                call: Call<WantedListData?>,
                response: Response<WantedListData?>
            ) {
                Log.d(TAG, "onResponse: " + response.code())
                if (response.isSuccessful) {
                    displayWantedListData(response.body())
                    page += 1
                    items = response.body()?.total!!
                } else {
                    Toast.makeText(
                        this@ListActivity,
                        "Error: " + response.message(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(
                call: Call<WantedListData?>,
                throwable: Throwable
            ) {
                throwable.printStackTrace()
                Toast.makeText(
                    this@ListActivity,
                    "Network request error occurred, check LOG",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun displayWantedListData(wantedListData: WantedListData?) {
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        val wantedPeople = wantedListData?.items!!
        adapter = ListAdapter(wantedPeople, this)
        binding.mainRecyclerView.adapter = adapter
    }

    private fun addDataToList() {
        binding.itemProgressBar.visibility = VISIBLE
        NetworkManager.getWantedList(pageSize, page)?.enqueue(object : Callback<WantedListData?> {
            override fun onResponse(
                call: Call<WantedListData?>,
                response: Response<WantedListData?>
            ) {
                Log.d(TAG, "onResponse: " + response.code())
                if (response.isSuccessful) {
                    for (person in response.body()?.items!!) {
                        adapter.addItem(person)
                    }
                    page += 1
                    binding.itemProgressBar.visibility = GONE
                } else {
                    Toast.makeText(
                        this@ListActivity,
                        "Error: " + response.message(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(
                call: Call<WantedListData?>,
                throwable: Throwable
            ) {
                throwable.printStackTrace()
                Toast.makeText(
                    this@ListActivity,
                    "Network request error occurred, check LOG",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    override fun onPersonSelected(person: WantedPersonData) {
        val showDetailsIntent = Intent()
        showDetailsIntent.setClass(this@ListActivity, DetailsActivity::class.java)
        showDetailsIntent.putExtra(DetailsActivity.EXTRA_PERSON_ID, person.uid)
        startActivity(showDetailsIntent)
    }
}