package amagen.magen.mediastacknews.ui.articles.category.articlesInCategory

import amagen.magen.mediastacknews.Adapters.ArticlesRecyclerview
import amagen.magen.mediastacknews.R
import amagen.magen.mediastacknews.databinding.ArticlesInCategoryFragmentBinding
import amagen.magen.mediastacknews.models.Article
import amagen.magen.mediastacknews.MainActivityViewModel
import amagen.magen.mediastacknews.ui.articles.category.Article.ArticleFragment
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson


class ArticlesInCategory(val articles:List<Article>) : Fragment(), ArticlesRecyclerview.EventListener {




    private var _binding:ArticlesInCategoryFragmentBinding? = null
    val binding get() = _binding!!

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ArticlesInCategoryFragmentBinding.inflate(layoutInflater,container,false)
        mainActivityViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init UI
        binding.rvArticlesInCategory.adapter = ArticlesRecyclerview(articles,this)
        binding.rvArticlesInCategory.layoutManager = LinearLayoutManager(requireContext())
    }

    //move to article page
    override fun onArticleRootClick(article: Article, adapterPosition: Int, drawable: Drawable) {
        val mArticleFragment = ArticleFragment()
        val bundle = Bundle()
        bundle.putString("Article", Gson().toJson(article))
        mArticleFragment.arguments= bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.app_container, mArticleFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}