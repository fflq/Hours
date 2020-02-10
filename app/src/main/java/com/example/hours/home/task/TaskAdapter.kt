package com.example.hours.home.task

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hours.R
import com.example.hours.home.data.Task
import kotlinx.android.synthetic.main.task_card_item.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class TaskAdapter(var mContext: Context?): ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {
    var onItemClickListener: OnTaskClickListener? = null
    var onItemAddTimeClickListener: OnTaskClickListener? = null

    interface OnTaskClickListener {
        fun onClick(v: View?, task: Task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_card_item, parent, false)
        itemView.cl12.onClick {
            this@TaskAdapter.onItemClickListener?.onClick(null, itemView.tag as Task)
        }
        itemView.ibTaskAddTime.onClick {
            this@TaskAdapter.onItemAddTimeClickListener?.onClick(null, itemView.tag as Task)
        }
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        var task = getItem(position)
        //holder.itemView.tvTaskID.text = (position+1).toString()
        holder.itemView.tvTaskName.text = task.name
        val mtimeLeft = task.mtime - task.mtimeDone
        holder.itemView.tvTaskHtime.text = (mtimeLeft/60).toString() + "h"
        holder.itemView.tvTaskMtime.text = (mtimeLeft%60).toString() + "m"
        holder.itemView.ivTask.setImageDrawable(mContext?.resources?.getDrawable(task.drawableId, null))
        holder.itemView.pbTask.progress = 100*task.mtimeDone/task.mtime
        holder.itemView.tag = task

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

