package com.nuhlp.nursehelper.utill.useapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nuhlp.nursehelper.data.room.app.Document
import com.nuhlp.nursehelper.databinding.ItemListItemBinding

class DocListAdapter (private val onItemClicked: (Document) -> Unit) :
    ListAdapter<Document, DocListAdapter.ItemViewHolder>(DiffCallback){

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Document>() {
            override fun areItemsTheSame(oldDoc: Document, newDoc: Document): Boolean {
                return oldDoc.docNo == newDoc.docNo
            }

            override fun areContentsTheSame(oldDoc: Document, newDoc: Document): Boolean {
                return (oldDoc.docNo == newDoc.docNo)
                        && (oldDoc.patNo == newDoc.patNo)
                        && (oldDoc.tmpNo == newDoc.tmpNo)
                        && (oldDoc.crtDate == newDoc.crtDate)
                        && (oldDoc.contentsJs == newDoc.contentsJs)
            }
        }
    }

    class ItemViewHolder(private var binding: ItemListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(doc: Document) {
            binding.apply {
                title.text = doc.docNo.toString()
                content.text = doc.crtDate
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemListItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }
}