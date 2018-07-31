package app.hb.firestoretest

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseFirestore = FirebaseFirestore.getInstance()


        tvSubmit.setOnClickListener { view ->
            if (ValidateInput()) saveDataToFirestore()
        }
    }

    private fun saveDataToFirestore() {
        val name = etName.text.toString()
        val city = etCity.text.toString()
        val adress = etAdress.text.toString()
        val stars = etStars.text.toString()

        val hotelItem = HashMap<String, Any>()
        hotelItem.put(applicationContext.getString(R.string.name), name)
        hotelItem.put(applicationContext.getString(R.string.city), city)
        hotelItem.put(applicationContext.getString(R.string.adress), adress)
        hotelItem.put(applicationContext.getString(R.string.stars), stars)

        firebaseFirestore.collection(applicationContext.getString(R.string.hotels)).add(hotelItem).addOnSuccessListener { DocumentReference ->

            showSnackBar(applicationContext.getString(R.string.hotel_saved))

        }.addOnFailureListener {

            showSnackBar(applicationContext.getString(R.string.error_saving))
        }

    }


    fun showSnackBar(message: String) {
        Snackbar.make(parentView, message, Snackbar.LENGTH_LONG).show()
    }

    fun ValidateInput(): Boolean {
        var valid = true

        if (etName.text.toString().isEmpty()) {
            valid = false
            etName.setError(applicationContext.getString(R.string.add_name))
        }

        if (etCity.text.toString().isEmpty()) {
            valid = false
            etCity.setError(applicationContext.getString(R.string.add_city))
        }

        if (etAdress.text.toString().isEmpty()) {
            valid = false
            etAdress.setError(applicationContext.getString(R.string.add_address))
        }

        if (etStars.text.toString().isEmpty()) {
            valid = false
            etStars.setError(applicationContext.getString(R.string.add_stars))
        }

        return valid
    }

}
