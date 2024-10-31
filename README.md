Käyttöliittymä: Käyttäjä voi syöttää työntekijän etunimen, sukunimen ja sähköpostiosoitteen. Käyttöliittymässä on myös kielivalikko, josta käyttäjä voi valita haluamansa kielen.

Kielituki: Sovellus päivittää käyttöliittymän tekstit valitun kielen mukaisesti. Tämä toteutetaan updateLabels-metodilla, joka päivittää tekstikenttien ja painikkeiden tekstit valitun kielen mukaisesti.  

Tietojen tallennus: Käyttäjä voi tallentaa syöttämänsä tiedot tietokantaan. Tiedot tallennetaan kielen mukaan eri tauluihin. Tallennus tapahtuu saveEmployee-metodilla, joka kutsuu DatabaseHelper-luokan insertEmployee-metodia.

Tietokantayhteys: Sovellus käyttää MariaDB-tietokantaa tietojen tallentamiseen. Tietokantayhteys ja tietojen tallennus on eriytetty DatabaseHelper-luokkaan, joka sisältää metodin insertEmployee tietojen tallentamiseksi tietokantaan.

Pääluokka EmployeeManagementApp sisältää käyttöliittymän ja toiminnallisuudet, kun taas DatabaseHelper-luokka huolehtii tietokantatoiminnoista.
