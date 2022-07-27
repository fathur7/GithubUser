package id.gamedest.githubusersubmission2.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.gamedest.githubusersubmission2.R
import id.gamedest.githubusersubmission2.data.Following
import id.gamedest.githubusersubmission2.data.User
import id.gamedest.githubusersubmission2.user.DetailUser
import kotlinx.android.synthetic.main.activity_detail_user.view.img_user_avatar
import kotlinx.android.synthetic.main.activity_detail_user.view.tv_name
import kotlinx.android.synthetic.main.activity_detail_user.view.tv_repository_number
import kotlinx.android.synthetic.main.activity_detail_user.view.tv_username
import kotlinx.android.synthetic.main.gh_user.view.*

class FollowingAdapter (private val listDataFollowing: ArrayList<Following>) :
    RecyclerView.Adapter<FollowingAdapter.ListDataHolder>() {

    fun setData(item: ArrayList<Following>) {
        listDataFollowing.clear()
        listDataFollowing.addAll(item)
        notifyDataSetChanged()
    }

    inner class ListDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("StringFormatInvalid")
        fun bind(dataFollowing: Following) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(dataFollowing.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(img_user_avatar)

                tv_name.text = dataFollowing.name
                tv_username.text = itemView.context.getString(R.string.gh_usernmae, dataFollowing.username)
                tv_repository_number.text = itemView.context.getString(R.string.gh_repository, dataFollowing.repository)
                tv_followers_number2.text = itemView.context.getString(R.string.gh_followers, dataFollowing.followers)
                tv_following_number2.text = itemView.context.getString(R.string.gh_following, dataFollowing.following)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.gh_user, parent, false)
        return ListDataHolder(view)
    }

    override fun getItemCount(): Int = listDataFollowing.size

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        holder.bind(listDataFollowing[position])
        val data = listDataFollowing[position]
        holder.itemView.setOnClickListener {
            val dataUserIntent = User(
                data.username,
                data.name,
                data.avatar,
                data.company,
                data.location,
                data.repository,
                data.followers,
                data.following
            )
            val mIntent = Intent(it.context, DetailUser::class.java)
            mIntent.putExtra(DetailUser.EXTRA_USER, dataUserIntent)
            it.context.startActivity(mIntent)
        }
    }
}