package com.example.getirfinalapp.util

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun BigDecimal.formatPrice(): String {
    val symbols = DecimalFormatSymbols(Locale.FRANCE).apply {
        groupingSeparator = '.'
        decimalSeparator = ','
    }
    val formatter = DecimalFormat("₺#,##0.00", symbols)
    return formatter.format(this)
}
