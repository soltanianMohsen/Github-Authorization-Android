package com.mydigipay.challenge.presentation.ui.fragments.branche

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mydigipay.challenge.R
import com.mydigipay.challenge.data.model.GithubBranchesModel

class BranchAdapter(private val  listener: BranchAdapter.BranchAdapterInteraction): RecyclerView.Adapter<BranchAdapter.ViewHolder> (){

    lateinit var context: Context
    private  var models = ArrayList<GithubBranchesModel>()

    interface BranchAdapterInteraction {
        fun onBranchItemClick(url: String)
    }

    fun updateBranches(users: List<GithubBranchesModel>){
        models.clear()
        models.addAll(users)
        notifyDataSetChanged()
    }

    fun clearBranches(){
        models.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchAdapter.ViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.branch_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =  models.size

    override fun onBindViewHolder(holder: BranchAdapter.ViewHolder, position: Int) {
        val githubBranchesModel = models[position]
        githubBranchesModel?.let {

            holder.tvName.text = it.name
            holder.tvDescription.text = it.commit.url


            holder.branchItem.setOnClickListener {
                listener.onBranchItemClick(githubBranchesModel.commit.url)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val branchItem : RelativeLayout = itemView.findViewById(R.id.branch_item)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
    }
}