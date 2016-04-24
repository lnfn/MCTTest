package www.mytest.ru.mcttest.interactor

import android.util.Log
import com.google.gson.Gson
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import www.mytest.ru.mcttest.network.APIWebService
import www.mytest.ru.mcttest.network.ResultResponse

/**
 * Created by Eugene on 21.04.2016.
 */
open class MainInteractor(val apiWebService: APIWebService): IMainInteractor {
    override fun getResult(query: String): Observable<ResultResponse> {
        return apiWebService.getResultObservable(query)
                .flatMap {
                    val str = it.string()
                    Log.d("STR", str)
                    val res = Gson().fromJson(str, ResultResponse::class.java)
                    Log.d("CLS", "size " + res.results.size)
                    Observable.just(res)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}