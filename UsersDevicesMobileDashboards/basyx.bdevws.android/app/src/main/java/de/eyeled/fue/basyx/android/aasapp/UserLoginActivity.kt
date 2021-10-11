package de.eyeled.fue.basyx.android.aasapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import de.eyeled.fue.basyx.android.bdevws.lib.viewmodel.UserAasViewModel
import com.google.android.material.textfield.TextInputEditText
import de.eyeled.fue.basyx.android.bdevws.lib.aas.UserAndroidAAS
import kotlinx.android.synthetic.main.content_user_login.*

class UserLoginActivity : DefaultActivity() {

    private var mUserNameET: TextInputEditText? = null
    private var mPasswortET: TextInputEditText? = null
    private var mLoginButton: Button? = null
    private var mLoginProgress: ProgressBar? = null
    private var mInfoText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.aas_registry, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_change_registry -> {
                startActivity(Intent(this, RegistryActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        initLayout()
    }

    /**
     * Get the AAS View Model Generic Class
     */
    override fun getAasViewModelClass(): Class<out UserAasViewModel> {
        return UserAasViewModel::class.java;
    }

    /**
     * Cast the view model to User View Model
     */
    fun getUserViewModel(): UserAasViewModel?{
        return mAasViewModel as? UserAasViewModel
    }

    private fun initLayout() {
        mFab = findViewById(R.id.fab)

        mUserNameET = findViewById(R.id.userName)
        mPasswortET = findViewById(R.id.userPassword)
        mLoginButton = findViewById(R.id.buttonLogin)
        mInfoText = findViewById(R.id.infoText)
        mLoginProgress = findViewById(R.id.loginProgress)

        mLoginButton?.setOnClickListener {
            checkLogin()
        }
    }

    /**
     * Check the login information by calling the view model
     */
    fun checkLogin(){
        var uName:String = mUserNameET?.text.toString()
        var password:String = mPasswortET?.text.toString()

        if(uName.isNotBlank() && password.isNotBlank()){
            mLoginProgress?.visibility = View.VISIBLE
            mLoginButton?.isEnabled = false
            getUserViewModel()?.checkUserLogin(uName, password)
        }
    }

    /**
     * Init the view model observer when the baSyx service is loaded
     */
    override fun onBaSyxServiceLoaded() {
        // init the observer for the login data
        getUserViewModel()?.mUserLoginCheckLiveData?.observe(this, Observer { userAas ->
            onLoginData(userAas)
        })
    }

    /**
     * check if we have valid login data and update the view accordingly
     */
    fun onLoginData(userAas : UserAndroidAAS?){
        this.runOnUiThread {
            infoText.visibility = View.VISIBLE;
            if(userAas != null){
                infoText.setText("Login successful");
                startActivity(Intent(this, AasRegistryActivity::class.java))
            } else {
                infoText.setText("Login failed");
            }
            mLoginProgress?.visibility = View.INVISIBLE
            mLoginButton?.isEnabled = true
        }
    }
}