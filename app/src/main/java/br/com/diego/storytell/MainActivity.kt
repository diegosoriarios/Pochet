package br.com.diego.storytell

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.diego.storytell.models.Post
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var progressBar: ProgressBar
    private lateinit var refreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)

        listView = findViewById(R.id.recipe_list_view)
        progressBar = findViewById(R.id.progressBar)
        refreshLayout = findViewById(R.id.swiperefresh)

        val model: MyViewModel by viewModels()
        model.getPosts().observe(this, Observer<List<Post>>{ posts ->
            val listItems = arrayOfNulls<String>(posts.size)
            for (i in posts.indices) {
                val post = posts[i]
                listItems[i] = post.name
            }
            if (posts.isNotEmpty()) {
                progressBar.visibility = View.GONE
                listView.visibility = View.VISIBLE
            }
            val adapter = PostAdapter(this, posts)
            listView.adapter = adapter
            listView.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    val imageItem = view.findViewById<ImageView>(R.id.thumbnail)
                    goPostDetailPage(posts[position], imageItem)
                }
        })

        refreshLayout.setOnRefreshListener {
            recreate()
        }
    }

    private fun goPostDetailPage(post: Post, imageView: ImageView) {
        val intent = Intent(this, PostDetailActivity::class.java).apply {
            putExtra("PostJson", Gson().toJson(post))
        }
        //val imageView: ImageView = listView.findViewById(R.id.thumbnail)
        val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this, imageView, ViewCompat.getTransitionName(imageView).toString()
        )
        startActivity(intent, options.toBundle())
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

class CustomTransition: Transition() {
    // Define a key for storing a property value in
    // TransitionValues.values with the syntax
    // package_name:transition_class:property_name to avoid collisions
    private val PROPNAME_BACKGROUND = "com.example.android.customtransition:CustomTransition:background"

    override fun captureStartValues(transitionValues: TransitionValues) {
        // Call the convenience method captureValues
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    // For the view in transitionValues.view, get the values you
    // want and put them in transitionValues.values
    private fun captureValues(transitionValues: TransitionValues) {
        // Get a reference to the view
        val view = transitionValues.view
        // Store its background property in the values map
        transitionValues.values[PROPNAME_BACKGROUND] = view.background
    }
}