package com.example.projectapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectapi.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchCurrencyData().start()
        }
private fun fetchCurrencyData():Thread {
    return Thread {
        val url =
            URL("http://openapi.seoul.go.kr:8088/487069577a7068793232696b66764a/xml/JobFairInfo/1/5/")
        val connection = url.openConnection() as HttpURLConnection

        if (connection.responseCode == 200) {
            val inputSystem = connection.inputStream
            val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
            val request = Gson().fromJson(inputStreamReader,ovevanner::class.java)
            updateUI(request)
            inputStreamReader.close()
            inputSystem.close()
        } else {
            binding.JOBFAIRFRTIME.text = "faild Connection"
            binding.JOBFAIRDATE.text = "faild Connection"
            binding.JOBFAIRLOCATION.text = "faild Connection"
            binding.JOBFAIRNAME.text = "faild Connection"
            binding.JOBFAIREDTIME.text = "faild Connection"
            binding.JOBFAIRURL.text = "faild Connection"

        }
    }
}
    private fun updateUI( request:ovevanner){
        runOnUiThread{
            kotlin.run{
                binding.JOBFAIRFRTIME.text= String.format("행사시작시간: %d",request.rates.JOBFAIR_FRTIME)
                binding.JOBFAIRDATE.text= String.format("행사 일자: %d",request.rates.JOBFAIR_DATE)
                binding.JOBFAIRLOCATION.text= String.format("행사장소: %d",request.rates.JOBFAIR_LOCATION)
                binding.JOBFAIRNAME.text= String.format("행사명: %d",request.rates.JOBFAIR_NAME)
                binding.JOBFAIREDTIME.text= String.format("행사종료시간: %d",request.rates.JOBFAIR_EDTIME)
                binding.JOBFAIRURL.text= String.format("약도 URL: %d",request.rates.JOBFAIR_URL)
            }
        }
    }
}
