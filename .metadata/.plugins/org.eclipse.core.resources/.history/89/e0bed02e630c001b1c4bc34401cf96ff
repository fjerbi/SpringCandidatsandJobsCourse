package views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
//import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.lucene.document.Document;
import org.graphstream.graph.implementations.SingleGraph;

import sparql.SPARQL;
import lucene.Indexing;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;

public class ProjectFrame  
{
	//private static final long serialVersionUID = 1L;
	public static JTabbedPane onglet;
	//private JSplitPane split;
	private JPanel topPanel = new JPanel(new FlowLayout());
	private	JButton bt_updateGraph;
	private JPanel[] tPan;
	private Boolean texteEditor=false;

	//Création d'un éditeur RDF text
	private JEditorPane EditeurRdf;

	private int change=0;

	//barre de menu

	private MyMenuBar menuBar;
	private JMenu menu = new JMenu("File");	
	private OpenFileChooserAction action;	  
	//private JButton Graphinit=new JButton("Graph initial");
	
	private URL url_iconNew;
	private URL url_iconExit;
	private URL url_iconRefresh;
	private Image imgNew;
	private Image imgExit;
	private Image imgRefresh;	
	
	/* accès au toolkit */
	
	Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();	
	
	private static JFrame f ;
	private JMenuItem open;
	private JMenuItem exit;
	
	//champs pour la recherche	
	private ChampTexte recherch;
	private JLabel recherchLabel;	

	private SousGraph GrapheSteiner;
	private JScrollPane centerPanel = new JScrollPane();
	//private JPanel mainPanel = new JPanel(new FlowLayout());
	//private JPanel mainPanelgrph = new JPanel(new FlowLayout());

	//Lien  du fichier RDF  DE L'ARBRE DE  STEINER
	private String LienFichierRDF;

	//MODEL RDF de L'ARBRE DE  STEINER
	public static Model ModelSparql;

	//tableau pourr visualiser les resultats de la recherche					
	private JTable tableau; 

	//modele de donnees de notre table
	static JTableRessourceModel tableModel;

