---
layout:     post
title:      "PTV-plugin WordPressiin"
date:       2016-11-21 13:00:00
author:     "Jere Lauha"
header-img: "img/main_banner.jpg"
---

Toteutimme mikkeli.fi projektin yhteydessä RESTful PTV -välipalvelimen (https://www.kunta-api.fi/2016/11/15/palvelinarkkitehtuuri-osa-1/), 
joka muuntaa PTV:stä tulevan datan REST-standardin mukaiseksi. 
Nykyinen PTV rajapinta ei tarjoa REST-standardin mukaista dataa ulos, joten jouduimme Mikkelin casessa
rakentamaan tämän "välipalvelimen" PTV:n ja Kunta APIn väliin. Tämä
RESTful PTV -välipalvelin välittää siis kaiken PTV:ssä olevan
informaation esim. Kunta APIn käyttöön.

RESTful PTV:tä hyödyntää myös jatkossa WordPressiin toteutettava
PTV-plugin (laajennus), joka mahdollistaa PTV:n sisällön tuomisen
WordPress sivustoon. Jatkossa plugin tulee hyödyntämään myös PTV:n
OUT-rajapintaa, jolloin PTV tietojen ylläpito onnistuu WordPressin
kautta. PTV -plugin toteutetaan joulukuun 2016 aikana ja se on
kunnille ilmainen.

Itse välipalvelin ja wp-laajennus ovat open source tuotteita, joten
kunnilla on mahdollisuus asentaa ne itselleen omaan käyttöön, mutta
helpommalla pääsee kun käyttää valmista ja testattua kokonaisuutta
API-avaimen avulla.

Ensisijaisesti tarjoamme kunnille mahdollisuutta ottaa käyttöön Kunta
API, joka on huomattavasti laajempi ja monipuolisempi kokonaisuus.
Kunta API sitoo PTV:n ja kunnan omat tietojärjestelmät samaan
rajapintaan ja jalostaa tiedot esim. www-sivuston, mobiiliapplikaation
jne. käyttöön.

Lisätietoja:

Jere Lauha, p. 044 794 5323 tai 044 290 9201
jere.lauha@otavanopisto.fi
