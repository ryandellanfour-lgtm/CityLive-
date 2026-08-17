package com.citylive.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.*
import android.graphics.drawable.GradientDrawable

class MainActivity : Activity() {
    private lateinit var root: LinearLayout
    private lateinit var citySpinner: Spinner
    private lateinit var feed: LinearLayout
    private val cities = listOf("Toronto", "Mississauga", "Brampton", "Vaughan", "Markham", "Hamilton", "Ottawa", "London", "Windsor")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showCityPicker()
    }

    private fun showCityPicker() {
        root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 48, 32, 32)
            setBackgroundColor(Color.WHITE)
        }

        val title = TextView(this).apply {
            text = "City Live"
            textSize = 32f
            setTextColor(Color.rgb(21,101,192))
            gravity = Gravity.CENTER
        }
        val subtitle = TextView(this).apply {
            text = "Choose your city to join the live local conversation."
            textSize = 17f
            setPadding(0, 16, 0, 32)
            gravity = Gravity.CENTER
        }

        citySpinner = Spinner(this)
        citySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cities)

        val enter = Button(this).apply {
            text = "Enter City Live"
            setOnClickListener { showFeed(citySpinner.selectedItem.toString()) }
        }

        root.addView(title)
        root.addView(subtitle)
        root.addView(citySpinner, LinearLayout.LayoutParams(-1, 60))
        root.addView(enter, LinearLayout.LayoutParams(-1, 60).apply { topMargin = 32 })
        setContentView(root)
    }

    private fun showFeed(city: String) {
        root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.rgb(245,247,250))
        }

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(24, 20, 24, 20)
            setBackgroundColor(Color.rgb(21,101,192))
        }
        val title = TextView(this).apply {
            text = "🔴 $city Live"
            textSize = 24f
            setTextColor(Color.WHITE)
        }
        val change = Button(this).apply {
            text = "Change"
            setOnClickListener { showCityPicker() }
        }
        header.addView(title, LinearLayout.LayoutParams(0, -2, 1f))
        header.addView(change)

        feed = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 100)
        }

        val scroll = ScrollView(this)
        scroll.addView(feed)

        val composer = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(12, 8, 12, 8)
            setBackgroundColor(Color.WHITE)
        }
        val input = EditText(this).apply {
            hint = "Write something..."
            maxLines = 3
        }
        val post = Button(this).apply {
            text = "Post"
            setOnClickListener {
                val text = input.text.toString().trim()
                if (text.isNotEmpty()) {
                    addComment("You", text)
                    input.text.clear()
                }
            }
        }
        composer.addView(input, LinearLayout.LayoutParams(0, -2, 1f))
        composer.addView(post)

        root.addView(header)
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        root.addView(composer)
        setContentView(root)

        addComment("City Live", "Welcome to $city! This is the beginning of your local live feed.")
        addComment("Example User", "What's happening around $city today?")
    }

    private fun addComment(user: String, text: String) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 14, 20, 14)
            background = GradientDrawable().apply {
                setColor(Color.WHITE)
                cornerRadius = 18f
            }
        }
        val name = TextView(this).apply {
            this.text = user
            textSize = 15f
            setTextColor(Color.rgb(21,101,192))
        }
        val message = TextView(this).apply {
            this.text = text
            textSize = 17f
            setTextColor(Color.DKGRAY)
            setPadding(0, 6, 0, 0)
        }
        card.addView(name)
        card.addView(message)
        feed.addView(card, 0, LinearLayout.LayoutParams(-1, -2).apply { bottomMargin = 12 })
    }
}
