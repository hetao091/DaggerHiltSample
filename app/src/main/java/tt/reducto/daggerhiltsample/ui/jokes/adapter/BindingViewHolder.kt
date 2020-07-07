package tt.reducto.daggerhiltsample.ui.jokes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import tt.reducto.daggerhiltsample.R


import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/**
 * 用于ViewBinding的通用ViewHolder。
 *
 * <p>对外提供数据与视图的匹配。</p>
 * <ul><li></li></ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020/5/18 12:38<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
open class BindingViewHolder<T : ViewBinding> private constructor(val binding: T) :
    RecyclerView.ViewHolder(binding.root) {
    constructor(
        parent: ViewGroup,
        creator: (inflater: LayoutInflater, root: ViewGroup, attachToRoot: Boolean) -> T
    ) : this(
        creator(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    /**
     *  委托
     *
     * @param T itemView适配数据。
     */
    class BindingDataLazy<T> : ReadWriteProperty<BindingViewHolder<*>, T> {
        override fun getValue(thisRef: BindingViewHolder<*>, property: KProperty<*>): T {
            @Suppress("UNCHECKED_CAST")
            return thisRef.propertyByMap[property.name] as T
        }

        override fun setValue(thisRef: BindingViewHolder<*>, property: KProperty<*>, value: T) {
            thisRef.propertyByMap[property.name] = value
        }
    }
}

inline fun <reified T : ViewBinding> BindingViewHolder<*>.binding() = (binding as T)

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewBinding> BindingViewHolder<*>.typed() = this as BindingViewHolder<T>


/**
 *
 * @receiver ViewGroup
 * @param creator 布局生成器
 * @return BindingViewHolder<T>
 */
fun <T : ViewBinding> ViewGroup.getViewHolder(
    creator: (inflater: LayoutInflater, root: ViewGroup, attachToRoot: Boolean) -> T
): BindingViewHolder<T> =
    BindingViewHolder(this, creator)

/**
 *
 */
private inline val BindingViewHolder<*>.propertyByMap
    get() = itemView.getOrPutTag<MutableMap<String, Any?>>(
        R.id.recyclerview_view_binding_map,
        ::mutableMapOf
    )

/**
 * getTag()内部使用 { <code>private SparseArray<Object> mKeyedTags;</code> } 进行查找
 *
 * @receiver View
 * @param id Int 保证ID唯一
 * @param initializer 容器初始化
 * @return T 存储容器-> map
 */
private inline fun <reified T> View.getOrPutTag(@IdRes id: Int, initializer: () -> T) =
    getTag(id) as? T ?: initializer().also {
        setTag(id, it)
    }