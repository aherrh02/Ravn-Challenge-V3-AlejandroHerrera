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

    private lateinit var binding: ActivityMainBinding
    private lateinit var client: ApolloClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        client = ApolloInstance().get()

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
            this@MainActivity,
            LinearLayoutManager.VERTICAL,
            false
        )

        val people = mutableListOf<AllPeoplePaginatedQuery.Person>()
        val adapter = AllPeopleAdapter(people)
        binding.listCharacters.layoutManager = mLayoutManager
        binding.listCharacters.adapter = adapter

        lifecycleScope.launchWhenResumed {
            var cursor: String? = null
            var hasNextPage:Boolean = true
            while (hasNextPage){
                val response = try {
                    client.query(AllPeoplePaginatedQuery(Optional.presentIfNotNull(cursor))).execute()
                } catch (e: ApolloException) {
                    binding.messageLayout.visibility = View.VISIBLE
                    return@launchWhenResumed
                }
                val newPeople = response.data?.allPeople?.people?.filterNotNull()

                if (newPeople != null) {
                    people.addAll(newPeople)
                    adapter.notifyDataSetChanged()
                }

                cursor = response.data?.allPeople?.pageInfo?.endCursor
                hasNextPage = response.data?.allPeople?.pageInfo?.hasNextPage == true

                if(!hasNextPage){
                    binding.loadingLayout.visibility = View.GONE
                }

                binding.listCharacters.visibility = View.VISIBLE
            }
        }
    }

    }
