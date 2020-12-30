package sa.net.ibtikar.android.view.ui.details.person

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.activity_person_detail.*
import kotlinx.coroutines.*
import org.koin.android.viewmodel.ext.android.getViewModel
import sa.net.ibtikar.android.R
import sa.net.ibtikar.android.compose.ViewModelActivity
import sa.net.ibtikar.android.databinding.ActivityPersonDetailBinding
import sa.net.ibtikar.android.extension.checkIsMaterialVersion
import sa.net.ibtikar.android.models.entity.Person
import java.io.*
import java.net.MalformedURLException
import java.net.URL
import java.util.*


class PersonDetailActivity : ViewModelActivity() {

    private val binding: ActivityPersonDetailBinding by binding(R.layout.activity_person_detail)
    private var context: Context? =null


  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intentPerson = intent.getParcelableExtra(personId) as Person
        val profileURL = intent.getStringExtra(profileUrl)

        with(binding) {
            activity = this@PersonDetailActivity
            lifecycleOwner = this@PersonDetailActivity
            viewModel =
                    getViewModel(PersonDetailViewModel::class).apply {
                        postPersonId(intentPerson.id)
                    }
            person = intentPerson
        }

     context=this@PersonDetailActivity


        downloadBtn.setOnClickListener {
            Log.e("profileURL",""+profileURL)
            DownloadImage(profileURL)
        }
    }

    private fun DownloadImage(ImageUrl: String?) {
        if (ContextCompat.checkSelfPermission(this@PersonDetailActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this@PersonDetailActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@PersonDetailActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 123)
            ActivityCompat.requestPermissions(this@PersonDetailActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 123)
            showToast("Need Permission to access storage for Downloading Image")
        } else {
            showToast("Downloading Image...")
            //Asynctask to create a thread to downlaod image in the background
            DownloadsImage().execute(ImageUrl)
        }
    }

    internal class DownloadsImage : AsyncTask<String?, Void?, Void?>() {

        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)
        }

      override fun doInBackground(vararg params: String?): Void? {
            var url: URL? = null
            try {
                url = URL(params[0])
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
            var bm: Bitmap? = null
            try {
                bm = BitmapFactory.decodeStream(url!!.openConnection().getInputStream())
            } catch (e: IOException) {
                e.printStackTrace()
            }

            //Create Path to save Image
            val path: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES.toString() + "/Ibtikar")
          //Creates app specific folder
            if (!path.exists()) {
                path.mkdirs()
            }
            val imageFile = File(path, System.currentTimeMillis().toString() + ".png")
            var out: FileOutputStream? = null
            try {
                out = FileOutputStream(imageFile)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            try {
                bm!!.compress(Bitmap.CompressFormat.PNG, 100, out) // Compress Image
                out!!.flush()
                out.close()
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
            } catch (e: Exception) {
            }
            return null

        }
    }

    private fun showToast(msg: String?) {
        Toast.makeText(this@PersonDetailActivity, msg, Toast.LENGTH_SHORT).show()
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) onBackPressed()
        return false
    }

    companion object {
        const val personId = "person"
        const val profileUrl = "profile_url"
        private const val intent_requestCode = 1000

        fun startActivity(context: Context, person: Person, profileURL: String?, view: View) {
            if (context is Activity) {
                if (checkIsMaterialVersion()) {
                    val intent = Intent(context, PersonDetailActivity::class.java)
                    ViewCompat.getTransitionName(view)?.let {
                        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, view, it)
                        intent.putExtra(personId, person)
                        intent.putExtra(profileUrl, profileURL)
                        context.startActivityForResult(intent, intent_requestCode, options.toBundle())
                    }
                } else {
                    context.startActivity(Intent(context, PersonDetailActivity::class.java).putExtra(personId,person))
                }
            }
        }
    }
}