	//Construction du Menu
	private void InitMenu(){
		//System.err.println("dir: " + System.getProperty("user.dir"));
		
		/* récupération des url */
		url_iconNew = this.getClass().getResource("/icones/open.png");
		url_iconExit = this.getClass().getResource("/icones/logout.png");
		url_iconRefresh = this.getClass().getResource("/icones/refresh.png");
		
		//System.out.println("----------------------------------");		
		//System.out.println(url_iconNew);
		//System.out.println(url_iconExit);
		//System.out.println(url_iconRefresh);		
		
		/* récupération des images */
		imgNew = toolkit.getImage(url_iconNew);
		imgExit = toolkit.getImage(url_iconExit);
		imgRefresh = toolkit.getImage(url_iconRefresh);
		
		open= new JMenuItem("New", new ImageIcon(imgNew));					  
		exit = new JMenuItem("Exit", new ImageIcon(imgExit));
		
		f=new JFrame();
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Searching in RDF graphs");

		f.setSize(800, 800);
		f.setAlwaysOnTop(true);
		f.setResizable(true);
		f.setExtendedState( f.getExtendedState()|JFrame.MAXIMIZED_BOTH );
		menuBar = new MyMenuBar(f);	

		//contruction du paneau superieur dans l'onglet "Recherche"

		topPanel.setSize(660, 40);
		recherchLabel = new JLabel("Recherche:");
		topPanel.add(recherchLabel);
		recherch = new ChampTexte();		
		recherch.setPreferredSize(new Dimension(300,25));
		topPanel.add(recherch);	
		
		bt_updateGraph=new JButton();
		bt_updateGraph.setIcon(new ImageIcon(imgRefresh));
		bt_updateGraph.setPreferredSize(new Dimension(25,25));
		bt_updateGraph.setToolTipText("create SPARQL graph");
        EditeurRdf = new JEditorPane();
		topPanel.add(bt_updateGraph);
		topPanel.setSize(f.getWidth(),f.getHeight());
		topPanel.setBackground(Color.WHITE);		
		
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gb.setConstraints(topPanel, gbc); // mise en forme des objets

		//construction du map de Documents lucene vide
		
		HashMap<String,Set<Document>> map = new HashMap<String,Set<Document>>();
		tableModel = new JTableRessourceModel(map);
		tableModel.fireTableDataChanged();
		tableau = new JTable(tableModel); 
		tableau.setBackground(Color.WHITE);
		centerPanel.setViewportView(tableau);
		gb.setConstraints(centerPanel, gbc);

		// choisir un type de curseur sur  le menu 

		Cursor cursor=new  Cursor(Cursor.HAND_CURSOR);
		menu.setCursor(cursor);
		menu.setMnemonic(KeyEvent.VK_F);
		menu.setForeground(Color.GREEN);
		open.setMnemonic(KeyEvent.VK_N);
		open.setToolTipText("Open a file");

		action=new OpenFileChooserAction(f, texteEditor);
		open.addActionListener(action);
		exit.setMnemonic(KeyEvent.VK_E);
		exit.setToolTipText("Exit application");
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,ActionEvent.CTRL_MASK));
		menu.add(open);
		menu.addSeparator();
		menu.add(exit);

		//redefinition des dimensions du  menu
		menuBar.add(Box.createRigidArea(new Dimension(5,70)));
		menuBar.add(menu);
		
		//initialisation des onglets
		InitOnglet();
		tPan[0].setLayout(gb);
		tPan[0].add(topPanel);
		tPan[0].add(centerPanel);

		//On passe ensuite les onglets au contentpane de la fenetre principale
		f.getContentPane().add(onglet);
		f.setJMenuBar(menuBar);			
		f.setVisible(true);
	}

	public void InitOnglet(){	
		
		//Création des Panneaux "Recherche,Editeur RDF,Resultat Recherche"
		tPan =new  JPanel[3];
		tPan[0]= new JPanel();
		tPan[1]= new JPanel();

		onglet = new JTabbedPane();
		int i = 0;
		//int j= 1;
		for(JPanel pan : tPan){
			if(i==0)
				onglet.addTab("Recherche", pan);
			if(i==1){
				onglet.addTab("Editeur de texte Rdf/owl", new JScrollPane(EditeurRdf));
			}
			i++;
		}
	}

	public void InitActions(){		
		/* Action  éxécuter lors des changements des Onglets */

		onglet.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent e){ 
				// init();
				change=1;
				EditeurRdf.setText("Saisissez un ensemble de données de type rdf/owl ou Faites un copier-coller");          
			}
		});
		
		// mis à jours du fichier tmp.rdf situé dans le dossier 'src/main/resources/files
		// lors de l'écriture  dans  l'editeur EditeurRdf géré par l'onglet 'Editeur Rdf/owl;		 

		EditeurRdf.getDocument().addDocumentListener(new DocumentListener(){

			public void insertUpdate(DocumentEvent de) { 
				if(change!=1)
				{
					//System.out.println("INsert");
					//System.out.println(EditeurRdf.getText());

					FileWriter fw = null;
					try {
						fw = new FileWriter(new File("src/main/java/jena/tmp.rdf"));
						fw.write(EditeurRdf.getText());
						fw.close();
					}
					catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} 
					catch (IOException e1) {
						e1.printStackTrace();
					}

					Model model = FileManager.get().loadModel("src/main/java/jena/tmp.rdf");
					//File path = new File("src/main/java/lucene/lucene");

					try{
						onglet.removeTabAt(2);
					}
					catch(Exception e){}

					try {
						texteEditor=true;
						action.SetTexteEditor( false);
						fr.uvsq.project.rdfSearch.App.index = new Indexing(model);
						//System.out.println("fichier tmp.rdf chargée!");
						javax.swing.JOptionPane.showMessageDialog(f,"fichier 'tmp.rdf' créé dans 'src/main/java/jena/'. Aller sur l'onglet 'Recherche'pour débuter"); 

					} 
					catch (Exception ex) {	
						ex.printStackTrace();
						javax.swing.JOptionPane.showMessageDialog(f,"Erreur survenue lors de la création du fichier rdf.veuillez rédémarer...");
					}
				}
				else
					change=0;
			}

			public void removeUpdate(DocumentEvent de){
				//System.out.println("Remove");
				if(change==0){}
				else
					change=1;
			}

			public void changedUpdate(DocumentEvent arg0) {
				//System.out.println("change");
				change=1;
			}
		}); //end documentListener

		/*Fin de la mise à jours du fichier tmp.rdf*/

		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {

				System.exit(0);
			}
		});

		bt_updateGraph.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0){

				List<String>Resource=new ArrayList<String>();
				int val=tableModel.getRowCount();
				int i=0;
				for(i=0;i<val;i++)
					Resource.add(tableModel.getValueAt(i,1).toString()); 
				org.graphstream.graph.Graph graphfinal = new SingleGraph("GraphSteiner");
				String a="";

				if(texteEditor && !action.getTexteEditor()){
					a="src/main/java/jena/tmp.rdf";
				}
				else{
					//Supprime l'onglet resultat recherche si il existe					
					a=action.getpath()[0];
				}
				
				try{
					onglet.removeTabAt(2);
				}
				catch(Exception e){}

				GrapheSteiner=new SousGraph(a,Resource,graphfinal,f,onglet,true);
				GrapheSteiner.exec();

				//MODEL SPARQL crée par la classe SousGraph
				ModelSparql=GrapheSteiner.getNewmodel();

				//Permet de Vérifier le contenu du nouvel model 'ModelSparql' crée
				
				//parcours();

				//On récupère le  path du fichier rdf ou owl sélectionné à partir de  la classe OpenFileChooserAction
				String split=action.getpath()[1];

				//On associe le path 'split' au  fichier  'out.rdf dans lequel le model 'ModelSparql' va être générer (en rdf)
				LienFichierRDF=split+"out.rdf";
				//System.out.println("liens----->"+a+" "+"split:"+ LienFichierRDF);

				//création du fichier rdf du graph  de Steiner ' GrapheSteiner'
				//à partir du model  'ModelSparql' si le model a été crée vous devez lire son fichier RDF dans 
				//src/main/java/jena/out.rdf

				//GrapheSteiner.ecrireFileRDF( LienFichierRDF);

				//generation and execution of SPARQL queries				
				String queryString = SPARQL.createQuerySPARQL();
				if(! queryString.equals("ko"))
					SPARQL.executeQuerySPARQL(queryString);
				else{
					JOptionPane.showMessageDialog(f, "Choose a class keyword (Film, Actor, Producer, Film maker)!","Class Required", JOptionPane.ERROR_MESSAGE);
				}					
				
			}//end actionPerformed
		});//end actionListener bt_updateGraph		
	}//end initActions

	public  ProjectFrame(){
		InitMenu();
		InitActions();	
	}	
	
	//parcours du ModelSparql
