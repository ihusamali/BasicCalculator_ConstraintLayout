package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private lateinit var display_text: TextView
    var count = 0
    var lastDigit = false
    var lastDecimal = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display_text = findViewById(R.id.display_text)
    }

    fun onDigitClick(view: View) {
        if (count == 0) {
            display_text.text = (view as Button).text
            count++
            lastDigit = true
        } else {
            display_text.append((view as Button).text)
            lastDigit = true
        }


    }

    fun onClear(view: View) {
        display_text.text = "0"
        lastDigit = false
        lastDecimal = false
        count = 0
    }

    fun onDecimalButton(view: View) {
        if (lastDigit && !lastDecimal) {
            display_text.append((view as Button).text)
            lastDigit = false
            lastDecimal = true
        }
    }

    fun onOperator(view: View) {
        if(lastDigit && !onOperatorAdded(display_text.text.toString())){
            display_text.append((view as Button).text)
            lastDigit = false
            lastDecimal =false
        }
    }

    private fun onOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }

    }

    fun onEqual(view: View) {
        if (lastDigit){



            try{

                var value = display_text.text.toString()

                var prefix = ""

                if(display_text.text.startsWith("-")){
                    prefix ="-"

                    value = value.substring(1)
                }


                if(display_text.text.contains("+")){
                    val splitValue = value.split("+")
                    val left = splitValue[0]
                    val right = splitValue[1]

                    display_text.text = removeZero((left.toDouble() + right.toDouble()).toString())
                }

                if(display_text.text.contains("-")){
                    val splitValue = value.split("-")
                    val left = splitValue[0]
                    val right = splitValue[1]

                    display_text.text = removeZero((left.toDouble() - right.toDouble()).toString())
                }

                if(display_text.text.contains("*")){
                    val splitValue = value.split("*")
                    val left = splitValue[0]
                    val right = splitValue[1]

                    display_text.text = removeZero((left.toDouble() * right.toDouble()).toString())
                }

                if(display_text.text.contains("/")){
                    val splitValue = value.split("/")
                    val left = splitValue[0]
                    val right = splitValue[1]

                    display_text.text = removeZero((left.toDouble() / right.toDouble()).toString())


                }


            }catch(e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZero(value : String) :String {
        var res = value

        if(value.contains(".0")){
            res = value.substring(0, value.length-2)
        }


        return res
    }
}