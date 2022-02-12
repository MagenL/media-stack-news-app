package amagen.magen.mediastacknews.ui.articles.allCategories

import amagen.magen.mediastacknews.Adapters.ArticlesRecyclerview
import amagen.magen.mediastacknews.R
import amagen.magen.mediastacknews.databinding.FragmentArticlesBinding
import amagen.magen.mediastacknews.models.Article
import amagen.magen.mediastacknews.MainActivityViewModel
import amagen.magen.mediastacknews.ui.articles.category.articlesInCategory.ArticlesInCategory

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

class ArticlesFragment : Fragment(), ArticlesRecyclerview.EventListener {

    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!
    private lateinit var articlesViewModel: ArticlesViewModel
    private lateinit var mainActivityViewModel: MainActivityViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //tide viewmodel to the fragment
        articlesViewModel = ViewModelProvider(this)[ArticlesViewModel::class.java]
        //init binding
        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        //init main activity view model
        mainActivityViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvArticles.layoutManager = LinearLayoutManager(requireContext())
        // init home screen -> shows 1 article of each category
        articlesViewModel.UIArticlesByCategory.observe(viewLifecycleOwner){UIArticles->
            binding.rvArticles.adapter = ArticlesRecyclerview(UIArticles ,this)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    //on category clicked -> move to all articles in the category
    override fun onArticleRootClick(article: Article, adapterPosition: Int, drawable: Drawable) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.app_container,
                ArticlesInCategory(articlesViewModel.getArticlesByCategory(adapterPosition))
            )
            .addToBackStack(null)
            .commit()
    }


}