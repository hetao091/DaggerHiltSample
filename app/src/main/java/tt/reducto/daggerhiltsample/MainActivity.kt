package tt.reducto.daggerhiltsample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import dagger.hilt.android.AndroidEntryPoint
import tt.reducto.daggerhiltsample.data.model.Repo
import tt.reducto.daggerhiltsample.databinding.ActivityMainBinding
import tt.reducto.daggerhiltsample.databinding.ItemLayoutBinding
import tt.reducto.daggerhiltsample.ui.entry.ContainerActivity
import tt.reducto.daggerhiltsample.ui.jokes.adapter.BindingViewHolder
import tt.reducto.daggerhiltsample.ui.jokes.adapter.getViewHolder
import tt.reducto.daggerhiltsample.ui.jokes.adapter.listAdapterOf
import tt.reducto.daggerhiltsample.ui.jokes.viewModel.JokesViewModel
import tt.reducto.daggerhiltsample.utils.Constant
import tt.reducto.daggerhiltsample.utils.UIState
import tt.reducto.daggerhiltsample.utils.viewBinding
import tt.reducto.log.TTLog

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //
    private val mJokesViewModel: JokesViewModel by viewModels()

    //
    private var mList: MutableList<Repo> = mutableListOf()

    //
    private lateinit var mAdapter: ListAdapter<Repo, BindingViewHolder<ItemLayoutBinding>>

    //
    private val mBinding: ActivityMainBinding by viewBinding {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        TTLog.d("onCreate")
        initNetWorkState()
        initAdapter()
        initData()
        mBinding.swipeRefreshLayout.setOnRefreshListener {
            mJokesViewModel.fetchJokes()
        }
    }

    /**
     *  适配器。
     */
    private fun initAdapter() {

        mAdapter = listAdapterOf(
            initialItems = mList,

            viewHolderCreator = { viewGroup, _ ->
                viewGroup.getViewHolder(ItemLayoutBinding::inflate).apply {
                    itemView.setOnClickListener {
                        val intent = Intent(this@MainActivity, ContainerActivity::class.java)
                        intent.putExtra(
                            Constant.MAIN_INTENT_TO_CONTAINER_CONTENT_KET,
                            mAdapter.currentList[adapterPosition].content
                        )
                        startActivity(intent)
                    }
                }
            },
            viewHolderBinder = { viewHolder, data, _ ->
                viewHolder.bindView(data)
            }

        )

        mBinding.rvJokes.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
        }


    }

    private fun initData() {

        mJokesViewModel.jokes.observe(this, Observer{
            when (it) {

                is UIState.Error -> {
                    toast(it.msg)
                    // 显示
                    showLoading(false)
                }

                is UIState.Success -> {
                    it.data.let { joke ->
                        mAdapter.submitList(joke.data.list)
                    }
                    showLoading(false)
                }

                is UIState.Loading -> {
                    //  加载
                    showLoading(true)
                }

                is UIState.Failure -> {
                    toast(it.msg)
                    showLoading(false)
                }

                is UIState.DismissLoading -> {

                }
            }
        })

    }


    private var BindingViewHolder<ItemLayoutBinding>.result by BindingViewHolder.BindingDataLazy<Repo>()

    private fun BindingViewHolder<ItemLayoutBinding>.bindView(data: Repo) {
        result = data
        binding.tvJokes.text = data.content
    }

    private fun toast(s: String = "") = Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    private fun showLoading(isLoading: Boolean) {
        mBinding.swipeRefreshLayout.isRefreshing = isLoading
    }

    private fun initNetWorkState() {
        mJokesViewModel.networkState.observe(this, Observer{
            when (it) {
                false -> {
                    mBinding.textViewNetworkStatus.text = "无网络"
                    mBinding.networkStatusLayout.apply {
                        visibility = View.VISIBLE
                        setBackgroundColor(
                            ContextCompat.getColor(
                                this@MainActivity,
                                R.color.colorStatusNotConnected
                            )
                        )
                    }
                }
                else -> {
                    mBinding.networkStatusLayout.apply {
                        visibility = View.GONE
                    }
                }
            }
        })
    }
}