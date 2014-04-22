package smo;
/**
 * @author Dariusz Pierzchala
 * 
 * Description: Klasa kolejki obiekt�w - zg�osze� do gniazda obs�ugi. Sk�adowa
 * SmoAppDesKit.
 */

import java.util.LinkedList;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimEventSemaphore;



public class SmoBis extends BasicSimObj 
{
    private LinkedList <Zgloszenie> kolejka;
    private boolean wolne = true;
    private int maxDlKolejki;
    //
	SimEventSemaphore semafor;
	//
    public RozpocznijObslugeBis rozpocznijObsluge;
    public ZakonczObslugeBis zakonczObsluge;
    //public OdblokujGniazdo odblokuj; 
    //
    public MonitoredVar MVczasy_obslugi;
    public MonitoredVar MVczasy_oczekiwania;
    public MonitoredVar MVdlKolejki;

    /** Creates a new instance of SmoBis 
     * @throws SimControlException */
    public SmoBis(int maxDlKolejki, SimEventSemaphore semafor) throws SimControlException
    {
        kolejka = new LinkedList <Zgloszenie>();
        this.maxDlKolejki = maxDlKolejki;
        this.semafor = semafor;
        //
        // Deklaracja zmiennych monitorowanych
        MVczasy_obslugi = new MonitoredVar();
        MVczasy_oczekiwania = new MonitoredVar();
        MVdlKolejki = new MonitoredVar();
    }

    // Wstawienie zg�oszenia do kolejki
    public boolean dodaj(Zgloszenie zgl)
    {
    	if(liczbaZgl() < maxDlKolejki){
    		kolejka.add(zgl);
    		MVdlKolejki.setValue(kolejka.size());
    		return true;
    	}
        return false;
    }

    // Pobranie zg�oszenia z kolejki
    public Zgloszenie usun()
    {
    	Zgloszenie zgl = (Zgloszenie) kolejka.removeFirst();
        MVdlKolejki.setValue(kolejka.size());
        return zgl;
    }

    // Pobranie zg�oszenia z kolejki
    public boolean usunWskazany(Zgloszenie zgl)
    {
    	Boolean b= kolejka.remove(zgl);
        MVdlKolejki.setValue(kolejka.size());
        return b;
    }
    
    public int liczbaZgl()
    {
        return kolejka.size();
    }

	public boolean isWolne() {
		return wolne;
	}

	public void setWolne(boolean wolne) {
		this.wolne = wolne;
	}
	
	public SimEventSemaphore getSemafor() {
		return semafor;
	}

	public void setSemafor(SimEventSemaphore semafor) {
		this.semafor = semafor;
	}
	public int getMaxDlKolejki() {
		return maxDlKolejki;
	}

	public void setMaxDlKolejki(int maxDlKolejki) {
		this.maxDlKolejki = maxDlKolejki;
	}
}