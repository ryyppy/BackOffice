show tables;

DROP TABLE (show tables);


CREATE TABLE projekt(
	projektID 		integer,
	name 			varchar(30),
	beschreibung	varchar(100),
	PRIMARY KEY (projektID)
);

CREATE TABLE kunde(
	kundeID 		integer,
	vorname 		varchar(50),
	nachname 		varchar(50),
	geburtsdatum 	date,
	PRIMARY KEY (kundeID)
);

CREATE TABLE angebot(
	angebotID 		integer, 
	summe 			double, 
	dauer 			integer,
	datum 			date,
	chance 			double,
	kundeID 		integer,
	projektID 		integer,
	PRIMARY KEY (angebotID),
	FOREIGN KEY (kundeID) REFERENCES kunde(kundeID) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (projektID) REFERENCES projekt(projekt) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE kontakt(
	kontaktID 		integer,
	firma    		varchar(50),
	name			varchar(50),
	telefon  		varchar(20),
	PRIMARY KEY (kontaktID)
);

CREATE TABLE rechnung(
	rechnungID 		integer,
	status 			varchar(20),
	datum 			date,
	PRIMARY KEY (rechnungID)
);

CREATE TABLE ausgangsrechnung(
	rechnungID integer,
	projektID integer,
	PRIMARY KEY (rechnungID, projektID)
);

CREATE TABLE eingangsrechnung(
	rechnungID integer,
	kontaktID integer,
	kundenID integer,
	PRIMARY KEY (rechnungID)
);


CREATE TABLE rechnungszeile(
	rechnungszeileID	integer,
	rechnungID			integer,
	kommentar			varchar(100),
	steuersatz			double,
	betrag				double,
	angebotID			integer,
	PRIMARY KEY (rechnungszeileID),
	FOREIGN KEY (rechnungID) REFERENCES rechnung(rechnungID) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (angebotID) REFERENCES angebot(angebotID) ON DELETE CASCADE ON UPDATE CASCADE
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
