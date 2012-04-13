DROP DATABASE IF EXISTS swe;
CREATE DATABASE swe;
USE swe;

DROP TABLE projekt CASCADE;
CREATE TABLE projekt(
	projektID 		integer auto_increment,
	name 			varchar(30),
	beschreibung	varchar(100),
	PRIMARY KEY (projektID)
);

INSERT INTO projekt(name, beschreibung) VALUES('bitstrips', 'cool');
INSERT INTO projekt(name, beschreibung) VALUES('wow', 'cool');


DROP TABLE kunde CASCADE;
CREATE TABLE kunde(
	kundeID 		integer auto_increment,
	vorname 		varchar(50),
	nachname 		varchar(50),
	geburtsdatum 	date,
	PRIMARY KEY (kundeID)
);

INSERT INTO kunde(vorname, nachname, geburtsdatum) VALUES ('armin', 'mehr', current_timestamp);
INSERT INTO kunde(vorname, nachname, geburtsdatum) VALUES ('patrick', 'stapfer', current_timestamp);


DROP TABLE angebot CASCADE;
CREATE TABLE angebot(
	angebotID 		integer auto_increment, 
	summe 			double, 
	dauer 			double,
	datum 			date,
	chance 			double,
	kundeID 		integer,
	projektID 		integer,
	PRIMARY KEY (angebotID),
	FOREIGN KEY (kundeID) REFERENCES kunde(kundeID) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (projektID) REFERENCES projekt(projektID) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO angebot(summe, dauer, datum, chance, kundeID, projektID) VALUES (12,  12, current_timestamp, 12, 1, 1);
INSERT INTO angebot(summe, dauer, datum, chance, kundeID, projektID) VALUES (12,  12, current_timestamp, 12, 1, 2);
INSERT INTO angebot(summe, dauer, datum, chance, kundeID, projektID) VALUES (12,  12, current_timestamp, 12, 2, 1);
INSERT INTO angebot(summe, dauer, datum, chance, kundeID, projektID) VALUES (12,  12, current_timestamp, 12, 2, 2);


CREATE TABLE kontakt(
	kontaktID 		integer auto_increment,
	firma    		varchar(50),
	name			varchar(50),
	telefon  		varchar(20),
	PRIMARY KEY (kontaktID)
);

INSERT INTO kontakt(firma, name, telefon) VALUES ('Niedermeyer', 'Max Mustermann', '01234567');
INSERT INTO kontakt(firma, name, telefon) VALUES ('Ditech', 'Mr Ansprechpartner', '01234567');


DROP TABLE rechnung CASCADE;
CREATE TABLE rechnung(
	rechnungID 		integer auto_increment,
	status 			varchar(20),
	datum 			date,
	PRIMARY KEY (rechnungID)
);

DROP TABLE ausgangsrechnung CASCADE;
CREATE TABLE ausgangsrechnung (
	kundeID 		integer,
	rechnungID		integer,
	FOREIGN KEY (rechnungID) REFERENCES rechnung(rechnungID) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO rechnung (rechnungID, status, datum) VALUES (1, 'offen', current_timestamp);
INSERT INTO rechnung (rechnungID, status, datum) VALUES (2, 'offen', current_timestamp);
INSERT INTO ausgangsrechnung (kundeID, rechnungID) VALUES (1, 1);
INSERT INTO ausgangsrechnung (kundeID, rechnungID) VALUES (2, 2);


DROP TABLE eingangsrechnung CASCADE;
CREATE TABLE eingangsrechnung(
	kontaktID 		integer,
	rechnungID		integer,
	FOREIGN KEY (rechnungID) REFERENCES rechnung(rechnungID) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO rechnung (rechnungID, status, datum) VALUES (3, 'offen', current_timestamp);
INSERT INTO rechnung (rechnungID, status, datum) VALUES (4, 'offen', current_timestamp);
INSERT INTO eingangsrechnung (kontaktID, rechnungID) VALUES (1, 3);
INSERT INTO eingangsrechnung (kontaktID, rechnungID) VALUES (2, 4);


DROP TABLE rechnungszeile CASCADE;
CREATE TABLE rechnungszeile(
	rechnungszeileID	integer auto_increment,
	rechnungID			integer,
	kommentar			varchar(100),
	steuersatz			double,
	betrag				double,
	angebotID			integer,
	PRIMARY KEY (rechnungszeileID),
	FOREIGN KEY (rechnungID) REFERENCES rechnung(rechnungID) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (angebotID) REFERENCES angebot(angebotID) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO rechnungszeile (rechnungID, kommentar, steuersatz, betrag, angebotID) VALUES (1, 'tomaten', 10, 5, 1);
INSERT INTO rechnungszeile (rechnungID, kommentar, steuersatz, betrag, angebotID) VALUES (1, 'apfel', 10, 10, 1);


DROP TABLE kategorie CASCADE;
CREATE TABLE kategorie(
	kbz 				varchar(20),
	beschreibung		varchar(100),
	PRIMARY KEY (kbz)
);

INSERT INTO kategorie VALUES ('Einnahme', 'Einnahme');
INSERT INTO kategorie VALUES ('Ausgabe', 'Ausgabe');
INSERT INTO kategorie VALUES ('Steuer', 'Steuer');
INSERT INTO kategorie VALUES ('SVA', 'Sozialversicherungsanstalt-Beitrag');


DROP TABLE buchungszeile CASCADE;
CREATE TABLE buchungszeile(
	buchungszeileID 	integer auto_increment,
	datum 				date,
	kommentar			varchar(100),
	steuersatz			double,
	betrag				double,
	kbz					varchar(20),
	PRIMARY KEY (buchungszeileID),
	FOREIGN KEY (kbz) REFERENCES kategorie(kbz) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO buchungszeile (datum,kommentar, steuersatz, betrag, kbz) VALUES (current_timestamp,'meine buchungszeile', 20, 100, 'Steuer');
INSERT INTO buchungszeile (datum,kommentar, steuersatz, betrag, kbz) VALUES (current_timestamp,'meine zweite buchungszeile', 10, 50, 'Einnahme');


DROP TABLE rechnung_buchungszeile CASCADE;
CREATE TABLE rechnung_buchungszeile(
	rechnungID			integer,
	buchungszeileID		integer,
	PRIMARY KEY (rechnungID, buchungszeileID),
	FOREIGN KEY (rechnungID) REFERENCES rechnung(rechnungID) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (buchungszeileID) REFERENCES buchungszeile(buchungszeileID) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO rechnung_buchungszeile VALUES (1,1);
INSERT INTO rechnung_buchungszeile VALUES (4,1);
INSERT INTO rechnung_buchungszeile VALUES (2,2);
INSERT INTO rechnung_buchungszeile VALUES (3,2);


--ALTER TABLE ORDERS ADD FOREIGN KEY (customer_sid) REFERENCES CUSTOMER(SID);
