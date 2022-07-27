package id.gamedest.githubusersubmission2.user

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.gamedest.githubusersubmission2.fragment.FragmentFollowers
import id.gamedest.githubusersubmission2.fragment.FragmentFollowing

class ViewPager(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    private val pages = listOf(
        FragmentFollowing(),
        FragmentFollowers()
    )

    override fun getItemCount(): Int {
        return pages.size
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FragmentFollowing()
            1 -> fragment = FragmentFollowers()
        }
        return fragment as Fragment
    }
}