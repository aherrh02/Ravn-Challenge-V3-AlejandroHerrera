package com.starwars.ui

import android.R
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.starwars.CharacterByIdQuery
import com.starwars.apollo.ApolloInstance
import com.starwars.databinding.PersonDetailBinding


class PersonDetail : AppCompatActivity() {

    private lateinit var binding: PersonDetailBinding
    private lateinit var client: ApolloClient
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PersonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = getApplicationContext()
        client = ApolloInstance().get()

        var personId = intent.getStringExtra("personId")

        lifecycleScope.launchWhenResumed {
            val response = try {
                client.query(CharacterByIdQuery(Optional.presentIfNotNull(personId))).execute()
            } catch (e: ApolloException) {
                return@launchWhenResumed
            }
            val person = response.data?.person

            binding.title.text = person?.name
            binding.textViewBirthYear.text = person?.birthYear
            binding.textViewEye.text = person?.eyeColor
            binding.textViewHair.text = person?.hairColor
            binding.textViewSkin.text = person?.skinColor

            var vehicles = person?.vehicleConnection?.vehicles
            var vehiclesArray = ArrayList<String?>()

            if (vehicles != null) {
                for(item in vehicles){
                    vehiclesArray.add(item?.name)
                }
                var arrayAdapter = ArrayAdapter<String>(context, R.layout.simple_list_item_1, vehiclesArray)
                binding.listView.setAdapter(arrayAdapter)
            } else {
                binding.listView.visibility = View.GONE
                binding.textView3.text = "No Data Found"
                binding.textView3.visibility = View.VISIBLE
            }

        }

    }
}