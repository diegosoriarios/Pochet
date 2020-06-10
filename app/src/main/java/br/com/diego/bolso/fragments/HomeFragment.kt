package br.com.diego.bolso.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import br.com.diego.bolso.Endpoint
import br.com.diego.bolso.NetworkUtils
import br.com.diego.bolso.R
import br.com.diego.bolso.Repository
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private val menuItems: ArrayList<Repository>? = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        //val menuItems: Array<String> = arrayOf("Something", "Another", "Another One")

        val listView: ListView = view.findViewById(R.id.recipe_list_view)

        /*val listViewAdapter: ArrayAdapter<Repository> = ArrayAdapter<Repository>(
            activity?.applicationContext!!,
            R.layout.custom_list,
            menuItems!!
        )*/
        val listViewAdapter = object : ArrayAdapter<Repository>(activity?.applicationContext!!, R.layout.custom_list, R.id.name, menuItems!!) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val text1 = view.findViewById(R.id.name) as TextView
                val text2 = view.findViewById(R.id.language) as TextView
                var icon = view.findViewById(R.id.icon) as ImageView
                text1.setText(menuItems!![position].name)
                text2.setText(menuItems!![position].language)

                Picasso.get().load("https://api.adorable.io/avatars/285/" + menuItems!![position].name).into(icon)
                //icon.setImageURI(Uri.parse()
                return view
            }
        }

        listView.adapter = listViewAdapter

        return view
    }

    /*companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/

    private fun getData() {
        val retrofitClient = NetworkUtils.getRetrofitInstance("https://api.github.com/users/diegosoriarios/")

        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.getPosts()

        callback.enqueue(object: Callback<List<Repository>> {
            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Toast.makeText(activity?.baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Repository>>, response: Response<List<Repository>>) {
                Toast.makeText(activity?.baseContext, "Done", Toast.LENGTH_SHORT).show()
                Log.d("Response", response.toString())
                response.body()?.forEach {
                    Log.d("Response", it.name)
                    menuItems?.add(it)
                }
            }
        })
    }
}