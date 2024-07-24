package com.example.openinappandroidassesment.models

data class Data(
    val favourite_links: List<Link>,
    //val overall_url_chart: Map<String,Int>,
    val recent_links: List<Link>,
    val top_links: List<Link>
)