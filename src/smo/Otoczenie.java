package smo;

import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;


public class Otoczenie extends BasicSimObj {
    public Zglaszaj zglaszaj;
    public MonitoredVar MVczasy_miedzy_zgl;
    public Smo smo;

    public Otoczenie(Smo smo) throws SimControlException {
        // Powo³anie instancji pierwszego zdarzenia
    	zglaszaj = new Zglaszaj(this, 0.0);
        // Deklaracja zmiennych monitorowanych
        MVczasy_miedzy_zgl = new MonitoredVar();
        // SMO dla zg³oszeñ
        this.smo = smo;
	}
}
