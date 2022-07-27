package id.gamedest.githubusersubmission2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.gamedest.githubusersubmission2.R
import id.gamedest.githubusersubmission2.viewmod.FollowingViewMod
import id.gamedest.githubusersubmission2.adapter.FollowingAdapter
import id.gamedest.githubusersubmission2.data.Following
import id.gamedest.githubusersubmission2.data.User
import kotlinx.android.synthetic.main.fragment_following.*

class FragmentFollowing : Fragment() {

    private var listData: ArrayList<Following> = ArrayList()
    private lateinit var adapter: FollowingAdapter
    private lateinit var followingViewModel: FollowingViewMod
    private lateinit var rvFollowing : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFollowing = view.findViewById(R.id.recyclerViewFollowing)

        setupRecyclerViewFollowing()

        adapter = FollowingAdapter(listData)
        followingViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewMod::class.java)

        val dataUser = requireActivity().intent.getParcelableExtra<User>(EXTRA_USER) as User?
        config()

        followingViewModel.getDataGit(
            requireActivity().applicationContext,
            dataUser?.username.toString()
        )
        showLoading(true)

        followingViewModel.getListFollowing().observe(requireActivity(), Observer { listFollowing ->
            if (listFollowing != null) {
                adapter.setData(listFollowing)
                showLoading(false)
            }
        })
    }

    private fun config() {
        recyclerViewFollowing.layoutManager = LinearLayoutManager(activity)
        recyclerViewFollowing.setHasFixedSize(true)
        recyclerViewFollowing.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressbarFollowing.visibility = View.VISIBLE
        } else {
            progressbarFollowing.visibility = View.INVISIBLE
        }
    }


    private fun setupRecyclerViewFollowing() {
        adapter = FollowingAdapter(arrayListOf())
        rvFollowing.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapter
        }
    }

    companion object {
        val TAG = FragmentFollowing::class.java.simpleName
        const val EXTRA_USER = "extra_user"
    }
}