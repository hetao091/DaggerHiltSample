package tt.reducto.daggerhiltsample.ui.entry

import javax.inject.Inject

class FragmentDataTest @Inject constructor() {
    private var data: String  = ""
    fun setData(msg: String) {
        data = msg
    }
    fun getData() = data
}
