package www.mytest.ru.mcttest.presenter

import www.mytest.ru.mcttest.view.MainView

/**
 * Created by Eugene on 21.04.2016.
 */
interface IMainPresenter {
    fun setView(mainView: MainView)
    fun loadResult(query: String)
    fun onDestroy()
}