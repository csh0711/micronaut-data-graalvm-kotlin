package utils

import org.skyscreamer.jsonassert.JSONCompare
import org.skyscreamer.jsonassert.JSONCompareMode.NON_EXTENSIBLE

fun String.compareJSON(expectedJson: String) = JSONCompare.compareJSON(this, expectedJson, NON_EXTENSIBLE).passed()