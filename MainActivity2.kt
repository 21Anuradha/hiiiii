package com.example.hiiiii

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.IOException
class MainActivity2 : AppCompatActivity(){

    private lateinit var editTextName: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var editTextAge: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextAddress: EditText
    private lateinit var editTextCountry: EditText
    private lateinit var checkBoxTerms: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //hide action bar
        getSupportActionBar()?.hide()



        editTextName = findViewById(R.id.editTextName)
        radioGroupGender = findViewById(R.id.radioGroupGender)
        editTextAge = findViewById(R.id.editTextAge)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextAddress = findViewById(R.id.editTextAddress)
        editTextCountry = findViewById(R.id.editTextCountry)
        checkBoxTerms = findViewById(R.id.checkBoxTerms)


       val buttonSubmit: Button = findViewById(R.id.buttonSubmit)
       buttonSubmit.setOnClickListener {
        Toast.makeText(this, "Registration Succesfully", Toast.LENGTH_SHORT).show()
           if (validateInputs()) {
               postUserData()
           }
       }
   }

   private fun validateInputs(): Boolean {
       val name = editTextName.text.toString().trim()
       val gender = when (radioGroupGender.checkedRadioButtonId) {
           R.id.radioButtonMale -> "Male"
           R.id.radioButtonFemale -> "Female"
           R.id.radioButtonOther -> "Other"
           else -> ""
       }
       val age = editTextAge.text.toString().trim()
       val phone = editTextPhone.text.toString().trim()
       val email = editTextEmail.text.toString().trim()
       val address = editTextAddress.text.toString().trim()
       val country = editTextCountry.text.toString().trim()
       val termsAccepted = checkBoxTerms.isChecked

       // Perform input validation as per your requirements
       if (name.isEmpty() || gender.isEmpty() || age.isEmpty() || phone.isEmpty() ||
           email.isEmpty() || address.isEmpty() || country.isEmpty() || !termsAccepted
       ) {
           // Display an error message or handle the validation logic as per your needs
           Log.d(
               "Registration",
               "Validation failed: Please fill all the fields and accept terms and conditions."
           )
           return false
       }

       return true
   }

    private fun postUserData() {
        val url = "https://bespokeapp.xyz/practical/index.php"
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val requestBody = createRequestBody()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle the request failure
                Log.d("Registration", "Request failed: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                // Handle the request success
                val responseData = response.body?.string()
                if (response.isSuccessful) {
                    if (responseData != null) {
                        // Process the response data as per your requirements
                        Log.d("Registration", "Request successful: $responseData")
                    } else {
                        Log.d("Registration", "Response body is null")
                    }
                } else {
                    Log.d("Registration", "Request failed: ${response.code}")
                }
            }
        })
    }


    private fun createRequestBody(): RequestBody {
        val name = editTextName.text.toString().trim()
        val gender = when (radioGroupGender.checkedRadioButtonId) {
            R.id.radioButtonMale -> "Male"
            R.id.radioButtonFemale -> "Female"
            R.id.radioButtonOther -> "Other"
            else -> ""
        }
        val age = editTextAge.text.toString().trim()
        val phone = editTextPhone.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val address = editTextAddress.text.toString().trim()
        val country = editTextCountry.text.toString().trim()

        // Create a JSON object with the user data
        val json = JSONObject()
        json.put("name", name)
        json.put("gender", gender)
        json.put("age", age)
        json.put("phone", phone)
        json.put("email", email)
        json.put("address", address)
        json.put("country", country)

        // Convert the JSON object to a request body
        val requestBodyString = json.toString()
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        return RequestBody.create(mediaType, requestBodyString)
    }

}