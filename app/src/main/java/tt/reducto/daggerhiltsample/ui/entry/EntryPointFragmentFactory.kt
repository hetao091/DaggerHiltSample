package tt.reducto.daggerhiltsample.ui.entry

import androidx.fragment.app.FragmentFactory


class EntryPointFragmentFactory( var fragmentDataTest: FragmentDataTest) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String) =
        when (className) {
            EntryPointFragment::class.java.name -> EntryPointFragment(fragmentDataTest)
            else -> super.instantiate(classLoader, className)
        }
}
