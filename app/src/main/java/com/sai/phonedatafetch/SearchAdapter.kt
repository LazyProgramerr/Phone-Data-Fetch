package com.sai.phonedatafetch

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sai.phonedatafetch.database.PhoneModel
import com.sai.phonedatafetch.databinding.SearchLayoutBinding

class SearchAdapter(val context: Context,val mobiles: List<PhoneModel>): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchViewHolder {
        val view = SearchLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: SearchViewHolder,
        position: Int
    ) {
        val text = mobiles[position].mBrand + "\n"+ mobiles[position].mName+"\n"+mobiles[position].mProcessor+ "\n"+mobiles[position].mPrice
        holder.binding.textHolder.text = text

        holder.binding.root.setOnClickListener {
            Toast.makeText(context,"clicked on ${mobiles[position].mName}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = mobiles.size

    class SearchViewHolder( val binding: SearchLayoutBinding): RecyclerView.ViewHolder(binding.root)
}