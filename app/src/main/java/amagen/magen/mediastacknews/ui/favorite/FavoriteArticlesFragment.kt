package amagen.magen.mediastacknews.ui.favorite

import amagen.magen.mediastacknews.Adapters.ArticlesRecyclerview
import amagen.magen.mediastacknews.R
import amagen.magen.mediastacknews.databinding.FragmentFavoriteArticlesBinding
import amagen.magen.mediastacknews.models.Article
import amagen.magen.mediastacknews.MainActivityViewModel
import amagen.magen.mediastacknews.ui.articles.category.Article.ArticleFragment
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.gson.Gson


class FavoriteArticlesFragment : Fragment(), ArticlesRecyclerview.EventListener {

    private var _binding: FragmentFavoriteArticlesBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var mAuth: FirebaseAuth

    lateinit var dashboardViewModel:FavoriteArticlesViewModel
    lateinit var mainActivityViewModel: MainActivityViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel = ViewModelProvider(this)[FavoriteArticlesViewModel::class.java]
        _binding = FragmentFavoriteArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        if(mAuth.currentUser != null){
            changeItemVisibilityIfUserIsLoggedIn()
            mainActivityViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
            dashboardViewModel.ifAuthenticatedGetFavoriteArticles(mainActivityViewModel.applicationDB)
            dashboardViewModel.favoriteArticles.observe(viewLifecycleOwner){
                binding.rvFavArticles.adapter = ArticlesRecyclerview(it,this)
                binding.rvFavArticles.layoutManager = LinearLayoutManager(requireContext())
            }
        }

        binding.signInButton.setOnClickListener {
            binding.pbGoogleBtn.visibility = View.VISIBLE
            signInWithGoogle()
        }
    }

    private fun changeItemVisibilityIfUserIsLoggedIn() {
        binding.signInButton.visibility = View.GONE
        binding.tvInstructionToLogin.visibility = View.GONE
        binding.rvFavArticles.visibility = View.VISIBLE
    }

    private fun signInWithGoogle() {
        // Configure Google Sign In
        // default web client id is a string stored in build resources
        // the compiler can't access to the this folder.
        // this property inject into into the code at the run time.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        val signInIntent = googleSignInClient.signInIntent
        getResult.launch(signInIntent)
    }


    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {

                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                if (task.isSuccessful) {
                    try {
                        // Google Sign In was successful
                        val account = task.getResult(ApiException::class.java)!!
                        firebaseAuthWithGoogle(account.idToken!!)

                    } catch (e: ApiException) {
                        // Google Sign In failed
                        Toast.makeText(requireContext(), "couldn't sign in to google, please try again later", Toast.LENGTH_LONG).show()
                        binding.pbGoogleBtn.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(requireContext(), "something went wrong, please try again later", Toast.LENGTH_LONG).show()
                    binding.pbGoogleBtn.visibility = View.GONE
                }


            }else{
                Toast.makeText(requireContext(),
                    "couldn't sign in to google, please try again later. result code ${it.resultCode}",
                    Toast.LENGTH_LONG)
                    .show()
                binding.pbGoogleBtn.visibility = View.GONE
            }

        }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    changeItemVisibilityIfUserIsLoggedIn()
                    Toast.makeText(requireContext(),
                        "Welcome to favorite page",
                        Toast.LENGTH_SHORT)
                        .show()
                }
                binding.pbGoogleBtn.visibility = View.GONE
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "could not login to google account", Toast.LENGTH_SHORT).show()
                binding.pbGoogleBtn.visibility= View.GONE
            }
    }



    // move to article page
    override fun onArticleRootClick(article: Article, adapterPosition: Int, drawable: Drawable) {

        val mArticleFragment = ArticleFragment()
        val bundle = Bundle()
        bundle.putString("Article", Gson().toJson(article))
        mArticleFragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.app_container, mArticleFragment)
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}