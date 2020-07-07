package tt.reducto.daggerhiltsample.ui.entry

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import tt.reducto.daggerhiltsample.R
import tt.reducto.daggerhiltsample.databinding.FragmentEntryBinding
import tt.reducto.daggerhiltsample.utils.viewBindingByLazy

/**
 * ……。
 *
 * <p>......。</p>
 * <ul><li></li></ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020/7/6 17:18<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */

@AndroidEntryPoint
class EntryPointFragment(private var fragmentDataTest: FragmentDataTest): Fragment(R.layout.fragment_entry) {


    private val mBinding: FragmentEntryBinding by viewBindingByLazy {
        FragmentEntryBinding.bind(requireView())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.fragmentBaseNumberTextView.text = fragmentDataTest.getData()
    }

}