package com.example.hours.home.task


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.hours.R
import com.example.hours.home.base.NotHomeBaseFragment
import com.example.hours.home.data.Task
import com.example.hours.home.data.TaskPlan
import com.example.hours.home.data.TaskRecord
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_task_detail.*
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class TaskDetailFragment : NotHomeBaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_task_detail, container, false)

        return root
    }

    fun initBarChart(barChart: BarChart, listBarEntry: ArrayList<BarEntry>) {
        if (barChart.data != null)  return

        val barDataSet = BarDataSet(listBarEntry, "minutes").also { it.colors = ColorTemplate.VORDIPLOM_COLORS.toMutableList() }
        val listDataSets = ArrayList<IBarDataSet>().also { it.add(barDataSet) }
        var tsNow =(System.currentTimeMillis()/1000).toInt()
        var zeroTime = tsNow - (tsNow + 3600 * 8) % 86400
        barChart.apply {
            //setMaxVisibleValueCount(10)
            animateXY(1000, 1000)
            setFitBars(true)
            description.isEnabled = false
            xAxis.setDrawGridLines(false)
            xAxis.valueFormatter = object: IndexAxisValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return listBarEntry[value.toInt()].data.toString()
                }
            }
            setDrawGridBackground(false)
            data = BarData(listDataSets)
            setPinchZoom(false)
            setScaleEnabled(false)
            //legend.isEnabled = false
        }
    }


    fun initBarChartByStats(barchart: BarChart, listStats: List<TaskRecord.Stats>, sizeBarEntry: Int) {
        val listBarEntry = ArrayList<BarEntry>()
        var zeroTimeToday = (System.currentTimeMillis()/1000).toInt().let { tsNow -> tsNow - (tsNow + 3600 * 8) % 86400 }
        val secOneDay = 86400

        for (i in (sizeBarEntry-1) downTo 0) {
            var date = SimpleDateFormat("M.d").format(1000L*(zeroTimeToday-i*secOneDay))
            listBarEntry.add(BarEntry(i.toFloat(), 10f, date))
        }

        for (stats in listStats) {
            var idx = (zeroTimeToday - stats.zeroTime)/secOneDay
            if ( (idx >= 0) and (idx < listBarEntry.size) )
                listBarEntry[idx]?.y = stats.sum.toFloat()
        }

        initBarChart(barchart, listBarEntry)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val task = arguments?.getParcelable<Task>("task")
        if (task is Task) {
            tvTaskName.text = task.name
            tvAddtime.text = SimpleDateFormat("dd/MM/YYYY").format(1000L*task.addTime)
            ivTaskIcon.image = resources.getDrawable(task.drawableId, null)
            tvTime.text = String.format("%.1f/%dh", task.totalMtimeDone.toDouble()/60, task.totalMtime/60)
            tvTimePlan.text = String.format("%.1f/%.1fh", task.cycleMtimeDone.toDouble()/60, task.cycleMtime.toDouble()/60)
            tvPlan.text = TaskPlan.PlanInfoOnly().also{it.setPlanInfoByTask(task)}.toString()
            ibTaskRecords.onClick {
                navController?.navigate(R.id.action_taskDetailFragment_to_taskRecordsFragment, arguments)
            }

            taskViewModel?.taskRepository?.selectRecordStatsByTid(task.id!!)?.observe(viewLifecycleOwner, Observer {
                if (it.isNotEmpty()) {
                    Log.d ("s", "aaa")
                    initBarChartByStats (barchart, it, 7)
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.task_detail_menu_edit -> {
                this.navController?.navigate(R.id.action_taskDetailFragment_to_taskEditFragment, arguments)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
