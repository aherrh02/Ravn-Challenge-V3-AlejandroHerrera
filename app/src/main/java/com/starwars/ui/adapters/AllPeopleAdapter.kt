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

    //App context
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleHolder {
        //Create new view when empty
        val binding = ListPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.context = parent.context
        return PeopleHolder(binding)
    }

    override fun onBindViewHolder(holder: PeopleHolder, position: Int) {
        //Get person object to creat
        val person = people[position]
        //Create list item
        holder.bind(position)
        //Listener to change screen to detail view
        holder.itemView.setOnClickListener {
            val intent = Intent(context, PersonDetail::class.java)
            //send of the item
            intent.putExtra("personId", person?.id)
            //start of the activity
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = people.size

    inner class PeopleHolder(private val binding: ListPersonBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            //Get person object
            val person = people[position]

            //Set name into label
            binding.textViewName.text = person?.name

            //Specify if specie is human or not
            var specie = person?.species?.name?: "Human"
            //Create text
            binding.textViewDescription.text = "$specie from ${person?.homeworld?.name}"

        }
    }

}