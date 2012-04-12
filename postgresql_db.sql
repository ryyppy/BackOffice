DROP DATABASE IF EXISTS swe;
CREATE DATABASE swe;
\c swe;

DROP TABLE projekt CASCADE;
CREATE TABLE projekt(
	projektID 		serial,
	name 			varchar(30),
	beschreibung	varchar(100),
	PRIMARY KEY (projektID)
);

INSERT INTO projekt(name, beschreibung) VALUES('bitstrips', 'cool');
INSERT INTO projekt(name, beschreibung) VALUES('wow', 'cool');


DROP TABLE kunde CASCADE;
CREATE TABLE kunde(
	kundeID 		serial,
	vorname 		varchar(50),
	nachname 		varchar(50),
	geburtsdatum 	date,
	PRIMARY KEY (kundeID)
);

INSERT INTO kunde(vorname, nachname, geburtsdatum) VALUES ('armin', 'mehr', current_timestamp);
INSERT INTO kunde(vorname, nachname, geburtsdatum) VALUES ('patrick', 'stapfer', current_timestamp);


DROP TABLE angebot CASCADE;
CREATE TABLE angebot(
	angebotID 		serial, 
	summe 			numeric, 
	dauer 			numeric,
	datum 			date,
	chance 			numeric,
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
	kontaktID 		serial,
	firma    		varchar(50),
	name			varchar(50),
	telefon  		varchar(20),
	PRIMARY KEY (kontaktID)
);

INSERT INTO kontakt(firma, name, telefon) VALUES ('Niedermeyer', 'Max Mustermann', '01234567');
INSERT INTO kontakt(firma, name, telefon) VALUES ('Ditech', 'Mr Ansprechpartner', '01234567');


DROP TABLE rechnung CASCADE;
CREATE TABLE rechnung(
	rechnungID 		serial,
	status 			varchar(20),
	datum 			date,
	PRIMARY KEY (rechnungID)
);

DROP TABLE ausgangsrechnung CASCADE;
CREATE TABLE ausgangsrechnung (
	kundeID integer, 
	PRIMARY KEY (rechnungID)
) INHERITS (rechnung);

INSERT INTO ausgangsrechnung (status, datum, kundeID) VALUES ('offen', current_timestamp, 0);
INSERT INTO ausgangsrechnung (status, datum, kundeID) VALUES ('bezahlt', current_timestamp, 1);


DROP TABLE eingangsrechnung CASCADE;
CREATE TABLE eingangsrechnung(
	kontaktID integer,
	PRIMARY KEY (rechnungID)
) INHERITS (rechnung);

INSERT INTO eingangsrechnung (status, datum, kontaktID) VALUES ('offen', current_timestamp, 0);
INSERT INTO eingangsrechnung (status, datum, kontaktID) VALUES ('bezahlt', current_timestamp, 1);


DROP TABLE rechnungszeile CASCADE;
CREATE TABLE rechnungszeile(
	rechnungszeileID	serial,
	rechnungID			integer,
	kommentar			varchar(100),
	steuersatz			numeric,
	betrag				numeric,
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
	buchungszeileID 	serial,
	kommentar			varchar(100),
	steuersatz			numeric,
	betrag				numeric,
	kbz					varchar(20),
	PRIMARY KEY (buchungszeileID),
	FOREIGN KEY (kbz) REFERENCES kategorie(kbz) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO buchungszeile (kommentar, steuersatz, betrag, kbz) VALUES ('meine buchungszeile', 20, 100, 'Steuer');
INSERT INTO buchungszeile (kommentar, steuersatz, betrag, kbz) VALUES ('meine zweite buchungszeile', 10, 50, 'Einnahme');


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
