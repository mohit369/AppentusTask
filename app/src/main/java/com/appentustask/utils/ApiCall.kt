package com.appentustask.utils


import com.appentustask.interfaces.ApiInterface
import com.appentustask.model.ResponseGetModel
import com.appentustask.utils.ApiClient.client
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * singleton class to all API method
 */

class ApiCall<Req, Res> private constructor() {

    /**
     * Method to get data from API
     *
     * @param req
     * @param iApiCallback
     */


    companion object {
        var apiCall: ApiCall<*, *>? = null
        var service: ApiInterface? = null
        val instance: ApiCall<*, *>?
            get() {
                if (apiCall == null) {
                    apiCall = ApiCall<Any?, Any?>()
                    service = client
                }
                return apiCall
            }
    }

    fun listItems(page: Int, iApiCallback: ApiCallback) {
        val call: Call<ResponseGetModel> = service!!.getPagingData(page)
        call.enqueue(object : Callback<ResponseGetModel?> {
            override fun onResponse(
                call: Call<ResponseGetModel?>,
                response: Response<ResponseGetModel?>
            ) {
                iApiCallback.onSuccess("", response)
            }

            override fun onFailure(
                call: Call<ResponseGetModel?>,
                t: Throwable
            ) {
                iApiCallback.onFailure(t.message)
            }
        })
    }

}