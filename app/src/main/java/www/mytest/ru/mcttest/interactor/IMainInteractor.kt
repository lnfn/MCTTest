package www.mytest.ru.mcttest.interactor

import rx.Observable
import www.mytest.ru.mcttest.network.ResultResponse

/**
 * Created by Eugene on 21.04.2016.
 */
interface IMainInteractor {
    fun getResult(query: String): Observable<ResultResponse>
}