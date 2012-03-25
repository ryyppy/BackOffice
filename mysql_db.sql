show tables;

DROP TABLE (show tables);

CREATE TABLE angebot(
	angebotID integer, 
	summe double, 
	dauer integer,
	datum date,
	chance double,
	PRIMARY KEY (angebotID)
);

CREATE TABLE projekt(
	projektID 		integer,
	name 					varchar(30),
	beschreibung	varchar(100),
	PRIMARY KEY (projektID)
);

CREATE TABLE rechnungszeile(
	rechnungszeileID integer,
	rechnungID 			 integer,
	ust 						 double,
	summe						 double,
	PRIMARY KEY (rechnungszeileID)
);

CREATE TABLE ausgangsrechnung(
	rechnungID integer,
	projektID integer,
	PRIMARY KEY (rechnungID, projektID)
);

CREATE TABLE kunde(
	kontaktID integer,
	vorname varchar(50),
	nachname varchar(50),
	PRIMARY KEY (kontaktID)
);

CREATE TABLE kontakt(
	kontaktID integer,
	firma    varchar(50),
	telefon  varchar(20),
	email 	 varchar(30),
	fax 		 varchar(20),
	strasse varchar(50),
	plz 	  varchar(10),
	ort     varchar(50),
	PRIMARY KEY (kontaktID)
);

CREATE TABLE eingangsrechnung(
	rechnungID integer,
	kontaktID integer,
	kundenID integer,
	PRIMARY KEY (rechnungID)
);

CREATE TABLE rechnung(
	rechnungID integer,
	kontaktID integer,
	kundenID integer,
	PRIMARY KEY (rechnungID)
);

CREATE TABLE buchungszeile(
	buchungszeileID integer,
	bankkontoID integer,
	kategorieID integer,
	PRIMARY KEY (buchungszeileID)
);

CREATE TABLE kategorie(
	kategorieID integer,
	kbz varchar(20),
	bezeichnung varchar(100),
	PRIMARY KEY (kategorieID)
);

CREATE TABLE bankkonto(
	bankkontoID integer,
	kontonummer varchar(50),
	blz varchar(10),
	PRIMARY KEY (bankkontoID)
);



--ALTER TABLE ORDERS ADD FOREIGN KEY (customer_sid) REFERENCES CUSTOMER(SID);
