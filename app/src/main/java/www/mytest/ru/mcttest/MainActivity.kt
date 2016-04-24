package www.mytest.ru.mcttest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import www.mytest.ru.mcttest.adapter.ResultRecyclerViewAdapter
import www.mytest.ru.mcttest.entity.Result
import www.mytest.ru.mcttest.presenter.MainPresenter
import www.mytest.ru.mcttest.view.MainView
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    private var resultList = listOf<Result>()
    private val adapter by lazy { ResultRecyclerViewAdapter(resultList, this) }

    @Inject lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        App.getAppComponent().inject(this)
        mainPresenter.setView(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val actionItem = menu.findItem(R.id.action_search)
        val searchView = actionItem.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String): Boolean {
                mainPresenter.loadResult(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                mainPresenter.loadResult(query)
                actionItem.collapseActionView()
                return true
            }
        })
        return true
    }

    override fun showError(resId: Int) {
        if(messageTextView.visibility == View.GONE) messageTextView.visibility = View.VISIBLE;
        messageTextView.text = getString(resId)
    }

    override fun loadedList(resultList: List<Result>) {
        if(recyclerView.visibility == View.GONE) recyclerView.visibility = View.VISIBLE;
        this.resultList = resultList
        adapter.refreshData(this.resultList)
    }

    override fun showProgressBar() {
        if(recyclerView.visibility == View.VISIBLE) recyclerView.visibility = View.GONE;
        if(messageTextView.visibility == View.VISIBLE) messageTextView.visibility = View.GONE;
        if(progressBar.visibility == View.GONE) progressBar.visibility = View.VISIBLE;
    }

    override fun hideProgressBar() {
        if(progressBar.visibility == View.VISIBLE) progressBar.visibility = View.GONE;
    }

    override fun onDestroy() {
        mainPresenter.onDestroy()
        super.onDestroy()
    }
}