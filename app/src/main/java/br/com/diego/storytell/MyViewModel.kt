package br.com.diego.storytell

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.diego.storytell.models.Post
import br.com.diego.storytell.services.PostFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyViewModel: ViewModel() {
    private val posts: MutableLiveData<List<Post>> by lazy {
        MutableLiveData<List<Post>>().also {
            loadPosts()
        }
    }

    fun getPosts(): LiveData<List<Post>> {
        return posts
    }

    private fun loadPosts() {
        val service = PostFactory.makePostService()
        val response = service.getPosts()

        response.enqueue(object: Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, res: Response<List<Post>>) {
                posts.value = res.body()
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.d("onFailureMode", "$t\n$response")
            }
        })
    }
}