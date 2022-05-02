package com.aymen.graphql.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.starwars.AllPeoplePaginatedQuery
import com.starwars.databinding.ListPersonBinding
import com.starwars.ui.PersonDetail

@SuppressLint("NotifyDataSetChanged")
class AllPeopleAdapter(private val people: List<AllPeoplePaginatedQuery.Person?>) : RecyclerView.Adapter<AllPeopleAdapter.PeopleHolder>() {

    companion object {
        private val PERSON_KEY = "PERSON"
    }

    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleHolder {
        val binding = ListPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.context = parent.context
        return PeopleHolder(binding)
    }

    override fun onBindViewHolder(holder: PeopleHolder, position: Int) {
        val person = people[position]
        holder.bind(position)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, PersonDetail::class.java)
            intent.putExtra("personId", person?.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = people.size

    inner class PeopleHolder(private val binding: ListPersonBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            val person = people[position]

            binding.textViewName.text = person?.name

            var specie = person?.species?.name?: "Human"
            binding.textViewDescription.text = "$specie from ${person?.homeworld?.name}"

        }
    }

}