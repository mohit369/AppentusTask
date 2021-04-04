package com.appentustask.utils

/**
 * Interface is used for common purpose in Application.
 *
 */
interface ApiCallback {
    /**
     * Method for getting the type and data.
     *
     * @param data Actual data
     */
    fun onSuccess( type:String,data: Any?)

    /**
     * Failure Reason
     * @param data
     */
    fun onFailure(data: Any?)
}