/*	public void parcours(){

		StmtIterator iter =  ModelSparql.listStatements();
		while (iter.hasNext()) {

			Statement stmt      = iter.nextStatement(); 
			Resource  subject   = stmt.getSubject();     
			Property  predicate = stmt.getPredicate();   
			RDFNode   object    = stmt.getObject();     

			System.out.print(subject.toString());
			System.out.print(" " + predicate.toString() + " ");
			if (object instanceof Resource) {
				System.out.print(object.toString());
			} else {

				System.out.print(" \"" + object.toString() + "\"");
			}
			System.out.println(" .");
		}
	}
*/
} //<------- VERIFIER OU CA OUVRE

class OpenFileChooserAction implements ActionListener{

	private String pass;
	private  File file;
	private  Boolean texteEditor;
	String  pass1;
	JFrame fen;
	public Boolean getTexteEditor(){
		return texteEditor;
	}

	public void SetTexteEditor( Boolean tr){
		texteEditor=tr;
	}

	public String[] getpath(){
		String[] str={pass,pass1};
		return str;
	}

	public OpenFileChooserAction(JFrame f, Boolean texteEditor){
		this. texteEditor= texteEditor=false;
		fen=f;
	}

	public void actionPerformed(ActionEvent e){
		
		URL url_rdfs = this.getClass().getResource("/files");
		
		//System.out.println("-------------------------");
		//System.out.println("pour fichiers");
		//System.out.println(url_rdfs);
		
		texteEditor=true;		
		//String DEFAULT_PATH ="src/main/resources/files";		
		
		//open a directory on a precise directory		
		
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(url_rdfs.getFile())); 

		//filter to show only the given types of file
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers RDF", "rdf", "rdfs", "owl");
		fc.setFileFilter(filter);

		int returnVal = fc.showOpenDialog(fen);
		if (returnVal == JFileChooser.APPROVE_OPTION){
			file = fc.getSelectedFile();	            	            
			// String  filegraphname = file.getPath().toString();

			/*********************************/
			/* applying the indexing method  */
			/*********************************/

			//creation of the Jena's empty file

			pass=file.getPath();
			// FileManager to open the file 
			InputStream in = FileManager.get().open(pass);
			if (in == null) 
				throw new IllegalArgumentException("Fichier: non trouvé");

			pass=file.getPath();
			pass1=file.getPath().replaceAll(file.getName(),"");

			Model model = FileManager.get().loadModel(pass);
			//System.out.println("NOM DU FICHIER :"+pass);
			//creation of the index on the passed model
			try {
				fr.uvsq.project.rdfSearch.App.index = new Indexing(model);
			} 
			catch (Exception ex){			
				ex.printStackTrace();
			}
		}
	}
}// end Class OpenFileChooserAction

class MyMenuBar extends JMenuBar {

	
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private JFrame fenetre;
	
	private URL url_logo;
	private Image imgLogo;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	public MyMenuBar(JFrame f){
		this.fenetre=f;
	}

	protected void paintComponent(Graphics g){

		super.paintComponent(g);
		
		url_logo = this.getClass().getResource("/icones/newuvsq.jpg");
		imgLogo = toolkit.getImage(url_logo);
		
		//System.out.println("-------------------------");
		//System.out.println("url logo"+url_logo);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
		try{
			
			imgLogo = ImageIO.read(url_logo);
			g.drawImage(imgLogo, 500, 2, null);
		}
		catch (Exception e){ e.printStackTrace();}
	}
}//end class MyMenuBar
