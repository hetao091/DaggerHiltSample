package tt.reducto.daggerhiltsample.ui.entry

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import tt.reducto.daggerhiltsample.databinding.ActivityContainerBinding
import tt.reducto.daggerhiltsample.di.module.ContainerActivityEntryPoint
import tt.reducto.daggerhiltsample.utils.Constant
import tt.reducto.daggerhiltsample.utils.viewBinding

@AndroidEntryPoint
class ContainerActivity : AppCompatActivity() {

    private val mBinding: ActivityContainerBinding by viewBinding {
        ActivityContainerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val entryPoint =
            EntryPointAccessors.fromActivity(this, ContainerActivityEntryPoint::class.java)
        val mFragmentFactory = entryPoint.getFragmentFactory()
        entryPoint.getFragmentManager().fragmentFactory = mFragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        supportActionBar?.let {
            it.title = "详情"
        }
        //
        val data = intent.getStringExtra(
            Constant.MAIN_INTENT_TO_CONTAINER_CONTENT_KET
        ) ?: ""
        mFragmentFactory.fragmentDataTest.setData(data)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("ContainerActivity", "--onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }
}
