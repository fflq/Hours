package com.example.hours.home.task

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hours.R
import com.example.hours.home.data.Task
import kotlinx.android.synthetic.main.task_card_item.view.*

class TaskAdapter(var mResources: Resources): ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_card_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        var task = getItem(position)
        holder.itemView.tvTaskID.text = (position+1).toString()
        holder.itemView.tvTaskName.text = task.name
        holder.itemView.tvTaskTime.text = task.time.toString()
        holder.itemView.ivTask.setImageDrawable(mResources.getDrawable(task.drawableId, null))
        holder.itemView.setOnClickListener {
        }
    }


    /*
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        this.onItemClickListener.onItemClick(parent, view, position, id)
    }
     */


    class TaskViewHolder(view: View): RecyclerView.ViewHolder(view)


    class TaskDiffCallback: DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return (oldItem.id == newItem.id)
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return (oldItem == newItem)
        }
    }

}

