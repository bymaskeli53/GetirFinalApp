package com.example.getirfinalapp

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

object PriceFormatter {
    private val turkishNumberFormat = DecimalFormat("#,##0.00 ₺").apply {
        decimalFormatSymbols = DecimalFormatSymbols(Locale("tr", "TR")).apply {
            groupingSeparator = '.'  // Binlik ayracı için nokta
            decimalSeparator = ','   // Ondalık ayracı için virgül
        }
    }

    fun formatPrice(price: Double): String {
        return turkishNumberFormat.format(price)
    }

    // Alternatif format için (gerekirse)
    fun formatPriceAlt(price: Double): String {
        val formatted = turkishNumberFormat.format(price)
        return if (price >= 1000) {
            // Binlik sayılarda formatlama
            val parts = formatted.split(",")
            "${parts[0]},${parts[1]}"
        } else {
            formatted
        }
    }

    // String'den Double'a çevirme (input handling için)
    fun parsePrice(priceStr: String): Double {
        return try {
            priceStr.replace("₺", "")
                .replace(".", "")
                .replace(",", ".")
                .trim()
                .toDouble()
        } catch (e: Exception) {
            0.0
        }
    }
}