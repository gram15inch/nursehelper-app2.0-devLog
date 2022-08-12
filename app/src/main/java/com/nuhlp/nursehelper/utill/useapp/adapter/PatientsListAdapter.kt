package com.nuhlp.nursehelper.utill.useapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nuhlp.nursehelper.databinding.ItemHolderPatientsBinding
import com.nuhlp.nursehelper.datasource.room.app.Patient

class PatientsListAdapter (private val onItemClicked: (Patient) -> Unit) :
    ListAdapter<Patient, PatientsListAdapter.PatientsHolder>(DiffCallback){

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Patient>() {
            override fun areItemsTheSame(oldItem: Patient, newItem: Patient): Boolean {
                return oldItem.patNo == newItem.patNo
            }

            override fun areContentsTheSame(oldItem: Patient, newItem: Patient): Boolean {
                return (oldItem.patNo == newItem.patNo)
                        && (oldItem.bpNo == newItem.bpNo)
                        && (oldItem.gender == newItem.gender)
                        && (oldItem.name == newItem.name)
                        && (oldItem.rrn == newItem.rrn)
            }
        }
    }

    class PatientsHolder(private var binding: ItemHolderPatientsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(patient: Patient) {
            binding.apply {
               content.text = "환자번호: ${patient.patNo}\n ${patient.name} \n성별: ${patient.gender} \n ${patient.rrn}" +
                       "\n 장소: ${patient.bpNo} "
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientsHolder {
        return PatientsHolder(
            ItemHolderPatientsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PatientsHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

}