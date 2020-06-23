package br.com.diego.storytell

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import br.com.diego.storytell.models.Post
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class DisplayMessageActivity : AppCompatActivity() {
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)

        /*val message = intent.getStringExtra(EXTRA_MESSAGE)
        val textView = findViewById<TextView>(R.id.textView).apply {
            text = message
        }*/

        listView = findViewById(R.id.recipe_list_view)

        val model: MyViewModel by viewModels()
        model.getPosts().observe(this, Observer<List<Post>>{ posts ->
            val listItems = arrayOfNulls<String>(posts.size)
            for (i in 0 until posts.size) {
                val post = posts[i]
                listItems[i] = post.name
            }
            val adapter = PostAdapter(this, posts)
            listView.adapter = adapter
            listView.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id -> //here you can use the position to determine what checkbox to check
                    //this assumes that you have an array of your checkboxes as well. called checkbox
                    Toast.makeText(this, posts[position].name, Toast.LENGTH_SHORT).show()
                    goPostDetailPage(posts[position])
                }
            /*post.forEach{ p ->
                Log.d("Names", p.name)
            }*/
        })
    }

    fun goPostDetailPage(post: Post) {
        val intent = Intent(this, PostDetailActivity::class.java).apply {
            putExtra("PostJson",Gson().toJson(post))
        }
        startActivity(intent)
    }
}

class PostAdapter(private val context: Context, private val dataSource: List<Post>) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    //2
    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //4
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.list_item, parent, false)

        val titleTextView = rowView.findViewById(R.id.name) as TextView

        val subtitleTextView = rowView.findViewById(R.id.subtitle) as TextView

        val thumbnailImageView: ImageView = rowView.findViewById(R.id.thumbnail) as ImageView

        val post = getItem(position) as Post

        titleTextView.text = post.name
        subtitleTextView.text = post.createdAt

        Picasso.get().load(post.image).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView)

        return rowView
    }
}