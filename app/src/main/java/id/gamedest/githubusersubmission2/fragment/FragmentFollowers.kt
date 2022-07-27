package id.gamedest.githubusersubmission2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient.log
import id.gamedest.githubusersubmission2.R
import id.gamedest.githubusersubmission2.viewmod.FollowersViewMod
import id.gamedest.githubusersubmission2.adapter.FollowersAdapter
import id.gamedest.githubusersubmission2.data.Followers
import id.gamedest.githubusersubmission2.data.User
import kotlinx.android.synthetic.main.fragment_followers.*


class FragmentFollowers : Fragment() {

    private val listData: ArrayList<Followers> = ArrayList()
    private lateinit var adapter: FollowersAdapter
    private lateinit var followerViewModel: FollowersViewMod

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowersAdapter(listData)
        followerViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewMod::class.java)

        val dataUser = requireActivity().intent.getParcelableExtra<User>(EXTRA_USER) as User?
        config()

        followerViewModel.getDataGit(requireActivity().applicationContext, dataUser?.username.toString())
        showLoading(true)

        followerViewModel.getListFollower().observe(requireActivity(), Observer { listFollower ->
            log.d("TES_LIST", "listFollower")
            if (listFollower != null) {
                adapter.setData(listFollower)
                showLoading(false)
            }
        })
    }

    private fun config() {
        recyclerViewFollowers.layoutManager = LinearLayoutManager(activity)
        recyclerViewFollowers.setHasFixedSize(true)
        recyclerViewFollowers.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressbarFollowers.visibility = View.VISIBLE
        } else {
            progressbarFollowers.visibility = View.INVISIBLE
        }
    }

    companion object {
        val TAG = FragmentFollowers::class.java.simpleName
        const val EXTRA_USER = "extra_user"
    }
}
