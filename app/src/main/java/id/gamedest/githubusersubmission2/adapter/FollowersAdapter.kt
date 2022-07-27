package id.gamedest.githubusersubmission2.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.gamedest.githubusersubmission2.R
import id.gamedest.githubusersubmission2.data.Followers
import kotlinx.android.synthetic.main.activity_detail_user.view.img_user_avatar
import kotlinx.android.synthetic.main.activity_detail_user.view.tv_name
import kotlinx.android.synthetic.main.activity_detail_user.view.tv_repository_number
import kotlinx.android.synthetic.main.activity_detail_user.view.tv_username
import kotlinx.android.synthetic.main.gh_user.view.*

class FollowersAdapter(private val listDataFollower: ArrayList<Followers>) :
    RecyclerView.Adapter<FollowersAdapter.ListDataHolder>() {

    fun setData(items: ArrayList<Followers>) {
        listDataFollower.clear()
        listDataFollower.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("StringFormatInvalid")
        fun bind(dataFollowers: Followers) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(dataFollowers.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(img_user_avatar)

                tv_name.text = dataFollowers.name
                tv_username.text = itemView.context.getString(R.string.gh_usernmae, dataFollowers.username)
                tv_repository_number.text = itemView.context.getString(R.string.gh_repository, dataFollowers.repository)
                tv_followers_number2.text = itemView.context.getString(R.string.gh_followers, dataFollowers.followers)
                tv_following_number2.text = itemView.context.getString(R.string.gh_following, dataFollowers.following)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataHolder {
        return ListDataHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.gh_user, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listDataFollower.size
    }

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        holder.bind(listDataFollower[position])
    }
}