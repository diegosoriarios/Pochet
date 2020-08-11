package br.com.diego.storytell.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import br.com.diego.storytell.R
import br.com.diego.storytell.interfaces.OnItemClickListener
import br.com.diego.storytell.interfaces.OnLongClickListener
import br.com.diego.storytell.models.News
import br.com.diego.storytell.models.Post
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*

class PostAdapter(val context: Context, private val dataSource: List<Any>)
    : RecyclerView.Adapter<PostAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var onItemClickListener: OnItemClickListener
    private lateinit var onLongClickListener: OnLongClickListener

    class MyViewHolder(view: View, dataSource: List<Any>, onItemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnLongClickListener(View.OnLongClickListener {
                val itemContainer: LinearLayout = it.findViewById(R.id.list_item_container)
                itemContainer.background = ColorDrawable(Color.BLUE)
                var post = dataSource[adapterPosition] as Post
                Log.d("LONG CLICK", post.name)
                true
            })
            view.setOnClickListener(View.OnClickListener {
                onItemClickListener.onClickListener(dataSource[adapterPosition], it.findViewById(R.id.thumbnail))
            })
        }
    }

    fun setOnLongClickListener(onLongClickListener: OnLongClickListener) {
        this.onLongClickListener = onLongClickListener
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    private fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getView(position, holder.itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val rowView = inflater.inflate(R.layout.list_item, parent, false)

        return MyViewHolder(rowView, dataSource, onItemClickListener)
    }

    //4
    private fun getView(position: Int, rowView: View): View {
        val titleTextView = rowView.findViewById(R.id.name) as TextView

        val subtitleTextView = rowView.findViewById(R.id.subtitle) as TextView

        val thumbnailImageView: ImageView = rowView.findViewById(R.id.thumbnail) as ImageView

        if (getItem(position) is Post) {
            val post = getItem(position) as Post
            titleTextView.text = post.name
            subtitleTextView.text = post.createdAt

            Picasso
                .get()
                .load(post.image)
                .placeholder(R.mipmap.ic_launcher)
                .resize(150, 150)
                .into(thumbnailImageView)
        } else if (getItem(position) is News){
            val post = getItem(position) as News
            titleTextView.text = post.name
            subtitleTextView.text = post.createdAt

            Picasso
                .get()
                .load(post.image)
                .placeholder(R.mipmap.ic_launcher)
                .resize(150, 150)
                .into(thumbnailImageView)
        }

        return rowView
    }
}