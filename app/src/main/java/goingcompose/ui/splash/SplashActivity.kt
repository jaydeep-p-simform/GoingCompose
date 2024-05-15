package goingcompose.ui.splash

import androidx.activity.viewModels
import goingcompose.R
import goingcompose.ui.base.BaseAppCompatActivity
import goingcompose.databinding.ActivitySplashBinding
import goingcompose.ui.sample.SampleActivity
import goingcompose.utils.extension.launchActivity
import goingcompose.utils.extension.observeEvent
import goingcompose.ui.splash.SplashViewModel.Destination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseAppCompatActivity<ActivitySplashBinding, SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.activity_splash

    override fun setupViewModel() {
        super.setupViewModel()

        viewModel.goToScreen.observeEvent(this) { destination ->
            when (destination) {
                Destination.Home -> openHomeScreen()
                Destination.Login -> openLoginScreen()
                Destination.Sample -> openSampleScreen()
            }
        }
    }

    private fun openLoginScreen() {
        // TODO : Open Login screen
    }

    private fun openHomeScreen() {
        // TODO : Open Home screen
    }

    private fun openSampleScreen() {
        launchActivity<SampleActivity>()
    }
}
