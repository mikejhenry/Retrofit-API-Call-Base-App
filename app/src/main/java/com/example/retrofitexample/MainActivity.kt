package com.example.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.retrofitexample.network.ApiClient
import com.example.retrofitexample.network.CharacterResponse
import retrofit2.Call
import retrofit2.Response
import com.example.retrofitexample.network.Character
import com.google.android.material.snackbar.Snackbar
import retrofit2.Callback
//import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy{
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Toast.makeText(DOMAIN_VERIFICATION_SERVICE,"This app utilizes Retrofit to make an api call to the requested database.", Toast.LENGTH_LONG).show()

        //ensure we are observing the LiveData
        viewModel.characterLiveData.observe(this,{state->
            processCharactersResponse(state)
        })


    }

    private fun processCharactersResponse(state: ScreenState<List<Character>?>){

        val pb = findViewById<ProgressBar>(R.id.progressBar)
        when(state){
            is ScreenState.Loading ->{
                //add progress bar
                pb.visibility = View.VISIBLE
            }
            is ScreenState.Success -> {
                pb.visibility = View.GONE
              //  viewModel.characterLiveData.observe(this,{ characters ->
                if(state.data != null) {
                    val adapter = MainAdapter(state.data)
                    val recyclerView = findViewById<RecyclerView>(R.id.charactersRv)
                    recyclerView?.layoutManager =
                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    recyclerView?.adapter = adapter
                }
            }
            is ScreenState.Error ->{
                pb.visibility = View.GONE
                val view = pb.rootView
                Snackbar.make(view,state.message!!,Snackbar.LENGTH_LONG).show()
            }
        }
    }
}