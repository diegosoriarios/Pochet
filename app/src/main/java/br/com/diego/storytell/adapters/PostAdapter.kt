package br.com.diego.storytell.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import br.com.diego.storytell.R
import br.com.diego.storytell.models.News
import br.com.diego.storytell.models.Post
import com.squareup.picasso.Picasso

class PostAdapter(private val context: Context, private val dataSource: List<Any>) : BaseAdapter() {
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