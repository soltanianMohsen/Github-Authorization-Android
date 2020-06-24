import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.mydigipay.challenge.R


fun Activity.lightStatusBar(statusBarColor: Int = -1) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        when (window.decorView.rootView.systemUiVisibility) {
            0 -> window.decorView.rootView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    window.decorView.rootView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR + View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                } else {
                    window.decorView.rootView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }
        }
        window.statusBarColor = if (statusBarColor == -1) Color.WHITE else statusBarColor
    }
}

fun RecyclerView.runAnimation() {
    layoutAnimation =
        AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
}

fun Fragment.showKeyboard(activity: Activity) {
    val imm: InputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}


fun Fragment.hideKeyboard(activity: Activity,view: View) {
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view!!.windowToken, 0)
}



