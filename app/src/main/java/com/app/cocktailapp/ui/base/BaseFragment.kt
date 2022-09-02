package com.app.cocktailapp.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.app.cocktailapp.R
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {
    private var snackBar: Snackbar? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarColor()
        subscribeUi()
    }

    /**
     * Set a status bar color to this fragment.
     */
    protected open fun setStatusBarColor() {
        activity?.let {
            it.window.statusBarColor = it.getColor(
                R.color.background
            )
        }
    }

    /**
     * Initialize/Subscribe UI properties after Fragment's View created.
     */
    abstract fun subscribeUi()

    protected fun showError(msg: String, onRetry: () -> Unit) {
        view?.let {
            snackBar = Snackbar.make(it, msg, Snackbar.LENGTH_LONG)
            snackBar?.setAction("RETRY") {
                snackBar?.dismiss()
                onRetry.invoke()
            }
            snackBar?.show()
        }
    }

    /**
     * Method to display a message to the user via SnackBar.
     */
    protected fun showMessage(msg: String) {
        view?.let {
            hideError()
            snackBar = Snackbar.make(it, msg, Snackbar.LENGTH_SHORT)
            snackBar?.show()
        }
    }

    /**
     * Method to dismiss the displayed message.
     */
    protected fun hideError() {
        snackBar?.dismiss()
    }

}