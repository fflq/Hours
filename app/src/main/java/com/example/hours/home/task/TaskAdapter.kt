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
import kotlinx.android.synthetic.main.list_item_card_task.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import kotlin.math.max

class TaskAdapter(var mContext: Context?, var type: TYPE = TYPE.TASK): ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {
    var onItemClickListener: OnTaskClickListener? = null
    var onItemAddTimeClickListener: OnTaskClickListener? = null

    interface OnTaskClickListener {
        fun onClick(v: View?, task: Task)
    }

    enum class TYPE { TASK, TODAY }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_card_task, parent, false)
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
        holder.itemView.tag = task
        holder.itemView.etTaskName.text = task.name
        holder.itemView.ivTask.setImageDrawable(mContext?.resources?.getDrawable(task.drawableId, null))

        var mtime = if(this.type == TYPE.TASK) task.totalMtime else task.cycleMtime
        var mtimeDone = if(this.type == TYPE.TASK) task.totalMtimeDone else task.cycleMtimeDone
        val mtimeLeft = max(mtime - mtimeDone, 0)
        holder.itemView.tvTaskHtime.text = (mtimeLeft/60).toString() + "h"
        holder.itemView.tvTaskMtime.text = (mtimeLeft%60).toString() + "m"
        holder.itemView.pbTask.progress = if (mtime == 0) 0 else 100*mtimeDone/mtime
    }


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

