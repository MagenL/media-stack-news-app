package amagen.magen.mediastacknews.ui.articles.category.Article

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import amagen.magen.mediastacknews.R
import amagen.magen.mediastacknews.databinding.ArticleFragmentBinding
import amagen.magen.mediastacknews.models.Article
import amagen.magen.mediastacknews.MainActivityViewModel
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.activity.OnBackPressedCallback as OnBackPressedCallback

//val article: Article, val articleImage:Drawable
class ArticleFragment() : Fragment() {

    private var _binding:ArticleFragmentBinding? =null
    private val binding get() = _binding!!
    private lateinit var mainActivityViewModel: MainActivityViewModel

    private lateinit var article:Article

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = arguments?.getString("Article")
        if(bundle != null){
            article = Gson().fromJson(bundle,Article::class.java)
        }
        mainActivityViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        _binding = ArticleFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //init UI

        if( !article.image.isNullOrEmpty() && article.image != "null"){
            Picasso.get().load(article.image).into(binding.ivArticle)
        }
        binding.tvTitle.text = article.title
        binding.tvDescription.text = article.description
        binding.tvCredits.text = resources.getString(R.string.s_published_at_s,article.articleSource,article.publishDate.toString())



        binding.ivFavorite.setOnClickListener {
            it.isSelected = it.isSelected.not()
            if(it.isSelected){
                binding.ivFavorite.setColorFilter(Color.RED)
            }else{
                binding.ivFavorite.setColorFilter(Color.BLACK)
            }
            mainActivityViewModel.addOrRemoveFromDB(article,it.isSelected)
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val isFavorite = mainActivityViewModel.checkIfArticleIsFavorite(article.title)
            lifecycleScope.launch(Dispatchers.Main){
                if(isFavorite){
                    binding.ivFavorite.setColorFilter(Color.RED)
                }else{
                    binding.ivFavorite.setColorFilter(Color.BLACK)
                }
            }
        }





    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //this fragment is accessible from 2 paths (articleCategory/favoriteArticle fragment)
        //need to specify what event we except to get on back-button pressed event.
        val callback = object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                parentFragmentManager.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,callback)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}