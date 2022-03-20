package com.ruslangrigoriev.topmovie.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.ruslangrigoriev.topmovie.domain.utils.FOOTER_VIEW_TYPE
import com.ruslangrigoriev.topmovie.domain.utils.REGULAR_VIEW_TYPE

class BaseRecyclerAdapter<T : Any>(
    private var dataSet: List<T>,
    @LayoutRes val layoutID: Int,
    private val bindingInterface: BindingInterface<T>,
) :
    RecyclerView.Adapter<BaseRecyclerAdapter.ViewHolder>() {

    class ViewHolder(
        val view: View
    ) : RecyclerView.ViewHolder(view) {

        fun <T : Any> bind(
            item: T,
            bindingInterface: BindingInterface<T>
        ) = bindingInterface.bindData(item, view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutID, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item, bindingInterface)
    }

    override fun getItemCount(): Int = dataSet.size

    fun updateList(list: List<T>) {
        this.dataSet = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == dataSet.size) FOOTER_VIEW_TYPE else REGULAR_VIEW_TYPE
    }
}

