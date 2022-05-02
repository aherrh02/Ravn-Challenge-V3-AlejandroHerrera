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

    //variable creation
    private lateinit var binding: PersonDetailBinding
    private lateinit var client: ApolloClient
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PersonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //context instance
        context = getApplicationContext()
        //client instance
        client = ApolloInstance().get()

        //save of the information passed before
        var personId = intent.getStringExtra("personId")

        //start of coroutine
        lifecycleScope.launchWhenResumed {
            val response = try {
                //query execution
                client.query(CharacterByIdQuery(Optional.presentIfNotNull(personId))).execute()
            } catch (e: ApolloException) {
                return@launchWhenResumed
            }
            //store of the person object
            val person = response.data?.person

            //set of the text in their labels
            binding.title.text = person?.name
            binding.textViewBirthYear.text = person?.birthYear
            binding.textViewEye.text = person?.eyeColor
            binding.textViewHair.text = person?.hairColor
            binding.textViewSkin.text = person?.skinColor

            //vehicle list object
            var vehicles = person?.vehicleConnection?.vehicles
            //creations of array of strings
            var vehiclesArray = ArrayList<String?>()

            if (vehicles != null) {
                for(item in vehicles){
                    //add string from object to array
                    vehiclesArray.add(item?.name)
                }
                //creation of the Array adapter for the listview
                var arrayAdapter = ArrayAdapter<String>(context, R.layout.simple_list_item_1, vehiclesArray)
                binding.listView.setAdapter(arrayAdapter)
            } else {
                //show up of the label in case of no data
                binding.listView.visibility = View.GONE
                binding.textView3.text = "No Data Found"
                binding.textView3.visibility = View.VISIBLE
            }

        }

    }
}