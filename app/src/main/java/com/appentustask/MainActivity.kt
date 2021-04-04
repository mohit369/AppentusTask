package com.appentustask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appentustask.adapter.ResponseModelRvAdapter
import com.appentustask.interfaces.ApiInterface
import com.appentustask.model.ResponseGetModel
import com.appentustask.model.ResponseGetModelItem
import com.appentustask.utils.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity(), ApiCallback {

    private lateinit var recyclerView:RecyclerView
    private lateinit var progressBar:ProgressBar
    private lateinit var responseModelRvAdapter: ResponseModelRvAdapter
    private lateinit var responseGetModel:ResponseGetModel
    private  var responseLiveModel:ArrayList<ResponseGetModelItem> = ArrayList()
    private lateinit var layoutManager: GridLayoutManager
    private var page = 32
    private var isLoading = false
    private lateinit var database: DatabaseService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.main_rv)
        progressBar = findViewById(R.id.progressBar)
        database = DatabaseService(this)

        layoutManager = GridLayoutManager(this,2,RecyclerView.VERTICAL,false)

        recyclerView.layoutManager = layoutManager


        getListFromPagingAPI()

        /**
         * on addOnScrollListener getting visible item count on screen and check
         * it with the list coming from pagination API
         * if addition of both visible item count and past visible item count is >=
         * total count then we will call Api once again and add this to live data list
         */
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                val total = responseModelRvAdapter.itemCount

                if (!isLoading) {

                    if ((visibleItemCount + pastVisibleItem) >= total) {
                        if (page == 34 ){
                            progressBar.visibility = View.VISIBLE
                        }else{
                            page++
                            getListFromPagingAPI()
                        }

                    }else{
                        progressBar.visibility = View.GONE
                    }

                }

                super.onScrolled(recyclerView, dx, dy)
            }

        })


    }


    /**
     * function to get data from API
     */
    private fun getListFromPagingAPI() {
        progressBar.visibility = View.VISIBLE
        ApiCall.instance?.listItems(page,this)
    }


    @Suppress("UNCHECKED_CAST")
    override fun onSuccess(type: String, data: Any?) {
        val response:Response<ResponseGetModel> = data as Response<ResponseGetModel>
        progressBar.visibility = View.GONE
        if (response.isSuccessful){
            if (response.body()!!.isNotEmpty()){
                responseGetModel = response.body() as ResponseGetModel

                for (i in 0 until responseGetModel.size) {
                    responseLiveModel.add(responseGetModel[i])
                }

                if (::responseModelRvAdapter.isInitialized) {
                    responseModelRvAdapter.notifyDataSetChanged()
                } else {
                    responseModelRvAdapter = ResponseModelRvAdapter(responseLiveModel,this)
                    recyclerView.adapter = responseModelRvAdapter
                    responseModelRvAdapter.notifyDataSetChanged()
                }
                isLoading = false
                progressBar.visibility = View.GONE

                database.deleteAllData()
                database.insertnewdata(responseLiveModel as List<ResponseGetModelItem>)

            }
        }
    }


    override fun onFailure(data: Any?) {
        /**
         * if response is null or network is off
         */
        responseLiveModel = database.fetchData() as ArrayList<ResponseGetModelItem>
        if (database.fetchData().isNotEmpty()) {
            responseModelRvAdapter = ResponseModelRvAdapter(responseLiveModel, this)
            recyclerView.adapter = responseModelRvAdapter
            responseModelRvAdapter.notifyDataSetChanged()
        }else{
            progressBar.visibility = View.GONE
            Toast.makeText(this,"check your internet connection",Toast.LENGTH_SHORT).show()
        }
    }
}