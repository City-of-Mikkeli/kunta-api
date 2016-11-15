---
layout:     post
title:      "Palvelinarkkitehtuuri Osa 2 - Kunta API"
date:       2016-11-15 20:00:00
author:     "Antti Leppä"
header-img: "img/main_banner.jpg"
---

Meille on tullut kyselyitä Kunta API:n palvelinarkkitehtuuriin liitten, joten päätimme kehitystiimin puolelta vastata kyselyihin muutaman blogipostauksen muodossa. Tämä on toinen postaus, joka käsittelee itse Kunta API:a. Voit lukea ensimmäisen RESTful PTV:tä käsittelevän osan täältä.

Kunta API on palvelu, joka yhdistää erilaisten kunnallisten järjestelmien datoja yhden rajapinnan alle. Rajapinta mahdollistaa näiden tietojen esittämisen ja käsittelemisen toisissa järjestelmissä sekä tietojen yhdistämisen ja rikastamisen.

Yleisin ja esimmäinen käyttötarkoitus datalle on esittää se verkkosivulla mutta käyttötarkoitukset eivät suikaan rajoitus siihen. Tämä blogikirjoitus ei kuitenkaan käsittele mahdollisia käyttötarkoituksia vaan sen sijaan palvelinarkkitehtuuria.

<img src="/img/kunta-api.svg" style="border: 1px solid #000; margin-top: 20px; padding: 10px; width: 100%;"/> 
<em style="font-size: 80%;"><b>Kunta API palvelinarkkitehtuurikuva.</b></em>

Kunta API:n ytimen muodostaa Wildfly klusteri. Klusteri koostuu n-määrästä serveri koneita (kuvassa Worker) sekä klusterin hallintapalvelusta (kuvassa Master) sekä edustapalvelimesta (kuvassa Nginx).

Kunta API:a voidaan kuvailla eräänlaisena data aggregaattorina. Sen tähtävänä on hakea tietoja erillisiltä järjestelmiltä ja normalisoida ne yhteiseen muotoon.

Kunta API:n yksi tehtävistä on säilöä ulkoisten järjestelmien dataa välimuistiin, jotta se olisi mahdollisimman nopeasti saatavilla käyttäjille. Tämän lisäksi se ylläpitää saamiaan tietoja siltä varalta, että ulkoiset järjestelmät eivät jostakin syystä pysty juuri sillä hetkellä niitä tarjoamaan.

Tähän Kunta API hyödyntää [Infinispan](http://infinispan.org/)-nimistä Datagridiä. [Infinispan](http://infinispan.org/) on hajautettu nimi/arvo tietovaranto, joka kykenee käsittelemään suuria tietomääriä valtavalla nopeudella. Kunta API:ssa [Infinispan](http://infinispan.org/) on hajautettu Worker -koneille, joten klusterin kasvaessa myös tietovarannon tehot ja koko kasvavat.

Kunta API myös tarjoaa mahdollisuuden hakea tietoja siihen liitetyistä tietolähteistä. Tietojen indexointiin ja hakemiseen Kunta API käyttää erillistä Elastic Search -klusteria. Elastic Search on erityisesti suurien tietomäärien indexointiin ja hakemiseen erikoistunut palvelu.

Viimeisenä virallisena osana Kunta API:n ydintä toimii edustapalvelin Nginx. Palvelimen tehtävänä on ottaa vastaan asiakasohjelmilta tulevat kutsut, toimia kuormantasaajana (Load balancer), salata kutsut https-salauksella sekä pakata lähetettävät tiedot.

Ytimen tilaa valvoo erillinen Cluster Controller. Sovelluksen tehtävänä on monitoroida Worker-koneiden tilaa ja tiputtaa hitaat tai vikaantuneet koneet pois klusterista sekä varmistaa, että esimerkiksi päivitystilanteessa asiakkaat tulevat aina palvelluksi.

Yksi Kunta API:n ytimestä erillinen mutta selkeästi palvelukokoneisuuteen kuuluva osa on hallintapalvelu. Hallintapalvelun tehtävänä on tarjota sisällöntuottajille sekä muille hallinnoijille käyttöliittymä tietojen hallintaan sekä esimerkiksi PTV-datan rikastamiseen.

Hallintapalvelu pyörii hyvin yleisesti käytössä olevan sisällönhallintajärjestelmän Wordpressin päällä. Teknisesti katsoen hallintapalvelu on integraatio muiden joukossa mutta sen luonteesta johtuen integraatio on hyvin syvä. Palvelu on omalla palvelimellaan ja säilöö omat datansa paikalliseen MySQL-tietokantaan.

Toinen ytimestä erillinen mutta myöskin selkeästi palvelukokonaisuuteen kuuluva osa on www-esityskerros tai tuttavallisemmin sanottuna verkkosivu. Kunta API:n luonteen vuoksi verkkosivun voisi toteuttaa haluamallaan tavalla mutta esimerkiksi www.mikkeli.fi -projektin osalta kehitystiimi päätyi tekemään esityskerroksen NodeJs -palveluna. Esityskerroksen tehtävänä on nimensä mukaisesti esittää Kunta API:n tarjoamaa tietoa verkkosivuna.

Teknisesti katsoen esityskerros on siis vain Kunta API:n asiakasohjelma ja se voisi aivan yhtä hyvin olla esimerkiksi mobiiliapplikaatio tai vaikkapa bussin valotaulu mikäli siinä haluttaisiin esittää jotain Kunta API:n dataa.

Esityskerros sisältää myös Nginx- edustapalvelimen, jonka tehtävä on vastaava kun Kunta API:ssa eli vastaanottaa kutsut, tasata kuormaa, salata sekä pakata liikenne. 

**Teknisiä tietoja Kunta API:sta:**

Kunta API:n testiympäristö pöyrii viidelllä koneella (1 x master, 2 x worker, 1 x hallintapalvelin sekä 1 x esityskerrospalvelin), joista jokaisessa on:

*Ubuntu Xenial (16.04 LTS) käyttöjärjestelmä, 2 x x86 -prosessoria sekä 2 Gt muistia sekä 50 Gt SSD -kovalevy.*

Erillinen Elastic Search klusteri toimii testiympäristössä kahdella koneella, jossa on kummassakin:

*Ubuntu Xenial (16.04 LTS) käyttöjärjestelmä,  6 x x86 -prosessoria, 8 Gt muistia sekä  200 Gt SSD -kovalely.*

Yhteensä testiympäristössä on siis *22 prosessoria*, *26 Gt muistia* sekä *650 Gt kovalevytilaa* ja kun tähän lisätään vielä RESTful PTV:n palvelinten kapasiteetit päädytään *28 prosessoriin*, *32 Gt muistia* sekä *800 Gt kovalevytilaa*.

Yllättävän paljon siis tarvitaan testiympäristön pyörittämisen ja toki hommaan tarvitaan erillinen staging -ympäristö testaamista varten sekä itse tuotantoympäristö, jossa tehoja pitääkin sitten olla huomattavasti enemmän. 

---
Kunta API on syntynyt osana [Mikkelin Lupaus 2016](http://www.mikkeli.fi/lupaus) -hanketta.
