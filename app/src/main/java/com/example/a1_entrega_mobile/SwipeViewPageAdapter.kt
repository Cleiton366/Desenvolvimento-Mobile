package com.example.a1_entrega_mobile
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SwipeViewPageAdapter ( private var tittle : List<String>, private var about : List<String>)
    : RecyclerView.Adapter<SwipeViewPageAdapter.Page2ViewHolder>(){

    class Page2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle : TextView = itemView.findViewById(R.id.swipe_item_tittle)
        val itemAbout : TextView = itemView.findViewById(R.id.swipe_item_about)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Page2ViewHolder {
        return Page2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.swipe_view_item_page,
        parent, false))
    }

    override fun onBindViewHolder(holder: Page2ViewHolder, position: Int) {
        holder.itemTitle.text = tittle[position]
        holder.itemAbout.text = about[position]
    }

    override fun getItemCount(): Int {
        return tittle.size
    }

}