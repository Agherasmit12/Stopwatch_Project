package com.example.stopwatch_project

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Chronometer
import android.widget.NumberPicker
import com.example.stopwatch_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    var isRunning = false
    private var minutes: String? = "00.00.00"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var lapslist=ArrayList<String>()
        var arrayAdapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lapslist)
        binding.listview.adapter=arrayAdapter
        binding.lap.setOnClickListener{
            if(isRunning){
                lapslist.add(binding.Chronometer.text.toString())
                arrayAdapter.notifyDataSetChanged()
            }
        }
        binding.clock.setOnClickListener {
            var dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog)
            var numberPicker = dialog.findViewById<NumberPicker>(R.id.numberPicker)
            numberPicker.minValue = 0
            numberPicker.maxValue = 10
            dialog.findViewById<Button>(R.id.set_time).setOnClickListener {
                minutes = numberPicker.value.toString()
                binding.clocktime.text =
                    dialog.findViewById<NumberPicker>(R.id.numberPicker).value.toString() + "mins"
                dialog.dismiss()
            }
            dialog.show()

        }
        binding.run.setOnClickListener {
            if (!isRunning) {
                isRunning = false
                if (!minutes.equals("00.00.00")) {
                    var totalmin = minutes!!.toInt() * 60 * 1000L
                    var countDown = 1000L
                    binding.Chronometer.base = SystemClock.elapsedRealtime() + totalmin
                    binding.Chronometer.format = "%S %S"
                    binding.Chronometer.onChronometerTickListener =
                        Chronometer.OnChronometerTickListener {
                            var elapsedtime = SystemClock.elapsedRealtime() - binding.Chronometer.base
                            if (elapsedtime >= totalmin) {
                                binding.Chronometer.stop()
                                isRunning = false
                                binding.run.text = "Run"
                            }

                        }


                } else {
                    isRunning = true
                    binding.Chronometer.base = SystemClock.elapsedRealtime()
                    binding.run.text = "Stop"
                    binding.Chronometer.start()
                }
            } else {
                isRunning = true
                binding.Chronometer.base = SystemClock.elapsedRealtime()
                binding.run.text = "Run"



            }



        }
    }
}



