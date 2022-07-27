package id.gamedest.githubusersubmission2.user

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.gamedest.githubusersubmission2.R
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.COLUMN_AVATAR
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.COLUMN_COMPANY
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.COLUMN_LOCATION
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.COLUMN_NAME
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.COLUMN_TYPE
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.COLUMN_USERNAME
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.CONTENT_URI
import id.gamedest.githubusersubmission2.data.User
import id.gamedest.githubusersubmission2.data.UserFavorite
import id.gamedest.githubusersubmission2.helper.UserFavoriteHelper
import id.gamedest.githubusersubmission2.setting.SettingActivity
import kotlinx.android.synthetic.main.activity_detail_user.*
import kotlinx.android.synthetic.main.gh_user.img_user_avatar
import kotlinx.android.synthetic.main.gh_user.tv_company
import kotlinx.android.synthetic.main.gh_user.tv_name
import kotlinx.android.synthetic.main.gh_user.tv_repository_number
import kotlinx.android.synthetic.main.gh_user.tv_username

class DetailUser : AppCompatActivity(), View.OnClickListener  {

    private lateinit var imageAvatar: String
    private lateinit var username: String
    private lateinit var userType: String
    private lateinit var userFavoriteHelper: UserFavoriteHelper
    private var isFavoriteState = false
    private var favorites: UserFavorite? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            viewpager.layoutParams.height = resources.getDimension(R.dimen.height).toInt()
        }

        if (supportActionBar != null) {
            supportActionBar?.title = "Detail User"
        }

        userFavoriteHelper = UserFavoriteHelper.getInstance(applicationContext)
        userFavoriteHelper.open()

        favorites = intent.getParcelableExtra(EXTRA_NOTE)
        if (favorites != null) {
            setDataObject()
            isFavoriteState = true
            val checked: Int = R.drawable.ic_baseline_favorite_24
            fab_favorite.setImageResource(checked)
        } else {
            setData()
        }
        viewPagerConfig()
        fab_favorite.setOnClickListener(this)
        checkUserFavorite()
    }


    private fun deleteUserFavorite() {
        userFavoriteHelper = UserFavoriteHelper.getInstance(applicationContext)
        userFavoriteHelper.open()
        if (userFavoriteHelper.checkUsername(username)) userFavoriteHelper.deleteByUsername(username)
        Toast.makeText(this@DetailUser,
            "$username ${getString(R.string.remove_github_user)}",
            Toast.LENGTH_SHORT).show()
    }

    private fun checkUserFavorite() {
        userFavoriteHelper = UserFavoriteHelper.getInstance(applicationContext)
        if (userFavoriteHelper.checkUsername(username)) {
            isFavoriteState = true
            setFavoriteStatus(isFavoriteState)
        }
    }
    private fun setFavoriteStatus(state: Boolean) {
        if (state) fab_favorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        else fab_favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
    }
    @SuppressLint("SetTextI18n")
    private fun setDataObject() {
        val favoriteUser = intent.getParcelableExtra(EXTRA_NOTE) as UserFavorite?
        tv_name.text = favoriteUser?.name
        tv_username.text = favoriteUser?.username
        tv_company.text = favoriteUser?.company
        tv_location.text = favoriteUser?.location
        Glide.with(this)
            .load(favoriteUser?.avatar)
            .into(img_user_avatar)
        imageAvatar = favoriteUser?.avatar.toString()
        userType = favoriteUser?.type.toString()
        username = favoriteUser?.username.toString()
    }

    override fun onClick(view: View) {
        val checked: Int = R.drawable.ic_baseline_favorite_24
        val unChecked: Int = R.drawable.ic_baseline_favorite_border_24
        if (view.id == R.id.fab_favorite) {
            if (isFavoriteState) {
                userFavoriteHelper.deleteByUsername(favorites?.username.toString())
                Toast.makeText(this, getString(R.string.remove_github_user), Toast.LENGTH_SHORT).show()
                fab_favorite.setImageResource(unChecked)
                isFavoriteState = false
                deleteUserFavorite()
            } else {
                val dataUsername = tv_username.text.toString()
                val dataName = tv_name.text.toString()
                val dataAvatar = imageAvatar
                val dataType = userType
                val datacompany = tv_company.text.toString()
                val dataLocation = tv_location.text.toString()

                val values = ContentValues()
                values.put(COLUMN_USERNAME, dataUsername)
                values.put(COLUMN_NAME, dataName)
                values.put(COLUMN_AVATAR, dataAvatar)
                values.put(COLUMN_COMPANY, datacompany)
                values.put(COLUMN_LOCATION, dataLocation)
                values.put(COLUMN_TYPE, dataType)

                isFavoriteState = true
                contentResolver.insert(CONTENT_URI, values)
                Toast.makeText(this, getString(R.string.insert_github_user), Toast.LENGTH_SHORT).show()
                fab_favorite.setImageResource(checked)
            }
        }
    }

    private fun viewPagerConfig() {
        val sectionsPagerAdapter = ViewPager(this)
        val viewPager: ViewPager2 = findViewById(R.id.viewpager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun setData() {
        val dataUser = intent.getParcelableExtra<User>(EXTRA_USER) as User?
        Glide.with(this)
            .load(dataUser?.avatar)
            .apply(RequestOptions().override(150, 130))
            .into(img_user_avatar)
        tv_name.text = dataUser?.name
        tv_username.text = dataUser?.username
        tv_company.text = dataUser?.company
        tv_location.text = dataUser?.location
        tv_following_number.text = dataUser?.following
        tv_followers_number.text = dataUser?.followers
        tv_repository_number.text = dataUser?.repository
        imageAvatar = dataUser?.avatar.toString()
        userType = dataUser?.type.toString()
        username = dataUser?.username.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings -> {
                val intent = Intent(this@DetailUser, SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.gh_following,
            R.string.gh_followers
        )
        const val EXTRA_AVATAR = "extra_avatar"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
    }
}