package com.example.appbudgetv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.appbudgetv2.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar




class MainActivity : AppCompatActivity() {

    private lateinit var menu: Menu
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding


    //meter funcoes das navbars
    fun showToolbar() {
        supportActionBar?.show()
    }

    fun hideToolbar() {
        supportActionBar?.hide()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



    var idMenuAtual : Int=R.menu.menu_main
        set(value){
            if(value!=field){
                field=value
                invalidateOptionsMenu()
            }
        }

    var fragment : Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        //ativar a seta pra voltar atras
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.hide()

        /*supportFragmentManager.addOnBackStackChangedListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.welcomeFragment)
            if (fragment is WelcomeFragment) {
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()
            }
        }*/
      //  supportActionBar?.hide()


        //desativar a toolbar num ecra especifico e ativar noutros
        // as per defined in your FragmentContainerView
        var navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        var navController = navHostFragment.navController
        var bottomNav : BottomNavigationView = findViewById(R.id.bottomNav)
        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            if (nd.id == R.id.homeFragment || nd.id == R.id.listaDespesasFragment || nd.id == R.id.verDespesaFragment) {
                bottomNav.visibility = View.VISIBLE
            } else {
                bottomNav.visibility = View.GONE
            }
        }


            bottomNav.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.action_savings -> {
                        // Navigate using the IDs you defined in your Nav Graph
                       navController.navigate(R.id.listaDespesasFragment)
                        //val intent = Intent(this, ListaDespesasFragment::class.java)
                        //startActivity(intent)
                        //loadFragment(ListaDespesasFragment())
                        true
                    }
                    R.id.action_graphics -> {
                        //loadFragment(ChatFragment())
                        true
                    }
                    R.id.action_more -> {
                        // loadFragment(SettingFragment())
                        true
                    }

                    else -> {false}
                }
            }
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        //setContentView(R.layout.activity_main)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (item.itemId == R.id.action_settings) {
            return true
        }



        val opcaoProcessada = when (fragment) {
            is AdicionarDespesaFragment -> (fragment as AdicionarDespesaFragment).processaOpcaoMenu(item)
            is ListaDespesasFragment-> (fragment as ListaDespesasFragment).processaOpcaoMenu(item)
            is VerDespesaFragment->(fragment as VerDespesaFragment).processaOpcaoMenu(item)
            else -> false
        }
        return if (opcaoProcessada) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }


    }

    fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_content_main,fragment).addToBackStack(null)
        transaction.commit()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(idMenuAtual, menu)
        this.menu=menu
        return true
    }
}