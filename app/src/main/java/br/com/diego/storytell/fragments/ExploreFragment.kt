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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.diego.storytell.MainActivity
import br.com.diego.storytell.MyViewModel
import br.com.diego.storytell.R
import br.com.diego.storytell.adapters.PostAdapter
import br.com.diego.storytell.models.News
import br.com.diego.storytell.models.Post
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.lang.Exception

class ExploreFragment : Fragment() {
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
            val adapter = activity?.applicationContext?.let { PostAdapter(it, news) }
            listView.adapter = adapter
            listView.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    val imageItem = view.findViewById<ImageView>(R.id.thumbnail)
                    val new = news[position]
                    try {
                        val post = Post(new.id, new.createdAt, new.name, new.image, new.tag, new.content)
                        (activity as MainActivity?)?.goPostDetailPage(post, imageItem)
                    } catch (e: Exception) {
                        Log.d("ERRORRRRRRRR", "$e")
                    }
                }
        })

        refreshLayout.setOnRefreshListener {
            (activity as MainActivity?)?.recreate()
        }

        return view
    }
}