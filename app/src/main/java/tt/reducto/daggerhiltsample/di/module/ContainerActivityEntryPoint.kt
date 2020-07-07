package tt.reducto.daggerhiltsample.di.module

import androidx.fragment.app.FragmentManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import tt.reducto.daggerhiltsample.ui.entry.EntryPointFragmentFactory


/**
 * ……。
 *
 * <p>......。</p>
 * <ul><li></li></ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020/7/7 14:15<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
@EntryPoint
@InstallIn(ActivityComponent::class)
interface ContainerActivityEntryPoint {
    fun getFragmentManager(): FragmentManager
    fun getFragmentFactory(): EntryPointFragmentFactory
}