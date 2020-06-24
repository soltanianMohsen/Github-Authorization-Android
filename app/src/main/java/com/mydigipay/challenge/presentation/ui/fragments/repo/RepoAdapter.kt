package com.mydigipay.challenge.presentation.ui.fragments.repo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mydigipay.challenge.R
import com.mydigipay.challenge.data.model.GithubRepoModel

class RepoAdapter(private val  listener: RepoAdapter.RepoAdapterInteraction): RecyclerView.Adapter<RepoAdapter.ViewHolder> (){

    lateinit var context: Context
    private  var models = ArrayList<GithubRepoModel>()

    interface RepoAdapterInteraction {
        fun onRepoItemClick(name: String, repo: String)
    }

    fun updateRepo(repos: List<GithubRepoModel>){
        models.clear()
        models.addAll(repos)
        notifyDataSetChanged()
    }

    fun clearRepo(){
        models.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoAdapter.ViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.repo_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =  models.size

    override fun onBindViewHolder(holder: RepoAdapter.ViewHolder, position: Int) {
        val githubRepoModel = models[position]
        githubRepoModel?.let {

            holder.tvName.text = it.name
            holder.tvDescription.text = it.htmlUrl

            holder.repoItem.setOnClickListener {
                listener.onRepoItemClick(githubRepoModel.owner.login,githubRepoModel.name)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repoItem : RelativeLayout = itemView.findViewById(R.id.repo_item)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
    }
}