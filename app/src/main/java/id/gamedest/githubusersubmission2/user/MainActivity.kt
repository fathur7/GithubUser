package id.gamedest.githubusersubmission2.user

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.gamedest.githubusersubmission2.R
import id.gamedest.githubusersubmission2.viewmod.ViewMod
import id.gamedest.githubusersubmission2.adapter.ListUserAdapter
import id.gamedest.githubusersubmission2.data.User
import id.gamedest.githubusersubmission2.setting.SettingActivity
import id.gamedest.githubusersubmission2.setting.SettingPref
import id.gamedest.githubusersubmission2.viewmod.SettingViewMod
import id.gamedest.githubusersubmission2.viewmod.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private var listDataUser = ArrayList<User>()
    private lateinit var listAdapter: ListUserAdapter
    private lateinit var mainViewModel: ViewMod

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listAdapter = ListUserAdapter(listDataUser)
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ViewMod::class.java]

        searchUsername(applicationContext)
        val pref = SettingPref.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewMod::class.java]
        settingViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        viewConfig()
        runGetDataGit()
        configMainViewModel(listAdapter)
    }

    private fun viewConfig() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        listAdapter = ListUserAdapter(listDataUser)
        recyclerView.adapter = listAdapter
        listAdapter.notifyDataSetChanged()
        recyclerView.setHasFixedSize(true)
    }

    private fun runGetDataGit() {
        mainViewModel.getDataGit(applicationContext)
        showLoading(true)
    }

    private fun configMainViewModel(adapter: ListUserAdapter) {
        mainViewModel.getListUsers().observe(this, Observer { listUsers ->
            if (listUsers != null) {
                adapter.setData(listUsers)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            prog_bar.visibility = View.VISIBLE
        } else {
            prog_bar.visibility = View.INVISIBLE
        }
    }

    private fun searchUsername(context: Context) {
        user_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isNotEmpty()!!) {
                    newText.let { mainViewModel.getDataGitSearch(it, context) }
                    showLoading(true)
                } else {
                    showLoading(false)
                    listDataUser.clear()
                }
                return true
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> {
                val intent = Intent(this@MainActivity, FavoritesActivityMain::class.java)
                startActivity(intent)
            }
            R.id.settings -> {
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }
}
