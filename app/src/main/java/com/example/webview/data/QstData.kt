package com.example.webview.data

import com.example.webview.Questions

class QstData {
    private var list: ArrayList<Questions> = arrayListOf()

    fun getList(): ArrayList<Questions>{

        list.add(Questions(" Команда какой страны стала чемпионом Европы по футболу в 2000 г.?",
            arrayListOf("Франция","Германия","Испания","Россия"),"Франция"))
        list.add(Questions(" Как называется обувь для футболиста?",
            arrayListOf("Кеды","туфли","бутсы","шлёпки"),"бутсы"))
        list.add(Questions(" Как итальянцы называют особо рьяных футбольных болельщиков?",
            arrayListOf("Тиффози","Хамло","Быдло","Макрози"),"Тиффози"))
        list.add(Questions(" Назовите город - столицу Олимпиады-2004",
            arrayListOf("Москва","Афины","Берлин","Лондон"),"Афины"))
        list.add(Questions(" Что означает футбольный термин аут ?",
            arrayListOf("Мяч в центре поля","Мяч в воздухе","Мяч в воротах","Мяч вне поля "),"Мяч вне поля "))
        list.add(Questions(" Собака какой группы пород была талисманом Олимпиады 1972 г. в Мюнхене?",
            arrayListOf("Чихухуа","Овчарка","Болонка","Такса"),"Такса"))
        list.add(Questions(" Финалист Суперкубка Европы ’91?- Doom",
            arrayListOf("Зенит","Манчестер Юнайтед","Барселона","Бавария"),"Манчестер Юнайтед"))
        return list
    }
}