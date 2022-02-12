package amagen.magen.mediastacknews.Adapters

import amagen.magen.mediastacknews.databinding.LayoutArticleBinding
import amagen.magen.mediastacknews.models.Article
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ArticlesRecyclerview(
    private val articles: List<Article>,
    private val eventListener: EventListener
):RecyclerView.Adapter<ArticlesRecyclerview.ViewHolder>(){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticlesRecyclerview.ViewHolder {
        return ViewHolder(LayoutArticleBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ArticlesRecyclerview.ViewHolder, position: Int) {
        //init the article card
        val article = articles[position]
        holder.binding.tvCategory.text=article.category
        holder.binding.tvArticleSubject.text = article.title
        if(article.image.toString() != "null" && !article.image.isNullOrEmpty()){
            Picasso.get().load(article.image).into(holder.binding.ivArticleImg)
        }
    }

    override fun getItemCount()=articles.size



    inner class ViewHolder(val binding:LayoutArticleBinding):RecyclerView.ViewHolder(binding.root){
        init{
            // set click event listener
            binding.root.setOnClickListener {
                if(adapterPosition != RecyclerView.NO_POSITION){
                    eventListener.onArticleRootClick(articles[adapterPosition], adapterPosition, binding.ivArticleImg.drawable)
                }
            }
        }
    }


    interface EventListener{
        fun onArticleRootClick(article: Article, adapterPosition: Int, drawable: Drawable)
    }
}