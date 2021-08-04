package com.example.calculatorapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.calculatorapp.databinding.ActivityMainBinding
import org.w3c.dom.Text
import java.lang.NumberFormatException

private const val OPERAND_1=""
private const val OPERATION_TEXT=""

class MainActivity : AppCompatActivity() {


    private lateinit var result:EditText

    private lateinit var newNumber:EditText

    private val displayOperation by lazy {findViewById<TextView>(R.id.operation)}

    private var operand1: Double? = null
    private var operand2: Double = 0.0
    private var pendingOperation :  String = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        result = findViewById(R.id.result)
        newNumber = findViewById(R.id.newNumber)

        val listener = View.OnClickListener{v ->
            val b = v as Button
            binding.newNumber.append(b.text)
        }

        binding.button0.setOnClickListener(listener)
        binding.button1.setOnClickListener(listener)
        binding.button2.setOnClickListener(listener)
        binding.button3.setOnClickListener(listener)
        binding.button4.setOnClickListener(listener)
        binding.button5.setOnClickListener(listener)
        binding.button6.setOnClickListener(listener)
        binding.button7.setOnClickListener(listener)
        binding.button8.setOnClickListener(listener)
        binding.button9.setOnClickListener(listener)
        binding.buttonDot.setOnClickListener(listener)

        val opListener = View.OnClickListener { v ->
            val operationButton = (v as Button).text.toString()
            try {
                val value = binding.newNumber.text.toString().toDouble()
                    performOperation(value,operationButton)
            }catch (e:NumberFormatException){
                binding.newNumber.setText("")
            }


            pendingOperation = operationButton
            displayOperation.text = pendingOperation
        }

        binding.buttonEquals.setOnClickListener(opListener)
        binding.buttonDivide.setOnClickListener(opListener)
        binding.buttonMultiply.setOnClickListener(opListener)
        binding.buttonAdd.setOnClickListener(opListener)
        binding.buttonSubtract.setOnClickListener(opListener)

    }

    private fun performOperation(value:Double, button:String){
        if (operand1==null){
            operand1 = value
        }else{
            operand2 = value
            if (pendingOperation=="="){
                pendingOperation = button
            }
            when(pendingOperation){
                "=" -> operand1=operand2
                "/" -> if (operand2 == 0.0){
                         operand1 = Double.NaN
                    }else{
                    operand1 = operand1!! /operand2
                }
                "x" -> operand1 = operand1!! *operand2
                "+" -> operand1 = operand1!! + operand2
                "-" -> operand1 = operand1!! - operand2
            }
        }
       result.setText(operand1.toString())
        newNumber.setText("")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        operand1 = savedInstanceState.getDouble(OPERAND_1)
        pendingOperation = savedInstanceState.getString(OPERATION_TEXT,"")
        displayOperation.text = pendingOperation


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(OPERATION_TEXT,pendingOperation)
        if (operand1 != null){
            outState.putDouble(OPERAND_1,operand1!!)
        }
    }

}