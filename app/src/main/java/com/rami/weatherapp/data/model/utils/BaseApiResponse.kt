package com.rami.weatherapp.data.model.utils

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.rami.weatherapp.R
import com.rami.weatherapp.utils.ApiStatus
import com.rami.weatherapp.utils.UN_AUTHORIZED_CODE
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * Created by Rami El-bouhi on 30,November,2021
 */
class BaseApiResponse <T>(
    val apiStatus: ApiStatus = ApiStatus.SUCCESS,
    val success: Boolean? = false,
    val data: T? = null,
    var message: String? = null,
    val error: CustomError? = null)
{
    companion object {
        fun <T> loading(): BaseApiResponse<T> {
            return BaseApiResponse(apiStatus = ApiStatus.LOADING)
        }

        fun <T> success(
            message: String? = null,
            data: T? = null
        ): BaseApiResponse<T> {
            return BaseApiResponse(
                apiStatus = ApiStatus.SUCCESS,
                success = true,
                data = data,
                message = message
            )
        }

        fun <T> error(customError: CustomError? = null): BaseApiResponse<T> {
            return BaseApiResponse(apiStatus = ApiStatus.ERROR, error = customError)
        }

        private fun <T> noInternetAccess(customError: CustomError?): BaseApiResponse<T> {
            return BaseApiResponse(apiStatus = ApiStatus.NO_INTERNET, error = customError)
        }

        private fun <T> authorizationError(customError: CustomError?): BaseApiResponse<T> {
            return BaseApiResponse(apiStatus = ApiStatus.UN_AUTHORIZED, error = customError)
        }

        fun <T> emptyData(): BaseApiResponse<T> {
            return BaseApiResponse(apiStatus = ApiStatus.EMPTY_DATA)
        }

        fun <T> handleError(error: Throwable): BaseApiResponse<T> {
            when (error) {
                is HttpException -> {
                    val body: ResponseBody? = error.response().errorBody()
                    val adapter: TypeAdapter<BaseApiResponse<*>> =
                        Gson().getAdapter(BaseApiResponse::class.java)
                    return try {
                        val errorParser: BaseApiResponse<*> = adapter.fromJson(body!!.string())
                        if (error.response().code() == UN_AUTHORIZED_CODE) {
                            authorizationError(errorParser.error)
                        } else {
//                            errorParser.error?.errorCode = error.response().code()
                            error(CustomError(message = errorParser.message, errorCode = error.response().code()))
                        }
                    } catch (ex: IOException) {
                        error(CustomError(message = R.string.error_happend))
                    }
                }
                else -> {
                    return noInternetAccess(CustomError(message = R.string.no_internet_connection))
                }
            }
        }

        fun <T> handleError(response: Response<*>): BaseApiResponse<T> {
            val body: ResponseBody? = response.errorBody()
            val adapter: TypeAdapter<BaseApiResponse<*>> =
                Gson().getAdapter(BaseApiResponse::class.java)
            return try {
                val errorParser: BaseApiResponse<*> = adapter.fromJson(body!!.string())
                if (response.code() == UN_AUTHORIZED_CODE) {
                    authorizationError(errorParser.error)
                } else {
                    error(errorParser.error)
                }
            } catch (ex: IOException) {
                error(CustomError(message = R.string.error_happend))
            }
        }
    }
}