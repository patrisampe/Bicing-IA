package	bicing;

import	java.io.FileWriter;
import	java.io.IOException;
import	java.nio.file.Files;
import	java.nio.file.Path;
import	java.nio.charset.Charset;
import	java.nio.file.Paths;
import	java.util.List;
import	java.util.Iterator;
import	java.util.Properties;

import	IA.Bicing.Estacion;
import	IA.Bicing.Estaciones;
import	aima.search.framework.Problem;
import	aima.search.framework.Search;
import	aima.search.framework.SearchAgent;
import	aima.search.informed.HillClimbingSearch;
import	aima.search.informed.SimulatedAnnealingSearch;

public	class	Main	{
	
	public	static	void	main(String[]	args)	throws	Exception	{
		//	args[0]	nombre	del	fichero	de	entrada
		//Path	path	=	Paths.get(System.getProperty("user.dir"),	args[0]);
		Path	path	=	Paths.get(System.getProperty("user.dir"),	"file/exemple.txt");
		Charset	charset	=	Charset.forName("ISO-8859-1");
		List<String>	lines	=	Files.readAllLines(path,	charset);
		String	alg	=	getString(lines.get(10));
		SA(lines);
	}
	
	private	static	void	SA(List<String>	lines)	throws	Exception	{
		//Leemos	datos	necesarios	para	HC
		int	repeticiones	=	5;
		int	numB	=	1250;
		int	numE	=	25;
		int	numF	=	5;
		int	dem	=	Estaciones.EQUILIBRIUM;
		int	itMAX	=	15000;
		int	semillas[]	=	{1234,242359,986,5437,72463,97856,7105,3189,13594,4};
		int	its[]	=	{10,20,30,40,50,60,70};
		int	ks[]	=	{5,10,15,20,25,30,40,50,60,70};
		double	lams[]	=	{0.1,0.01,0.001,0.0001,0.00001};
		FileWriter	fichero1	=	new	FileWriter("pruebasExp3.txt");
		FileWriter	fichero2	=	new	FileWriter("mejoresExp3.txt");
		for	(int	j	=	0;	j	<	semillas.length;	++j)	{
			int	seed	=	semillas[j];
			GeneraProblema.CrearProblema(numE,	numB,	dem,	seed);
			Estado	estado	=	Estado.estadoInicial_v1(numF,	numE);
			SuccessorsSA2	succ	=	new	SuccessorsSA2();
			EstadoFinal	ef	=	new	EstadoFinal();
			Problem	problem	=	new	Problem(estado,	succ,	ef,	new	FuncionHeuristica2());
			int	maxit	=	its[0];
			int	maxk	=	ks[0];
			double	maxlam	=	lams[0];
			double	maxbeneficio	=	0;
			for	(int	i	=	0;	i	<	its.length;	++i)	{
				int	it	=	its[i];
				for	(int	l	=	0;	l	<	ks.length;	++l)	{
					int	k	=	ks[l];
					for	(int	p	=	0;	p	<	lams.length;	++p)	{
						double	lam	=	lams[p];
						double	money	=	0;
						for	(int	r	=	0;	r	<	repeticiones;	++r)	{
							Search	search	=	new	SimulatedAnnealingSearch(itMAX,	it,	k,	lam);
							SearchAgent	agent	=	new	SearchAgent(problem,	search);
							Estado	result	=	(Estado)	search.getGoalState();
							money	+=	result.getBicisBienColocadas()-result.getBicisMalColocadas();
						}
						money	/=	repeticiones;
						fichero1.write(seed	+	"	"	+itMAX	+	"	"	+	it	+	"	"	+	k	+	"	"	+	lam	+	"	"	+	money	+	"\n");
						if	(money	>	maxbeneficio)	{
							maxit	=	it;
							maxk	=	k;
							maxlam	=	lam;
							maxbeneficio	=	money;
						}
					}
				}		
			}
			
			fichero2.write(seed	+	"	"	+itMAX	+	"	"	+	maxit	+	"	"	+	maxk	+	"	"	+	maxlam	+	"	"	+	maxbeneficio	+	"\n");
			System.out.println(seed	+	"	"	+itMAX	+	"	"	+	maxit	+	"	"	+	maxk	+	"	"	+	maxlam	+	"	"	+	maxbeneficio);
		}
		fichero1.close();
		fichero2.close();
	}
	
