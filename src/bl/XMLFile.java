package bl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bl.objects.Eingangsrechnung;
import bl.objects.Kontakt;
import bl.objects.Rechnungszeile;
import dal.DALException;

public class XMLFile {
	private File file;
	private String log;

	public XMLFile(File file) {
		this.file = file;
		log = "";
	}

	public void importRechnungen() throws ParserConfigurationException,
			SAXException, IOException {
		readXMLbyDOM();
	}

	public void close() {

	}

	public void addLogLine(String txt) {
		log += txt + "\n\n";
	}

	public String getLog() {
		return log;
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}

	private void readXMLbyDOM() throws ParserConfigurationException,
			SAXException, IOException {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document document = null;

		db = dbf.newDocumentBuilder();
		document = db.parse(file);
		document.getDocumentElement().normalize();
		// name=document.getDocumentElement().getNodeName();

		NodeList nl = document.getElementsByTagName("eingangsrechnung");
		for (int i = 0; i < nl.getLength(); i++) {
			this.addLogLine("----- Eingangsrechnung #" + (i + 1));
			Node n = nl.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element eingangsrechnung = (Element) n;
				Eingangsrechnung r = new Eingangsrechnung();
				try {
					r.setDatum(getTagValue("datum", eingangsrechnung));
				} catch (ParseException e) {
					r.setDatum(new Date());
				}
				r.setStatus(getTagValue("status", eingangsrechnung));

				Element kontakt = (Element) eingangsrechnung
						.getElementsByTagName("kontakt").item(0);
				Kontakt k = new Kontakt(getTagValue("firma", kontakt),
						getTagValue("name", kontakt), getTagValue("telefon",
								kontakt));
				try {
					Integer kontaktID = BL.containsKontakt(k);
					if (kontaktID != -1) {
						r.setKontaktID(kontaktID);
						k.setKontaktID(kontaktID);
						this.addLogLine("USE " + k.toString());
					} else {
						kontaktID = BL.saveKontakt(k);
						r.setKontaktID(kontaktID);
						k.setKontaktID(kontaktID);
						this.addLogLine("ADD " + k.toString());
					}
				} catch (DALException e) {
					e.printStackTrace();
					this.addLogLine("ERROR: " + k.toString()
							+ "\nERRORMESSAGE: " + e.getMessage());
				}
				try {
					BL.saveEingangsrechnung(r);
					this.addLogLine("ADD " + r.toString());
				} catch (DALException e) {
					e.printStackTrace();
					this.addLogLine("ERROR " + r.toString()
							+ "\nERRORMESSAGE: " + e.getMessage());
				}

				NodeList rechnungszeilen = eingangsrechnung
						.getElementsByTagName("rechnungszeile");
				for (int j = 0; j < rechnungszeilen.getLength(); j++) {
					Element rechnungszeile = (Element) rechnungszeilen.item(j);
					Rechnungszeile p = null;
					String kommentar = getTagValue("kommentar", rechnungszeile);
					String steuersatz = getTagValue("steuersatz",
							rechnungszeile);
					String betrag = getTagValue("betrag", rechnungszeile);
					try {
						p = new Rechnungszeile(kommentar,
								Double.parseDouble(steuersatz),
								Double.parseDouble(betrag), r.getRechnungID(),
								null);
					} catch (Exception e) {
						this.addLogLine("ERROR Rechnungszeile [kommentar="
								+ kommentar + ", steuersatz=" + steuersatz
								+ ", betrag=" + betrag + "]\nERRORMESSAGE: "
								+ e.getMessage());
					}
					try {
						if (p != null) {
							int rechnungszeileID = BL.saveRechnungszeile(p);
							p.setRechnungszeileID(rechnungszeileID);
							this.addLogLine("ADD " + p.toString());
						}
					} catch (DALException e) {
						e.printStackTrace();
						this.addLogLine("ERROR " + p.toString()
								+ "\nERRORMESSAGE: " + e.getMessage());
					}
				}
			}

			this.addLogLine("");
		}
	}
}
