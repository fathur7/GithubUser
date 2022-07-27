package id.gamedest.githubusersubmission2.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.gamedest.githubusersubmission2.R
import id.gamedest.githubusersubmission2.data.User
import id.gamedest.githubusersubmission2.user.DetailUser
import kotlinx.android.synthetic.main.gh_user.view.*
import kotlinx.android.synthetic.main.gh_user.view.img_user_avatar
import kotlinx.android.synthetic.main.gh_user.view.tv_company
import kotlinx.android.synthetic.main.gh_user.view.tv_followers_number2
import kotlinx.android.synthetic.main.gh_user.view.tv_following_number2
import kotlinx.android.synthetic.main.gh_user.view.tv_name
import kotlinx.android.synthetic.main.gh_user.view.tv_repository_number
import kotlinx.android.synthetic.main.gh_user.view.tv_username

class ListUserAdapter(private val listDataUsersGithub: ArrayList<User>) :
    RecyclerView.Adapter<ListUserAdapter.ListDataHolder>() {

    fun setData(items: ArrayList<User>) {
        listDataUsersGithub.clear()
        listDataUsersGithub.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataUsers: User) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(dataUsers.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(img_user_avatar)

                tv_name.text = dataUsers.name
                tv_username.text = dataUsers.username
                tv_repository_number.text = dataUsers.repository
                tv_followers_number2.text = dataUsers.followers
                tv_following_number2.text = dataUsers.following
                tv_company.text = dataUsers.company
                tv_location2.text = dataUsers.location
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.gh_user, parent, false)
        return ListDataHolder(view)
    }

    override fun getItemCount(): Int = listDataUsersGithub.size


    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        holder.bind(listDataUsersGithub[position])
        val data = listDataUsersGithub[position]
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