package com.mydigipay.challenge.presentation.ui.fragments.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mydigipay.challenge.R
import com.mydigipay.challenge.data.model.GithubUserModel
import com.mydigipay.challenge.utils.CircleImageView

class SearchAdapter(private val  listener: SearchAdapter.SearchAdapterInteraction): RecyclerView.Adapter<SearchAdapter.ViewHolder> (){

    lateinit var context: Context
    private  var usersModel = ArrayList<GithubUserModel>()

    interface SearchAdapterInteraction {
        fun onUserItemClick(login: String)
    }

    fun updateUsers(users: List<GithubUserModel>){
        usersModel.clear()
        usersModel.addAll(users)
        notifyDataSetChanged()
    }

    fun clearUsers(){
        usersModel.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.search_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =  usersModel.size

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        val githubUserModel = usersModel.get(position)
        githubUserModel?.let {
            Glide.with(context)
                .load(it.avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.ivProfile)

            holder.tvName.text = it.login
            holder.tvDescription.text = it.htmlUrl


            holder.userItem.setOnClickListener {
                listener.onUserItemClick(githubUserModel.login)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userItem : RelativeLayout = itemView.findViewById(R.id.user_item)
        val ivProfile: CircleImageView = itemView.findViewById(R.id.ivProfile)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
    }
}