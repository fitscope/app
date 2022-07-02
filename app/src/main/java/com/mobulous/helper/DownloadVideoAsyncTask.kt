package com.mobulous.helper

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

open class DownloadVideoAsyncTask(private val mContext: Context) : AsyncTask<String?, Int?, String?>() {
    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg sUrl: String?): String? {
        var input: InputStream? = null
        var output: OutputStream? = null
        var connection: HttpURLConnection? = null
        try {
            val url = URL(sUrl[0])
            connection = url.openConnection() as HttpURLConnection
            connection.connect()

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection!!.responseCode != HttpURLConnection.HTTP_OK) {
                return ("Server returned HTTP " + connection.responseCode
                        + " " + connection.responseMessage)
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            val fileLength = connection.contentLength

            // download the file
            input = connection.inputStream

//            output = new FileOutputStream("/data/data/com.example.vadym.test1/textfile.txt");
            output = FileOutputStream(mContext.filesDir.toString() + "/file.mp4")
            Log.d("paaaaaaaa", mContext.filesDir.toString() + "/file.mp4")
            val data = ByteArray(4096)
            var total: Long = 0
            var count: Int
            while (input.read(data).also { count = it } != -1) {
                // allow canceling with back button
                if (isCancelled) {
                    input.close()
                    return null
                }
                total += count.toLong()
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((total * 100 / fileLength).toInt())
                output.write(data, 0, count)
            }
        } catch (e: Exception) {
            return e.toString()
        } finally {
            try {
                output?.close()
                input?.close()
            } catch (ignored: IOException) {
            }
            connection?.disconnect()
        }
        return null
    }


    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        Log.d("ptg", "onProgressUpdate: " + values[0])
    }


    override fun onPostExecute(s: String?) {
        super.onPostExecute(s)
    }
}