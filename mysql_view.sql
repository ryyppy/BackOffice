DROP VIEW IF EXISTS jahresumsatz;
CREATE VIEW jahresumsatz AS
	SELECT a.projektid, p.name as 'projektName', COUNT(summe) as 'AnzahlAngebote', YEAR(datum) as 'Jahr',
			SUM(summe) as 'SummeAngebote', SUM(summe)/COUNT(summe) as 'AvgAngebote' 
	FROM angebot a, projekt p
	WHERE a.projektid=p.projektid
	GROUP BY projektid
;

DROP VIEW IF EXISTS stundensatz;
CREATE VIEW stundensatz AS
	SELECT p.projektid, p.name AS Projekt ,a.angebotid, a.beschreibung as Angebot, 
			a.dauer, a.summe, round(a.summe/a.dauer,2) AS Stundensatz
	FROM projekt p, angebot a
	WHERE p.projektid = a.projektid
;

DROP VIEW IF EXISTS offeneProjekte;
CREATE VIEW offeneProjekte AS
	SELECT *
	FROM projekt p
	WHERE p.projektid NOT IN
		(SELECT DISTINCT projektid FROM angebot)
;

DROP VIEW IF EXISTS offeneAusgangsrechnungen;
CREATE VIEW offeneAusgangsrechnungen AS
	SELECT r.rechnungid, r.status, r.datum, k.kundeid, k.nachname as Kunde
	FROM rechnung r, ausgangsrechnung a, kunde k
	WHERE r.status='offen'
	AND r.rechnungid = a.rechnungid
	AND a.kundeid = k.kundeid
;

DROP VIEW IF EXISTS einnahmen;
CREATE VIEW einnahmen AS
	SELECT rz.rechnungszeileid, rz.kommentar, rz.steuersatz, rz.betrag, r.datum
	FROM rechnungszeile rz, rechnung r
	WHERE rz.rechnungid=r.rechnungid 
	AND rz.rechnungid IN 
		(SELECT rechnungid FROM ausgangsrechnung)
;

DROP VIEW IF EXISTS ausgaben;
CREATE VIEW ausgaben AS
	SELECT rz.rechnungszeileid, rz.kommentar, rz.steuersatz, rz.betrag, r.datum
	FROM rechnungszeile rz, rechnung r
	WHERE rz.rechnungid=r.rechnungid 
	AND rz.rechnungid IN 
		(SELECT rechnungid FROM eingangsrechnung)
;

DROP VIEW IF EXISTS angebotview;
CREATE VIEW angebotview AS 
	SELECT a.angebotid, a.beschreibung, a.summe, a.dauer, a.chance, a.datum, k.kundeid, k.nachname AS kunde, p.projektid, p.name AS projekt
	FROM angebot a, kunde k, projekt p
	WHERE a.kundeid = k.kundeid
	AND a.projektid = p.projektid
;

DROP VIEW IF EXISTS ausgangsrechnungview;
CREATE VIEW ausgangsrechnungview AS
	SELECT ar.rechnungid, k.kundeid, k.nachname AS kunde
	FROM ausgangsrechnung ar, kunde k
	WHERE ar.kundeid = k.kundeID
;

DROP VIEW IF EXISTS eingangsrechnungview;
CREATE VIEW eingangsrechnungview AS
	SELECT er.rechnungid, k.kontaktid, k.firma AS kontakt
	FROM eingangsrechnung er, kontakt k
	WHERE er.kontaktid = k.kontaktid
;

DROP VIEW IF EXISTS buchungszeileview;
CREATE VIEW buchungszeileview AS
	SELECT bz.buchungszeileid, bz.datum, bz.kommentar, bz.steuersatz, bz.betrag, k.kkbz
	FROM buchungszeile bz, kategorie k
	WHERE bz.kkbz = k.kkbz
;

DROP VIEW IF EXISTS kundeview;
CREATE VIEW kundeview AS
	SELECT k.kundeID, k.vorname, k.nachname, k.geburtsdatum
	FROM kunde k
;
DROP VIEW IF EXISTS kontaktview;
CREATE VIEW kontaktview AS
	SELECT k.kontaktid, k.firma, k.name, k.telefon
	FROM kontakt k
;

DROP VIEW IF EXISTS projektview;
CREATE VIEW projektview AS
	SELECT p.projektid, p.name, p.beschreibung, p.verbrauchteStunden
	FROM projekt p
;

DROP VIEW IF EXISTS rechnungszeileview;
CREATE VIEW rechnungszeileview AS
	SELECT rz.rechnungszeileid, rz.kommentar, rz.steuersatz, rz.betrag, rz.rechnungid, a.angebotid, a.beschreibung AS angebot
	FROM rechnungszeile rz, angebot a
	WHERE rz.angebotid is not null and rz.angebotid = a.angebotid
	UNION
	SELECT rz.rechnungszeileid, rz.kommentar, rz.steuersatz, rz.betrag, rz.rechnungid, null, null AS angebot
	FROM rechnungszeile rz
	WHERE rz.angebotid is null
;SELECT * FROM rechnungszeileview;

DROP VIEW IF EXISTS rechnungview;
CREATE VIEW rechnungview AS
	SELECT r.rechnungid, r.status, r.datum
	FROM rechnung r
;