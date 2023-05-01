package com.example.android.politicalpreparedness

import androidx.databinding.BindingAdapter
import androidx.core.content.ContextCompat.startActivity
import android.net.Uri
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.utils.ProgressState
import com.example.android.politicalpreparedness.utils.ProgressState.*
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT

@BindingAdapter("externalUrl")
fun bindUrlToTextView(textView: TextView, url: String?) {
    if (TextUtils.isEmpty(url)) {
        textView.visibility = View.GONE
    } else {
        textView.visibility = View.VISIBLE
        textView.setOnClickListener { view ->
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(view.context, browserIntent, null)
        }
    }
}

@BindingAdapter("dataSavedState")
fun bindSavedStateToTextView(textView: TextView, isSaved: Boolean) {
    when (isSaved) {
        true -> textView.setText(R.string.button_label_unfollow_election)
        false -> textView.setText(R.string.button_label_follow_election)
    }
}

@BindingAdapter("recyclerViewVisibilityState")
fun bindSearchStateToImageView(
    recyclerView: RecyclerView,
    searchState: ProgressState
) {
    when (searchState) {
        LOADING_SUCCESS -> recyclerView.visibility = View.VISIBLE
        else -> recyclerView.visibility = View.INVISIBLE
    }
}

@BindingAdapter("constraintLayoutVisibilityState")
fun bindSearchStateToConstraintLayout(
    layout: ConstraintLayout,
    searchState: ProgressState
) {
    when (searchState) {
        LOADING_SUCCESS -> layout.visibility = View.VISIBLE
        else -> layout.visibility = View.INVISIBLE
    }
}


@BindingAdapter("textViewVisibilityState")
fun bindSearchStateToTextView(
    textView: TextView,
    searchState: ProgressState
) {
    when (searchState) {
        INITIAL -> textView.visibility = View.VISIBLE
        LOADING_NO_DATA -> textView.visibility = View.VISIBLE
        else -> textView.visibility = View.GONE
    }
}

@BindingAdapter("stateValue")
fun Spinner.setNewValue(value: String?) {
    val adapter = toTypedAdapter<String>(this.adapter as ArrayAdapter<*>)
    val position = when (adapter.getItem(0)) {
        is String -> adapter.getPosition(value)
        else -> this.selectedItemPosition
    }
    if (position >= 0) {
        setSelection(position)
    }
}

inline fun <reified T> toTypedAdapter(adapter: ArrayAdapter<*>): ArrayAdapter<T>{
    return adapter as ArrayAdapter<T>
}

/**
 * Use Glide library to load an image from url into an [ImageView]
 */
//@BindingAdapter("imageUrl")
//fun bindUrlToImage(imgView: ImageView, imgUrl: String?) {
//    imgUrl?.let {
//        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
//        Glide.with(imgView.context)
//            .load(imgUri)
//            .apply(
//                RequestOptions()
//                    .placeholder(R.drawable.ic_profile)
//                    .error(R.drawable.ic_profile)
//                    .circleCrop()
//            )
//            .into(imgView)
//    }
//}

//@BindingAdapter("progressVisibilityState")
//fun bindSearchStateToImageView(
//    imageView: ImageView,
//    searchState: ProgressState
//) {
//    when (searchState) {
//        LOADING_ACTIVE -> {
//            imageView.visibility = View.VISIBLE
//            imageView.setImageResource(R.drawable.loading_animation)
//        }
//        LOADING_FAILURE -> {
//            imageView.visibility = View.VISIBLE
//            imageView.setImageResource(R.drawable.ic_connection_error)
//            Snackbar.make(
//                imageView,
//                imageView.context.getString(R.string.loading_failure_snack),
//                LENGTH_SHORT
//            ).show()
//        }
//        else -> imageView.visibility = View.GONE
//    }
//}