package br.com.diego.storytell.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.diego.storytell.MainActivity
import br.com.diego.storytell.MyViewModel
import br.com.diego.storytell.R
import br.com.diego.storytell.adapters.PostAdapter
import br.com.diego.storytell.interfaces.OnItemClickListener
import br.com.diego.storytell.models.News
import br.com.diego.storytell.models.Post
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.lang.Exception

class ExploreFragment : Fragment() {
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
        model.getNews().observe(viewLifecycleOwner, Observer<List<News>> { news ->
            val listItems = arrayOfNulls<String>(news.size)
            for (i in news.indices) {
                val new = news[i]
                listItems[i] = new.name
            }
            if (news.isNotEmpty()) {
                progressBar.visibility = View.GONE
                listView.visibility = View.VISIBLE
            }
            val adapter = PostAdapter(ctx, news)
            val linearLayoutManager = LinearLayoutManager(ctx)
            listView.adapter = adapter
            adapter.setOnItemClickListener(object : OnItemClickListener {
                override fun onClickListener(post: Any,  imageView: ImageView) {
                    val modelPost = post as News
                    val post = Post(modelPost.id, modelPost.createdAt, modelPost.name, modelPost.image, modelPost.tag, modelPost.content)
                    (activity as MainActivity?)?.goPostDetailPage(post, imageView)
                }
            })
            listView.layoutManager = linearLayoutManager
        })

        refreshLayout.setOnRefreshListener {
            (activity as MainActivity?)?.recreate()
        }

        return view
    }
}