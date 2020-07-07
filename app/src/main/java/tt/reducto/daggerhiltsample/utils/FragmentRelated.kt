package tt.reducto.daggerhiltsample.utils

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * ……。
 *
 * <p>......。</p>
 * <ul><li></li></ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020/5/18 10:21<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
fun <T> Fragment.viewBindingByLazy(initialise: () -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {

        private var binding: T? = null

        private var viewLifecycleOwner: LifecycleOwner? = null
        private val mainHandler = Handler(Looper.getMainLooper())


        init {
            this@viewBindingByLazy
                .viewLifecycleOwnerLiveData
                .observe(this@viewBindingByLazy, Observer { newLifecycleOwner ->
                    viewLifecycleOwner
                        ?.lifecycle
                        ?.removeObserver(this)

                    viewLifecycleOwner = newLifecycleOwner.also {
                        it.lifecycle.addObserver(this)
                    }
                })
        }
        @MainThread
        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            mainHandler.post {
                binding = null
            }
        }
        @MainThread
        override fun getValue(
            thisRef: Fragment,
            property: KProperty<*>
        ): T {

            return this.binding ?: initialise().also {
                this.binding = it
            }
        }
    }