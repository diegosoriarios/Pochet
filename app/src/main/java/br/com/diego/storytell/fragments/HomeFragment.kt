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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.diego.storytell.MainActivity
import br.com.diego.storytell.MyViewModel
import br.com.diego.storytell.R
import br.com.diego.storytell.adapters.PostAdapter
import br.com.diego.storytell.models.Post
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {
    private lateinit var listView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var model: MyViewModel
    private lateinit var ctx: Context

    override fun onAttach(context: Context) {
        this.ctx = context
        super.onAttach(context)
    }

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

        listView = view.findViewById(R.id.recyler_list_view)
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
            val adapter = PostAdapter(ctx, posts)
            val linearLayoutManager = LinearLayoutManager(ctx)
            listView.adapter = adapter
            listView.layoutManager = linearLayoutManager
            /*listView.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    val imageItem = view.findViewById<ImageView>(R.id.thumbnail)
                    (activity as MainActivity?)?.goPostDetailPage(posts[position], imageItem)
                }
             */
        })

        refreshLayout.setOnRefreshListener {
            (activity as MainActivity?)?.recreate()
        }

        return view
    }
}

