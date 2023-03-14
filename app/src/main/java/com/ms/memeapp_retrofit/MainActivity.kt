package com.ms.memeapp_retrofit

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.ms.memeapp_retrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()

        binding.btnMeme.setOnClickListener {
            getData()
        }
    }

    // after write the enqueue press ctrl+shift+space and enter for getting override methods

    private fun getData(){
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait..")
        progressDialog.show()

        RetrofitInstance.apiInterface.getData().enqueue(object : Callback<responseDataClass?> {
            override fun onResponse(
                call: Call<responseDataClass?>,
                response: Response<responseDataClass?>
            ) {
                binding.title1.text ="Title: "+ response.body()?.title
                binding.author1.text = "Author: "+ response.body()?.author
                Glide.with(this@MainActivity).load(response.body()?.url).into(binding.img1)
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<responseDataClass?>, t: Throwable) {
                binding.author1.text = "No Data Found!"
                progressDialog.dismiss()
            }
        })
    }
}



