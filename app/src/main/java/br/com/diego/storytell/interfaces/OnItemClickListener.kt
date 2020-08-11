package br.com.diego.storytell.interfaces

import android.widget.ImageView
import br.com.diego.storytell.models.Post

interface OnItemClickListener {
    fun onClickListener(post: Any, imageView: ImageView)
}

interface OnLongClickListener {
    fun onLongClickListener(post: Any)
}