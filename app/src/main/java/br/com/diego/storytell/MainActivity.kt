package br.com.diego.storytell

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import br.com.diego.storytell.models.Post

val EXTRA_MESSAGE = "br.com.diego.storytell.MESSAGE"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val model: MyViewModel by viewModels()
        model.getPosts().observe(this, Observer<List<Post>>{ post ->
            post.forEach{ p ->
                Log.d("Names", p.name)
            }
        })*/

        val btnSend: Button = findViewById(R.id.btnSend)
        btnSend.setOnClickListener{
            sendMessage()
        }
    }

    private fun sendMessage() {
        val editText: EditText = findViewById(R.id.inputText)
        val message = editText.text.toString()
        if (message.length > 0) {
            val intent = Intent(this, DisplayMessageActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, message)
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "Type Something", Toast.LENGTH_SHORT)
        }
    }
}