	//Funcions	auxiliars
	
	private	static	int	getNum(String	s)	{
		int	i	=	s.indexOf(':');
		String	num	=	s.substring(i+2);
		return	Integer.parseInt(num);
	}
	
	private	static	String	getString(String	s)	{
		int	i	=	s.indexOf(':');
		return	s.substring(i+2);
	}
	
	private	static	double	getDouble(String	s)	{
		int	i	=	s.indexOf(':');
		String	num	=	s.substring(i+2);
		return	Double.parseDouble(num);
	}
	
	private	static	int	readDemanda(String	linia)	{
		int	dem	=	Estaciones.RUSH_HOUR;
		String	demS	=	getString(linia);
		if	(demS.equals("EQUILIBRIUM"))	dem	=	Estaciones.EQUILIBRIUM;
		return	dem;
	}
	
	
	private	static	void	printEstado(Estado	result,	boolean	inicial)	{
		if	(inicial)	System.out.println("Estado	inicial");
		else	System.out.println("Estado	final");
		int	bien	=	result.getBicisBienColocadas();
		int	mal	=	result.getBicisMalColocadas();
		System.out.println("Beneficios:	"	+	(bien-mal));
		result.print();
	}
	
	private	static	void	printEstaciones(int	numF)	{
		Estaciones	Est	=	GeneraProblema.getEstaciones();
		System.out.println("Estaciones	(NoUsadas,	Prevision	Demanda)");
		int	beneficiosMax	=	0;
		int	faltan	=	0;
		int	sobran	=	0;
		for	(int	i	=	0;	i	<	Est.size();	++i)	{
			Estacion	est	=	Est.get(i);
			int	dif	=	est.getDemanda()	-	est.getNumBicicletasNext();
			if	(dif	<	0)	{dif	=	0;	sobran	+=	-dif;}
			if	(dif	>	0)	faltan	+=	dif;
			beneficiosMax	+=	dif;
			System.out.println("Estacion	"	+	i);
			System.out.println(est.getNumBicicletasNoUsadas()	+	"	"	+		est.getNumBicicletasNext()	+	"	"	+	est.getDemanda());
		}
		int	aux	=	numF*30;
		if	(aux	<	beneficiosMax)	beneficiosMax	=	aux;
		System.out.println("Beneficios	Maximos:	"	+	beneficiosMax);
		System.out.println("Min(sobran,faltan):	"	+	sobran	+	"	"	+	faltan);
		System.out.println();
	}
	
	private	static	int	min(int	a,	int	b)	{
		if	(a	<	b)	return	a;
		else	return	b;
	}
	
	private	static	void	calculBestia(Estado	result)	{
		Struct[]	v	=	result.getvEstaciones();
		Estaciones	Est	=	GeneraProblema.getEstaciones();
		int	beneficios	=	0;
		System.out.println("Entren	Surten");
		for	(int	i	=	0;	i	<	v.length;	++i)	{
			Struct	s	=	v[i];
			Estacion	est	=	Est.get(i);
			int	dif	=	est.getDemanda()	-	est.getNumBicicletasNext();
			int	delta	=	s.getBicisColocades()	-	s.getBicisAgafen();
			if	(delta	<	0)	{
				if	(dif	>	0)	beneficios	+=	delta;
				if	(dif	<=	0){
					if(dif>delta)beneficios	+=	delta	-	dif;
				}
			}
			else	{
				if	(dif	>	0)	beneficios	+=	min(delta,	dif);
			}
			System.out.println(i	+	"	->	"	+		s.getBicisColocades()	+	"	"	+		s.getBicisAgafen());
		}
		System.out.println("Beneficios	REALES:	"	+	beneficios);
	}
	
	private	static	void	printInstrumentation(Properties	properties)	{
								Iterator	keys	=	properties.keySet().iterator();
								while	(keys.hasNext())	{
												String	key	=	(String)	keys.next();
												String	property	=	properties.getProperty(key);
												System.out.println(key	+	"	:	"	+	property);
								}
				}
				
				private	static	void	printActions(List	actions)	{
					System.out.println(	"ACTIIIIOONSS	");
								for	(int	i	=	0;	i	<	actions.size();	i++)	{
												String	action	=	(String)	actions.get(i);
												System.out.println(action);
								}
				}
}