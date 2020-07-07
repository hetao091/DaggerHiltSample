package tt.reducto.daggerhiltsample.di.module

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import tt.reducto.daggerhiltsample.ui.entry.EntryPointFragmentFactory
import tt.reducto.daggerhiltsample.ui.entry.FragmentDataTest


/**
 * ……。
 *
 * <p>......。</p>
 * <ul><li></li></ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020/7/6 19:39<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
@Module
@InstallIn(ActivityComponent::class)
object EntryPointModule {

    @Provides
    @ActivityScoped
    fun provideFragmentManager(activity: Activity): FragmentManager {
        return (activity as AppCompatActivity).supportFragmentManager
    }

    @Provides
    @ActivityScoped
    fun provideData(fragmentDataTest: FragmentDataTest) = EntryPointFragmentFactory(fragmentDataTest)
}