package com.starwars.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.aymen.graphql.ui.adapters.AllPeopleAdapter
import com.starwars.AllPeoplePaginatedQuery
import com.starwars.apollo.ApolloInstance
import com.starwars.databinding.ActivityMainBinding

@SuppressLint("NotifyDataSetChanged")
class MainActivity : AppCompatActivity() {

    //Create variables
    private lateinit var binding: ActivityMainBinding
    private lateinit var client: ApolloClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Get all view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Instance of the apollo client
        client = ApolloInstance().get()

        //Setup a layout manager to display items
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
            this@MainActivity,
            LinearLayoutManager.VERTICAL,
            false
        )

        //Instances of people list and adapter for items
        val people = mutableListOf<AllPeoplePaginatedQuery.Person>()
        val adapter = AllPeopleAdapter(people)
        binding.listCharacters.layoutManager = mLayoutManager
        binding.listCharacters.adapter = adapter

        //Start of coroutine
        lifecycleScope.launchWhenResumed {
            var cursor: String? = null
            var hasNextPage:Boolean = true
            //Cycle to get items 5 by 5 while there are still items available
            while (hasNextPage){
                val response = try {
                    //Query execution
                    client.query(AllPeoplePaginatedQuery(Optional.presentIfNotNull(cursor))).execute()
                } catch (e: ApolloException) {
                    //Error catching
                    binding.messageLayout.visibility = View.VISIBLE
                    return@launchWhenResumed
                }

                //list 5 items of people query
                val newPeople = response.data?.allPeople?.people?.filterNotNull()

                if (newPeople != null) {
                    //append of people to main list
                    people.addAll(newPeople)
                    //Notification of data update
                    adapter.notifyDataSetChanged()
                }

                //Save last cursor for next query
                cursor = response.data?.allPeople?.pageInfo?.endCursor
                hasNextPage = response.data?.allPeople?.pageInfo?.hasNextPage == true

                if(!hasNextPage){
                    //Remove loading layout
                    binding.loadingLayout.visibility = View.GONE
                }

                //Load list
                binding.listCharacters.visibility = View.VISIBLE
            }
        }
    }

    }
