package ir.danial.choopan.tvonlinepersianchannels

import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.telecom.Conference
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ir.danial.choopan.tvonlinepersianchannels.adapters.Adapter_recyclerView_channels
import ir.danial.choopan.tvonlinepersianchannels.model.TVchannle
import ir.danial.choopan.tvonlinepersianchannels.model.db_adapters.Adapter_tbl_tvChannels
import kotlinx.android.synthetic.main.activity_home.*
import kotlin.math.log

class HomeActivity : AppCompatActivity() {
    var video_url = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val adapter_tbl_tvChannels = Adapter_tbl_tvChannels(this@HomeActivity)
        Log.i("url",video_url)
        if (video_url == "") {
            lbl_choise_one.visibility = View.VISIBLE
            videoView_tv.pause()
        } else {
            videoView_tv.setVideoURI(Uri.parse(video_url))
            progress_tv.visibility = View.VISIBLE
            lbl_choise_one.visibility = View.GONE
            videoView_tv.setOnPreparedListener {
                progress_tv.visibility = View.GONE
            }
            videoView_tv.start()
        }
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            videoView_tv.setVideoURI(Uri.parse(video_url))
            progress_tv.visibility = View.VISIBLE
            lbl_choise_one.visibility = View.GONE
            videoView_tv.setOnPreparedListener {
                progress_tv.visibility = View.GONE
                videoView_tv.start()
            }
        } else {
            recyclerView_channles.layoutManager = LinearLayoutManager(this@HomeActivity)
            recyclerView_channles.adapter =
                Adapter_recyclerView_channels(adapter_tbl_tvChannels.get_channels()) {
                    video_url = it.stream_url
                    videoView_tv.setVideoURI(Uri.parse(video_url))
                    progress_tv.visibility = View.VISIBLE
                    lbl_choise_one.visibility = View.GONE
                    videoView_tv.setOnPreparedListener {
                        progress_tv.visibility = View.GONE
                    }
                    videoView_tv.start()
                }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString("uri", video_url)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        video_url = savedInstanceState.getString("uri").toString()
    }
}
