package com.example.choplaygroundkotlin.util

object Constants {

    const val TAG = "AppDebug" // Tag for logs
    const val DEBUG = true // enable logging

    const val FOLDER_ORDER_ASC: String = ""
    const val FOLDER_ORDER_DESC: String = "-"
    const val FOLDER_FILTER_TITLE = "folder_name"
    const val FOLDER_FILTER_DATE_CREATED = "created_at"

    const val ORDER_BY_ASC_DATE_UPDATED = FOLDER_ORDER_ASC + FOLDER_FILTER_DATE_CREATED
    const val ORDER_BY_DESC_DATE_UPDATED = FOLDER_ORDER_DESC + FOLDER_FILTER_DATE_CREATED
    const val ORDER_BY_ASC_TITLE = FOLDER_ORDER_ASC + FOLDER_FILTER_TITLE
    const val ORDER_BY_DESC_TITLE = FOLDER_ORDER_DESC + FOLDER_FILTER_TITLE

    const val FOLDER_PAGINATION_PAGE_SIZE = 30
}