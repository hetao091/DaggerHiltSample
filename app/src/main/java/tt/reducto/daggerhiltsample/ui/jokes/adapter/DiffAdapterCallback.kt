package tt.reducto.daggerhiltsample.ui.jokes.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

/**
 * ……。
 *
 * <p>......。</p>
 * <ul><li></li></ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020/5/18 13:18<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
class DiffAdapterCallback<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        if (oldItem is Differentiable && newItem is Differentiable) oldItem.diffId == newItem.diffId
        else false

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T) =
        if (oldItem is Differentiable && newItem is Differentiable) oldItem.areContentsTheSame(newItem)
        else oldItem == newItem

    override fun getChangePayload(oldItem: T, newItem: T): Any? =
        if (oldItem is Differentiable && newItem is Differentiable) oldItem.getChangePayload(newItem)
        else null
}


interface Differentiable {

    val diffId: String

    fun areContentsTheSame(other: Differentiable): Boolean = this == other

    fun getChangePayload(other: Differentiable): Any? = null

    companion object {

        fun fromCharSequence(charSequenceSupplier: () -> CharSequence): Differentiable {
            val id = charSequenceSupplier().toString()

            return object : Differentiable {
                override val diffId: String get() = id

                override fun equals(other: Any?): Boolean = id == other

                override fun hashCode(): Int = id.hashCode()
            }
        }
    }

}