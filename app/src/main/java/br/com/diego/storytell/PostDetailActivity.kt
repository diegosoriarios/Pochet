    package br.com.diego.storytell

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.com.diego.storytell.models.Post
import com.google.gson.Gson
import com.squareup.picasso.Picasso


class PostDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
        val post: Post =
            Gson().fromJson(intent.getStringExtra("PostJson"), Post::class.java)

        val titlePost: TextView = findViewById(R.id.titlePost)
        val contentPost: TextView = findViewById(R.id.contentPost)
        val imageView: ImageView = findViewById(R.id.imageView)

        titlePost.text = post.name
        contentPost.text = post.content
        Picasso.get().load(post.image).placeholder(R.mipmap.ic_launcher).into(imageView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}