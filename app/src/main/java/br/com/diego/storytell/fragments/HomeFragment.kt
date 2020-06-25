package br.com.diego.storytell.fragments

import android.content.Context
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionValues
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.diego.storytell.MainActivity
import br.com.diego.storytell.MyViewModel
import br.com.diego.storytell.R
import br.com.diego.storytell.models.Post
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {
    private lateinit var listView: ListView
    private lateinit var progressBar: ProgressBar
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var model: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        model = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val view: View = inflater.inflate(R.layout.activity_display_message, container, false)

        listView = view.findViewById(R.id.recipe_list_view)
        progressBar = view.findViewById(R.id.progressBar)
        refreshLayout = view.findViewById(R.id.swiperefresh)

        //val model: MyViewModel by viewModels()
        model.getPosts().observe(viewLifecycleOwner, Observer<List<Post>>{ posts ->
            val listItems = arrayOfNulls<String>(posts.size)
            for (i in posts.indices) {
                val post = posts[i]
                listItems[i] = post.name
            }
            if (posts.isNotEmpty()) {
                progressBar.visibility = View.GONE
                listView.visibility = View.VISIBLE
            }
            val adapter = activity?.applicationContext?.let { PostAdapter(it, posts) }
            listView.adapter = adapter
            listView.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    val imageItem = view.findViewById<ImageView>(R.id.thumbnail)
                    (activity as MainActivity?)?.goPostDetailPage(posts[position], imageItem)
                }
        })

        refreshLayout.setOnRefreshListener {
            (activity as MainActivity?)?.recreate()
        }

        return view
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