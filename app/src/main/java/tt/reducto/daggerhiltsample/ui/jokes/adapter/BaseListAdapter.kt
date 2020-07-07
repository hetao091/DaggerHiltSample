package tt.reducto.daggerhiltsample.ui.jokes.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * ……。
 *
 * <p>......。</p>
 * <ul><li></li></ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020/5/18 13:17<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
private class BaseListAdapter<ItemT, VH : RecyclerView.ViewHolder>(
    initialItems: List<ItemT>,
    private val viewHolderCreator: (ViewGroup, Int) -> VH,
    private val viewHolderBinder: (holder: VH, item: ItemT, position: Int) -> Unit,
    private val viewHolderPartialBinder: ((holder: VH, item: ItemT, position: Int, updates: List<Any>) -> Unit)? = null,
    private val viewTypeFunction: ((ItemT) -> Int)? = null,
    private val itemIdFunction: ((ItemT) -> Long)? = null,
    private val onViewHolderAttached: ((VH) -> Unit)? = null,
    private val onViewHolderDetached: ((VH) -> Unit)? = null,
    private val onViewHolderRecycled: ((VH) -> Unit)? = null,
    private val onViewHolderRecycleFailed: ((VH) -> Boolean)? = null,
    private val onAttachedToRecyclerView: ((RecyclerView) -> Unit)? = null,
    private val onDetachedFromRecyclerView: ((RecyclerView) -> Unit)? = null
) : ListAdapter<ItemT, VH>(DiffAdapterCallback<ItemT>()) {

    init {
        setHasStableIds(true)
        submitList(initialItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        viewHolderCreator(parent, viewType)

    override fun onBindViewHolder(holder: VH, position: Int) =
        viewHolderBinder(holder, getItem(position), position)

    override fun getItemViewType(position: Int): Int =
        viewTypeFunction?.invoke(getItem(position)) ?: super.getItemViewType(position)

    override fun getItemId(position: Int): Long =
        itemIdFunction?.invoke(getItem(position)) ?: super.getItemId(position)

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) =
        viewHolderPartialBinder?.invoke(holder, getItem(position), position, payloads)
            ?: super.onBindViewHolder(holder, position, payloads)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) =
        onAttachedToRecyclerView?.invoke(recyclerView)
            ?: super.onAttachedToRecyclerView(recyclerView)

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) =
        onDetachedFromRecyclerView?.invoke(recyclerView)
            ?: super.onDetachedFromRecyclerView(recyclerView)

    override fun onViewAttachedToWindow(holder: VH) =
        onViewHolderAttached?.invoke(holder) ?: super.onViewAttachedToWindow(holder)

    override fun onViewRecycled(holder: VH) =
        onViewHolderRecycled?.invoke(holder) ?: super.onViewRecycled(holder)

    override fun onViewDetachedFromWindow(holder: VH) =
        onViewHolderDetached?.invoke(holder) ?: super.onViewDetachedFromWindow(holder)

    override fun onFailedToRecycleView(holder: VH): Boolean =
        onViewHolderRecycleFailed?.invoke(holder) ?: super.onFailedToRecycleView(holder)
}

/**
 * Composes a [RecyclerView.Adapter]. Never Subclass an adapter again.
 */
fun <ItemT, VH : RecyclerView.ViewHolder> listAdapterOf(
    initialItems: List<ItemT>,
    viewHolderCreator: (parent: ViewGroup, viewType: Int) -> VH,
    viewHolderBinder: (holder: VH, item: ItemT, position: Int) -> Unit,
    viewHolderPartialBinder: ((holder: VH, item: ItemT, position: Int, updates: List<Any>) -> Unit)? = null,
    viewTypeFunction: ((ItemT) -> Int)? = null,
    itemIdFunction: ((ItemT) -> Long)? = null,
    onViewHolderAttached: ((VH) -> Unit)? = null,
    onViewHolderDetached: ((VH) -> Unit)? = null,
    onViewHolderRecycled: ((VH) -> Unit)? = null,
    onViewHolderRecycleFailed: ((VH) -> Boolean)? = null,
    onAttachedToRecyclerView: ((RecyclerView) -> Unit)? = null,
    onDetachedFromRecyclerView: ((RecyclerView) -> Unit)? = null
): ListAdapter<ItemT, VH> =
    BaseListAdapter(
        initialItems,
        viewHolderCreator,
        viewHolderBinder,
        viewHolderPartialBinder,
        viewTypeFunction,
        itemIdFunction,
        onViewHolderAttached,
        onViewHolderDetached,
        onViewHolderRecycled,
        onViewHolderRecycleFailed,
        onAttachedToRecyclerView,
        onDetachedFromRecyclerView
    )