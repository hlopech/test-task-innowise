package com.example.pants.presentation.extentions

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pants.presentation.extentions.DialogTags.ERROR_DIALOG_TAG
import com.example.pants.presentation.ui.androidView.ErrorDialogFragment

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showErrorDialog(message: String) {
    val dialog = ErrorDialogFragment.newInstance(message)
    dialog.show(childFragmentManager, ERROR_DIALOG_TAG)
}

object DialogTags {
    const val ERROR_DIALOG_TAG = "ErrorDialog"
}
