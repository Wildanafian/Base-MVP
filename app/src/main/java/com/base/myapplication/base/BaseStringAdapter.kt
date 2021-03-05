package com.base.myapplication.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseStringAdapter<String>: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items:ArrayList<String> = ArrayList()

    override fun getItemCount(): Int = items.size

    open fun add(item: String) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addAll(items: List<String>) {
        for (item in items) {
            add(item)
        }
    }

    fun removeAt(position: Int){
        items.removeAt(position)
    }

    fun editAt(position: Int, newData : String){
        items[position] = newData
    }

    fun clear() {
        items.clear()
        notifyItemRangeRemoved(0, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getBindViewHolder(holder, position)
    }

    abstract fun getCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun getBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    var clickListener : OnItemClickListener? = null
    fun setOnClickListener(mClickListener : OnItemClickListener){
        clickListener = mClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, view: View, vh: RecyclerView.ViewHolder)
    }
}