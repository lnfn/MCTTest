package www.mytest.ru.mcttest.view

import www.mytest.ru.mcttest.entity.Result

/**
 * Created by Eugene on 21.04.2016.
 */
interface MainView {
    fun showProgressBar()
    fun hideProgressBar()
    fun showError(resId: Int)
    fun loadedList(resultList: List<Result>)
}