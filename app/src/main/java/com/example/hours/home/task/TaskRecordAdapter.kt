package com.example.hours.home.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hours.R
import com.example.hours.home.data.TaskRecord
import kotlinx.android.synthetic.main.task_card_item.view.cl12
import kotlinx.android.synthetic.main.task_card_item.view.tvTaskHtime
import kotlinx.android.synthetic.main.task_card_item.view.tvTaskMtime
import kotlinx.android.synthetic.main.task_record_card_item.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.text.SimpleDateFormat

class TaskRecordAdapter(): ListAdapter<TaskRecord, TaskRecordAdapter.TaskRecordViewHolder>(TaskDiffCallback()) {
    var onItemClickListener: OnTaskRecordClickListener? = null

    interface OnTaskRecordClickListener {
        fun onClick(v: View?, task: TaskRecord)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskRecordViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_record_card_item, parent, false)
        itemView.cl12.onClick {
            this@TaskRecordAdapter.onItemClickListener?.onClick(null, itemView.tag as TaskRecord)
        }
        return TaskRecordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskRecordViewHolder, position: Int) {
        var taskRecord = getItem(position)
        holder.itemView.tag = taskRecord
        holder.itemView.tvNote.text = taskRecord.note
        holder.itemView.tvTaskHtime.text = (taskRecord.mtimeDone/60).toString() + "h"
        holder.itemView.tvTaskMtime.text = (taskRecord.mtimeDone%60).toString() + "m"
        holder.itemView.tvRecordAddtime.text = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(taskRecord.addTime.toLong()*1000)
    }


    class TaskRecordViewHolder(view: View): RecyclerView.ViewHolder(view)


    class TaskDiffCallback: DiffUtil.ItemCallback<TaskRecord>() {
        override fun areItemsTheSame(oldItem: TaskRecord, newItem: TaskRecord): Boolean {
            return (oldItem.id == newItem.id)
        }

        override fun areContentsTheSame(oldItem: TaskRecord, newItem: TaskRecord): Boolean {
            return (oldItem == newItem)
        }
    }

}
