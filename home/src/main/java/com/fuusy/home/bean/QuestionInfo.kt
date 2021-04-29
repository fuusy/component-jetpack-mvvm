package com.fuusy.home.bean

data class QuestionInfo(var curPage: Int, var data: List<QuestionList>, var pageCount: Int, var size: Int, var total: Int)

data class QuestionList(var author: String, var desc: String, var niceDate: String, var title: String)

