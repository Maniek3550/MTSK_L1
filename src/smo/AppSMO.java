package smo;

/**
 * @author Dariusz Pierzchala
 * 
 * Description: Klasa g³ówna SmoAppDesKit. Tworzy kolejkê obiektów - zg³oszeñ, gniazda obs³ugi. Startuje symulacjê.
 * 
 * Wersja testowa.
 */

import java.math.BigDecimal;
import java.util.Date;

import dissimlab.monitors.Diagram;
import dissimlab.monitors.Statistics;
import dissimlab.monitors.Diagram.DiagramType;
import dissimlab.simcore.SimEventSemaphore;
import dissimlab.simcore.SimControlEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;
import dissimlab.simcore.SimParameters;
import dissimlab.simcore.SimParameters.SimControlStatus;

public class AppSMO {
	public static void main(String[] args) {
		try {
			SimManager model = SimManager.getInstance();
			//Semafor
			SimEventSemaphore semafor = new SimEventSemaphore("Semafor dla SMO");
			// Powo³anie 2-ego Smo
			SmoBis smoBis = new SmoBis(5, semafor);
			// Powo³anie Smo nr 1 
			Smo smo = new Smo(smoBis, semafor);
			// Utworzenie otoczenia
			Otoczenie generatorZgl = new Otoczenie(smo);
			// Dwa sposoby zaplanowanego koñca symulacji
			//model.setEndSimTime(10000);
			// lub
			SimControlEvent stopEvent = new SimControlEvent(60.0, SimControlStatus.STOPSIMULATION);
			// Badanie czasu trwania eksperymentu - pocz¹tek
			long czst = new Date().getTime();
			// Uruchomienie symulacji za poœrednictwem metody "start" z
			model.startSimulation();
			// Badanie czasu trwania eksperymentu - koniec
			czst = new Date().getTime() - czst;
			// Wyniki
			System.out.println("Czas trwania eksperymentu: " + czst);

			// Formatowanie liczby do 2 miejsc po przecinku
			double wynik = new BigDecimal(Statistics
					.arithmeticMean(smo.MVczasy_oczekiwania)).setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue();
			System.out
					.println("Wartoœæ œrednia czasu oczekiwania na obs³ugê:   "
							+ wynik);
			wynik = new BigDecimal(Statistics
					.standardDeviation(smo.MVczasy_oczekiwania)).setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue();
			System.out
					.println("Odchylenie standardowe dla czasu obs³ugi:       "
							+ wynik);
			wynik = new BigDecimal(Statistics.max(smo.MVczasy_oczekiwania))
			.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			System.out.println("Wartoœæ maksymalna czasu oczekiwania na obs³ugê: "
					+ wynik);
			wynik = new BigDecimal(Statistics
					.arithmeticMean(smo.MVdlKolejki)).setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue();
			System.out
					.println("Wartoœæ œrednia d³ugoœci kolejki:       "
							+ wynik);
			wynik = new BigDecimal(Statistics
					.max(smo.MVdlKolejki)).setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue();
			System.out
					.println("Wartoœæ maksymalna d³ugoœci kolejki:       "
							+ wynik);


			Diagram d1 = new Diagram(DiagramType.DISTRIBUTION, "Czas obs³ugiwania");
			d1.add(smo.MVczasy_obslugi, java.awt.Color.GREEN);
			d1.show();

			Diagram d2 = new Diagram(DiagramType.DISTRIBUTION,
					"Dlugoœæ kolejki w SMO nr 2");
			d2.add(smoBis.MVdlKolejki, java.awt.Color.BLUE);
			d2.show();

			Diagram d3 = new Diagram(DiagramType.HISTOGRAM,
					"Czasy oczekiwania na obs³ugê");
			d3.add(smo.MVczasy_oczekiwania, java.awt.Color.BLUE);
			d3.show();

			Diagram d4 = new Diagram(DiagramType.DISTRIBUTION,
					"D³ugoœæ kolejki w czasie");
			d4.add(smo.MVdlKolejki, java.awt.Color.RED);
			d4.show();
		} catch (SimControlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
