package id.gamedest.githubusersubmission2.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.gamedest.githubusersubmission2.CustomOnItemClickListener
import id.gamedest.githubusersubmission2.R
import id.gamedest.githubusersubmission2.data.UserFavorite
import id.gamedest.githubusersubmission2.user.DetailUser
import kotlinx.android.synthetic.main.gh_user.view.*

class UserFavoriteAdapter(private val activity: Activity) : RecyclerView.Adapter<UserFavoriteAdapter.NoteViewHolder>() {
    var listFavorite = java.util.ArrayList<UserFavorite>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gh_user, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int = this.listFavorite.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(fav: UserFavorite) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(fav.avatar)
                    .apply(RequestOptions().override(250, 250))
                    .into(itemView.img_user_avatar)
                tv_username.text = fav.username
                tv_name.text = fav.name
                tv_company.text = fav.company.toString()
                tv_location2.text = fav.location
                itemView.setOnClickListener(
                    CustomOnItemClickListener(
                        adapterPosition,
                        object : CustomOnItemClickListener.OnItemClickCallback {
                            override fun onItemClicked(view: View, position: Int) {
                                val intent = Intent(activity, DetailUser::class.java)
                                intent.putExtra(DetailUser.EXTRA_POSITION, position)
                                intent.putExtra(DetailUser.EXTRA_NOTE, fav)
                                activity.startActivity(intent)
                                activity.finish()
                            }
                        }
                    )
                )
            }
        }
    }
}