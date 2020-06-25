package br.com.diego.storytell

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.diego.storytell.models.News
import br.com.diego.storytell.models.Post
import br.com.diego.storytell.services.NewsFactory
import br.com.diego.storytell.services.NewsService
import br.com.diego.storytell.services.PostFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MyViewModel: ViewModel() {
    private val posts: MutableLiveData<List<Post>> by lazy {
        MutableLiveData<List<Post>>().also {
            loadPosts()
        }
    }

    private val news: MutableLiveData<List<News>> by lazy {
        MutableLiveData<List<News>>().also {
            loadNews()
        }
    }

    fun getPosts(): LiveData<List<Post>> {
        return posts
    }

    fun getNews(): LiveData<List<News>> {
        return news
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

    private fun loadNews() {
        try {
            val service= NewsFactory.makeNewsService()
            val response = service.getNews()

            response.enqueue(object: Callback<List<News>> {
                override fun onResponse(call: Call<List<News>>, res: Response<List<News>>) {
                    news.value = res.body()
                }

                override fun onFailure(call: Call<List<News>>, t: Throwable) {
                    Log.d("onFailureMode", "$t\n$response")
                }
            })
        } catch (e: Exception) {
            Log.d("Deumal", "ashdaksd")
        }
    }
}