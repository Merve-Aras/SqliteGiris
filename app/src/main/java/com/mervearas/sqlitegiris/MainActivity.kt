package com.mervearas.sqlitegiris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //SQL KOMUTLARI TRY CATCH İÇİNE YAZILIR ÇÜNKÜ HATA İLE KARŞILAŞINCA HATA VERİP APP ÇÖKMESİ ENGELLENİR.
        try {
            //**********Musicians İSİMLİ DATABASE OLUŞTURULDU**********
            val myDatabase = this.openOrCreateDatabase("Musicians", MODE_PRIVATE,null) //yeni database oluşturma kodu. ilk olarak database ismi yazıldı, sonra mode yazıldı yani bu database'e nerelerden ulaşılabilirlik için. daha sonra factory kısmına null yazıldı çünkü şuan cursor oluşturulmadı.

            //**********musicians İSİMLİ 3 SÜTUNLU (id, name,age) TABLO OLUŞTURULDU**********
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS musicians (id INTEGER PRIMARY KEY ,name VARCHAR, age INT)") //Yukarıda oluşturduğumuz database üzerinde bir kod çalıştıracaksak .execSQL kullanılır ve içine SQL kodu yazılır.

            //**********TABLOYA VERİ EKLENDİ**********
            //myDatabase.execSQL("INSERT INTO musicians (name, age) VALUES ('James',50)")  //name ve age isimli sütunlara veri eklendi
            //myDatabase.execSQL("INSERT INTO musicians (name, age) VALUES ('Lars',60)")
            //myDatabase.execSQL("INSERT INTO musicians (name, age) VALUES ('Kirk',55)")

            //**********TABLODAKİ VERİLERİN GÜNCELLEMESİ YAPILDI**********
            //myDatabase.execSQL("UPDATE musicians SET age = 61 WHERE name = 'Lars'") //ismi Lars olanın yaşını 61 olarak günceller
            //myDatabase.execSQL("UPDATE musicians SET name = 'Kirk Hammett' WHERE id=3")  //3.id'deki name sütununda bulunan veriyi Kirk Hammett olarak günceller

            //**********TABLODAN VERİ SİLİNDİ**********
            myDatabase.execSQL("DELETE FROM musicians WHERE name='Lars'") //İsmi Lars olan veriyi tablodan siler

            //**********TABLODAN VERİ ÇEKME İŞLEMİ YAPILDI (Tüm veri ve Filtreli veri)**********
            val cursor = myDatabase.rawQuery("SELECT * FROM musicians",null) //musicians tablosunun tüm içeriğini çektik. tüm içeriği almak için * işareti koyuldu.
            //val cursor = myDatabase.rawQuery("SELECT * FROM musicians WHERE id = 3",null) // FİLTRELEME İŞLEMİ YAPILDI. bunu name age ya da id'ye göre yapabiliriz.
            //val cursor = myDatabase.rawQuery("SELECT * FROM musicians WHERE name LIKE '%s'",null) //name sütunundaki verilerden sonu s ile bitenleri getirme komutu
            //val cursor = myDatabase.rawQuery("SELECT * FROM musicians WHERE name LIKE 'K%'",null)  //name sütunundaki verilerden K ile başlayanları getirme komutu

            //**********TABLODAKİ SÜTUNLARIN KAÇINCI İNDEXTE OLDUKLARI TESPİT EDİLDİ**********
            val nameIndex = cursor.getColumnIndex("name") //tablodaki name sütununun kaçıncı indexten olduğunu öğrenmek için bu kod yazıldı
            val ageIndex = cursor.getColumnIndex("age") //tablodaki age sütununun kaçıncı indexten olduğunu öğrenmek için bu kod yazıldı
            val idIndex = cursor.getColumnIndex("id") //tablodaki id sütununun kaçıncı indexten olduğunu öğrenmek için bu kod yazıldı

            //**********EKRANA VERİLER YAZDIRILDI**********
            while (cursor.moveToNext()){  //cursor tablo üzerinde gezebildiği kadar gezsin (cursor.moveToNext)
                println ("Name: " + cursor.getString(nameIndex)) //name sütununun index'i nameIndex değişkenine atanmıştı. bu index numarasının içindeki veriyi alır.
                println("Age: " + cursor.getInt(ageIndex))  //age sütununun index'i ageIndex değişkenine atanmıştı. bu index numarasının içindeki veriyi alır.
                println("id: " + cursor.getInt(idIndex))
            }
        } catch (e:Exception){
            e.printStackTrace()
        }
    }
}