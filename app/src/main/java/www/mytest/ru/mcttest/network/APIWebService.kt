package www.mytest.ru.mcttest.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import rx.Observable

/**
 * Created by Eugene on 24.03.2016.
 */
interface APIWebService {
    @Streaming
    @GET("/search?media=music")
    fun getResultObservable(@Query("term") term: String): Observable<ResponseBody>
}