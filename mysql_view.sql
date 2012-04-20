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
	WHERE rz.angebotid = a.angebotid
;

DROP VIEW IF EXISTS rechnungview;
CREATE VIEW rechnungview AS
	SELECT r.rechnungid, r.status, r.datum
	FROM rechnung r
;