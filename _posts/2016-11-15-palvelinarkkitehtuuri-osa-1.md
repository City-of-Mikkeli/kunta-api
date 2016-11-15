---
layout:     post
title:      "Palvelinarkkitehtuuri Osa 1 - RESTful PTV"
date:       2016-11-15 12:00:00
author:     "Antti Leppä"
header-img: "img/main_banner.jpg"
---

Meille on tullut kyselyitä Kunta API:n palvelinarkkitehtuuriin liitten, joten päätimme kehitstytiimin puolelta vastata kyselyihin muutaman blogipostauksen muodossa.

Kunta API on luonteeltaan dataa useista eri lähteistä koostava ja rikastava palvelu. Tällä hetkellä palvelun merkittävin datalähde on [PTV](https://esuomi.fi/palveluntarjoajille/palvelutietovaranto/), joten tämän datan hienostunut käsittely on erityisen tärkeätä projektin onnistumiselle.

Hyvin alkuvaiheessa projektia kuitenkin havaittiin, että PTV:n toteutuksessa esiintyy vakavia laadullisia sekä arkkitehtuurillisia ongelmia. Tuotuamme ongelmat toteuttajien tietoon ja saatuamme vastauksen että näitä puutteita ei aikataulun vuoksi pystytä korjaamaan, päädyimme siihen ratkaisuun, että päästäksemme onnistuneeseen lopputulokseen on meidän tehtävä PTV:n ja Kunta API:n väliin erillinen palvelu.

Syntyneen palvelun nimeksi tuli RESTful PTV.

Palvelu on hyvin olennainen osa Kunta API:n arkkitehtuuria, joten ensimmäisessä postauksesa kuvaamme sen palvelinarkkitehtuuria.

<img src="img/restful.svg"/> *RESTful PTV palvelinarkkitehtuurikuva.*

RESTful PTV toimii välittäjäpalvelimena PTV:n sekä asiakasohjelmien välillä. Palvelun yleiseen toimintalogiikkaan on syytä perehtyä toisessa blogipostauksessa mutta pääpiirteittäin sen tehtävänä on muuttaa PTV:n data helpommin käsiteltäväksi sekä taata palvelun jatkuva saatavuus.

Jatkuva saatavuus onkin RESTful PTV:n yksi tärkeimmistä ominaisuuksista, sillä mikäli palvelu ei ole saatavissa ei myöskään asiakasohjelmilla (esim. kuntien sivuilla) ole PTV:n tarjoamia tietoja saatavilla.

Jatkuva saatavuus on varmistettu klusteroimalla sovellus. Käytännössä tämä tarkoittaa sitä, että sovellus pyörii yht'aikaisesti useilla palvelimilla (kuvassa Worker 1-n) ja kutsuja ohjataan erillisestä edustapalvelimesta (Master + Nginx) aina kaikkein vähiten kuormittuneelle palvelimelle.

Edustapalvelimella päivystää myös erillinen klusterin valvontasovellus (kuvassa Cluster Controller), joka tarkkailee klusterin tilaa ja poistaa vikautuneet serverit pois klusterista.

Järjestelmän tilaa monitoroidaan toki useilla monitotointijärjestelmillä sekä loppukädessä myös ihmisvoimin. 

Strategiaa kutsutaan yleisesti termillä High Availability (HA) ja termi viittaa siihen, että palvelu on aina käyttäjien saatavilla. Strategia mahdollistaa myös palvelun skaalaamisen suuremmalle käyttäjämäärälle lisäämällä siihen lisää serveritä eli Worker -koneita.

Yksi merkittävä osa palvelua on Infinispan -niminen datagridi. Infinispan säilöö PTV:stä haettua dataa ja mahdollistaa tiedon toimittamisen hyvin nopeasti asiakasohjelmille. Jos tarkkoja ollaan Infinispan säilöö dataansa vain hetkeksi aikaa ja itse varsinaisen säilömisen hoitaa MySQL-tietokanta.

Tietokanta on usein HA-järjestelmien pullonkaula ja täten se on usein ko. järjestelmissä myös klusteroitu mutta RESTful PTV:n tapauksessa tietokanta toimii enemmänkin datan passivointitarkoituksessa, joten kannan nopeudella ei ole paljonkaan vaikutusta järjestelmän nopeuteen.

Viimeisenä komponenttina palvelussa  on webpalvelin Nginx. Nginxin tehtävänä on ottaa vastaan varsinaiset kutsut ja välittää ne vähiten kuormittuneille palvelimille (Load balancer). Tämän lisäksi Nginx hoitaa https-salauksen, pakaa kutsut sekä hoitelee yleisesti kaiken liikenteen ulkoverkon ja palvelinten välillä.

**Lopuksi vielä hieman teknisiä yksityiskohtia:**

Testiympäristö pöyrii kolmella koneella (1 x master ja 2 x worker), joista jokaisessa on

*Ubuntu Xenial (16.04 LTS) käyttöjärjestelmä, 2 x x86 -prosessoria sekä 2 Gt muistia sekä 50 Gt SSD -kovalevy.*

 

Itse klusteri on rakennettu Wildfly 10.1.0.Final JavaEE sovelluspalvelinten päälle ja Datagridinä toimii Infinispan 8.2, edustapalvelimena Nginx 1.10.1, tietokantapalvelimena MySQL 5.7.16.

 

Tuotantoympäristössä koneiden tehot ja määrät tulevat toki nousemaan mutta testitarkoitukseen tehot riittävät mainiosti.

---
Kunta API on syntynyt osana Mikkelin Lupaus 2016 -hanketta.
