package com.example.mathgameproject

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import com.example.mathgameproject.databinding.ActivityGameactivityaddBinding
import java.util.*
import kotlin.random.Random

class gameactivityadd : AppCompatActivity() {
    private lateinit var binding:ActivityGameactivityaddBinding
    private lateinit var timer: CountDownTimer
    private var startmills:Long= 60000
    private var correctans:Int=0
    private var timeleftmills:Long = startmills
    private var lives =3;
    private var userscore = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameactivityaddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gameLogic()


        binding.buttonOk.setOnClickListener {
            Log.e("correctans",correctans.toString())
            val intput = binding.InputText.text.toString()
            if(intput==""){
                Toast.makeText(this, "Please write an answer or click next button", Toast.LENGTH_SHORT).show()
            }else{
                pauseTimer()
                var userAnswer = intput.toString()
                if(userAnswer==correctans.toString()){
                    userscore+=10
                    Log.e("useranswer",userAnswer.toString())
                    Log.e("correctans",correctans.toString())
                    binding.gamePoints.text = userscore.toString()
                    binding.Questionofgame.text = "Congratulations, It is Correct"
                }else{
                    lives--
                    Log.e("lives",lives.toString())
                    binding.gameLives.text =lives.toString()
                    binding.Questionofgame.text="Sorry, Answer is wrong"
                }

            }
            if(lives==0){
                val intent = Intent(this,EndActivity::class.java)
                intent.putExtra("userscore",userscore.toString())
                startActivity(intent)
                finish()
            }
        }
        binding.buttonNext.setOnClickListener {
            pauseTimer()
            resetTimer()
            gameLogic()
            binding.InputText.setText("")

        }


    }
    fun gameLogic(): String {
        val RandomNum1 = Random.nextInt(0,100)
        val RandomNum2 = Random.nextInt(0,100)
        val Questions = "$RandomNum1 + $RandomNum2"
        correctans= RandomNum1+RandomNum2
        binding.Questionofgame.text =Questions
        starttimer()
        return correctans.toString()
    }
    fun starttimer(){
        timer =object: CountDownTimer(timeleftmills,1000){
            override fun onTick(millisUntilFinished: Long) {
                timeleftmills = millisUntilFinished
                updatetext()

            }

            override fun onFinish() {
                updatetext()
                pauseTimer()
                resetTimer()
                lives--
                binding.Questionofgame.text ="Sorry, Time's Up"
                Log.e("lives",lives.toString())
                val intent = Intent(this@gameactivityadd,EndActivity::class.java)
                intent.putExtra("userscore",userscore.toString())
                startActivity(intent)
                finish()
            }

        }.start()


    }

    private fun updatetext() {
        val timeleft: Int = (timeleftmills/1000).toInt()
        binding.timeLeft.text = String.format(Locale.getDefault(),"%2d",timeleft)
    }
    private fun pauseTimer(){
        timer.cancel()
    }
    private fun resetTimer(){
        timeleftmills=startmills
        updatetext()
    }



}