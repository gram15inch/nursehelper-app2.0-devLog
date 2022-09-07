package com.nuhlp.nursehelper.utill.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nuhlp.nursehelper.datasource.room.app.CareService

/*


class QuickListAdapter (private val onServiceClicked: (CareService) -> Unit) :
    ListAdapter<CareService, QuickListAdapter.TaskViewHolder>(DiffCallback){

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<CareService>() {
            override fun areItemsTheSame(oldCS: CareService, newCS: CareService): Boolean {
                return oldCS.csNo == newCS.csNo
            }

            override fun areContentsTheSame(oldCS: CareService, newCS: CareService): Boolean {
                return (oldCS.csNo == newCS.csNo)
                        && (oldCS.name == newCS.name)
            }
        }
    }

    class TaskViewHolder(private var binding: ItemHolderCareServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(doc: CareService) {
            binding.apply {
                title.text = doc.docNo.toString()
                content.text = "${doc.crtDate}\n환자번호: ${doc.patNo}\n문서종류: ${doc.tmpNo}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            //ItemHolderDocumentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onTaskClicked(current)
        }
        holder.bind(current)
    }
}*/
//todo 리클라이어뷰가 아닌 개별 커스텀뷰로 해야 동적 생성 가능할듯 좀더 생각해보기