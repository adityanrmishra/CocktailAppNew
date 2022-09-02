package com.app.cocktailapp.ui.model

import androidx.annotation.IntDef
import com.app.cocktailapp.ui.model.ErrorType.Companion.ALERT
import com.app.cocktailapp.ui.model.ErrorType.Companion.DIALOG
import com.app.cocktailapp.ui.model.ErrorType.Companion.SNACK
import com.app.cocktailapp.ui.model.ErrorType.Companion.TOAST

@IntDef(SNACK, TOAST, ALERT, DIALOG)
annotation class ErrorType {
    companion object {
        const val SNACK = 1
        const val TOAST = 2
        const val ALERT = 3
        const val DIALOG = 4
    }
}