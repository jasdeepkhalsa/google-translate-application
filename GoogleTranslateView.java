/*
 * GoogleTranslateView.java
 */

package googletranslate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;

//import com.google.api.translate.Language;
//import com.google.api.translate.Translate;
import java.awt.Font;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;


/**rever And Ever True. ||1||
 * The application's main frame.
 */
public class GoogleTranslateView extends FrameView implements ActionListener,
                                        PropertyChangeListener {

    Language inputLanguage = Language.valueOf("ENGLISH") ;
    Language outputLanguage = Language.valueOf("ENGLISH") ;
     private Task task;
     String theText = null ;
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
private static Charset getEncoding(String filePath) {


        Charset guessedCharset = null ;
        try {
            guessedCharset = com.glaforge.i18n.io.CharsetToolkit.guessEncoding(new File(filePath), 100);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GoogleTranslateView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GoogleTranslateView.class.getName()).log(Level.SEVERE, null, ex);
        }
                 System.out.println("guess = "+ guessedCharset) ;
		return guessedCharset;
	}
    class Task extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            try {
                String inputText = jTextAreaInput.getDocument().getText(0, jTextAreaInput.getDocument().getLength());
             //  for(int g = 0 ; g < inputText .length() ; g++){
               //     System.out.println(" "+inputText .charAt(g)+" : "+(int)inputText .charAt(g)) ;
                //}
                for(int i = 0 ; i < 10 ; i++){
                    inputText = inputText.replace("\n\n", "\na\n");
                }
                StringTokenizer st = new StringTokenizer(inputText, "\n");
                StringBuilder translatedText = new StringBuilder();
                int lines = st.countTokens();
                jLabelLines.setText(""+lines+" lines") ;
                //progressBar.setMaximum(lines);
             //   System.out.println("maximum = "+progressBar.getMaximum()) ;
                progressBar.setStringPainted(true);
                jButtonTranslate.setText("Cancel");
                int i = 1;
                boolean add = true ;
                int j = 0 ;
                int translatedLinesCout = 0 ;
                int linesTime = 17;
                setProgress(0);
                String lineBefore ;
                String line ;
                StringBuilder batch ;
                String tok = null ;
                List<Integer> linesWithNumbers = new ArrayList<Integer>() ;
                Map<Integer,String> endingLines = new HashMap<Integer,String>() ;
                while(st.hasMoreTokens()|| (tok != null)){
                  //  System.out.println("gorny while") ;
                    batch = new StringBuilder() ;
                    j = 0 ;


                    while (st.hasMoreTokens()|| (tok != null) ) {
                      //  System.out.println("j = "+j) ;
                        if (j>linesTime) break ;
                       
                        if(tok == null ){
                            tok = st.nextToken() ;
                           //  System.out.println("null tok = "+tok);
                        }else{
                            String tmp = new String(tok+" . qwerty. "+System.getProperty("line.separator")) ;
                            //System.out.println("else tmp = "+tmp);
                            if(st.hasMoreTokens()){
                                tok = tmp.concat(st.nextToken()) ;
                            }else {
                                tok = new String(tmp) ;
                            }
                            j++ ;
                           
                      //    System.out.println("else tok = "+tok);
                        }
                        if((batch.length()+tok.length())>1500){
                        //    System.out.println("break = st.hasMoreTokens() = " +st.hasMoreTokens());
                            break ;
                            //continue ;
                        }
                      //  System.out.println("tok = "+tok);
                     /*   if((outputLanguage == Language.HEBREW)||(outputLanguage == Language.PERSIAN)){
                            int ind = tok.indexOf("||") ;
                            if(ind != -1){
                                linesWithNumbers.add(j) ;
                                String sub = new String("||x"+tok.substring(ind+2));
                                tok = tok.substring(0, ind-1) ;
                                endingLines.put(j, sub) ;
                                System.out.println("sub = "+sub + " j = "+j) ;
                            }

                        }*/
                        if((outputLanguage == Language.PERSIAN)|| (outputLanguage == Language.TURKISH) ||(outputLanguage == Language.KOREAN)|| (outputLanguage == Language.JAPANESE)|| (outputLanguage == Language.HINDI)){
                            if(tok.contains(" washed")){
                                tok = tok.replace(" washed", "") ;
                                System.out.println("tok = "+tok);
//                                  add = false ;
                            }
                            if(tok.contains("others,")){
                                tok = tok.replace("others,", "others") ;
                            }
                            if(tok.endsWith("one,")){
                                tok = tok.replace("one,", "on") ;
                            }
                             if(tok.endsWith("one")){
                                tok = tok.replace("one", "on") ;


                            }
//                            if(tok.endsWith(",")){
//                                tok = tok.substring(0, tok.length() - 1) ;
//                            }
//                            if(tok.endsWith(";")){
//                                tok = tok.substring(0, tok.length() - 1) ;
//                            }
                            tok = tok.replace("\"\"\"\"", "\"") ;
                            tok = tok.replace("\"\"\"", "\"") ;
                            tok = tok.replace("\"\"", "\"") ;
                           
                            tok = tok.replace("attaches", "") ;
                            tok = tok.replace("Fruitful is the coming", "") ;
                     //   }
                      //  if(outputLanguage == Language.TURKISH){
                            tok = tok.replace("sins,", "") ;
                            tok = tok.replace("his filth would be removed, his soul would be cleansed, and he would be liberated when he departs. ||2||","Please make Your Own ||2||");
                            tok = tok.replace("loud,", "loud");//your God
                            tok = tok.replace("Lord,", "Lord");//your God
                            tok = tok.replace("to your God", "God");
                            tok = tok.replace("Those,", "they");
                            tok = tok.replace("that", "");
                            tok = tok.replace("humble", "");
                            tok = tok.replace("to His", "to");// Please make Nanak Your Own.
                            tok = tok.replace("Please make Nanak Your Own.", "Please make Your Own.");
                            tok = tok.replace("recognizes God", "God") ;
                            tok = tok.replace("advice,", "advice") ;
                            tok = tok.replace("Embellish ,", "") ;
                            tok = tok.replace("of that humble", "") ;
                            tok = tok.replace("in many", "many") ;
                            tok = tok.replace("\"\"\"\"", "\"") ;
                            tok = tok.replace("\"\"\"", "\"") ;
                            tok = tok.replace("\"\"", "\"") ;
                            if(tok.endsWith(",")){
                                tok = tok.substring(0, tok.length() - 1) ;
                            }
                            if(tok.endsWith(";")){
                                tok = tok.substring(0, tok.length() - 1) ;
                            }

                    }
                        batch.append(tok) ;
                        batch.append(" . qwerty. ") ;
                      //  batch.append(System.getProperty("line.separator"));
                        tok= null ;
                        j++;
        
                    }

                 // System.out.println("batch length = "+batch.length()) ;

                    // System.out.println("lines = "+(j+1));
                   //System.out.println("batch = "+batch.toString()) ;
                    int count = 0 ;
                    int err = -1 ;
                    while(err!=0){
                    try {

                        line = Translate.translate(batch.toString(), inputLanguage, outputLanguage);
                     //   System.out.println("line = "+line) ;
                        //line = Translate.translate(batch.toString(), Language.ENGLISH, Language.FRENCH);
//                        for(int e = 0 ; e < line.length() ; e++){
//                            System.out.println(""+line.charAt(e) + " - "+ (int)line.charAt(e) );
//                        }
                        line = line.replace("\n", " ") ;
                        line = line.replace(". &quot;Qwerty&quot;.", ". Qwerty.") ;
                        line = line.replace(". &quot;qwerty&quot;.", ". Qwerty.") ;
                        line = line.replace(". &quot;qwerty&quot;.", ". Qwerty.") ;
                        line = line.replace(". &quot;qwerty.", ". Qwerty.") ;
                        line = line.replace(". azerty.",". Qwerty." ) ;
                        line = line.replace(". Azerty.",". Qwerty." ) ;
                        line = line.replace(".Azerty.",". Qwerty." ) ;
                        line = line.replace(".azerty.",". Qwerty." ) ;
                        line = line.replace(". qzertz.",". Qwerty." ) ;
                        line = line.replace(". Qzertz.",". Qwerty." ) ;
                        line = line.replace(".Qzertz.",". Qwerty." ) ;
                        line = line.replace(".qzertz.",". Qwerty." ) ;//
                        line = line.replace(". qwertz.",". Qwerty." ) ;
                        line = line.replace(". Qwertz.",". Qwerty." ) ;
                        line = line.replace(".Qwertz.",". Qwerty." ) ;
                        line = line.replace(".qwertz.",". Qwerty." ) ;//
                        line = line.replace(". Azuchan.",". Qwerty." ) ;
                        line = line.replace(". azuchan.",". Qwerty." ) ;
                        line = line.replace(".Azuchan.",". Qwerty." ) ;
                        line = line.replace(".azuchan.",". Qwerty." ) ;//. Azuchan ".
                        line = line.replace(". Azuchan \".",". Qwerty." ) ;
                        line = line.replace(". azuchan \".",". Qwerty." ) ;
                        line = line.replace(".Azuchan \".",". Qwerty." ) ;
                        line = line.replace(".azuchan \".",". Qwerty." ) ;//شسته شده است.
                        line = line.replace("azuchan",". Qwerty." ) ;
                        line = line.replace("Azuchan",". Qwerty." ) ;
                        line = line.replace("شسته شده است.",". Qwerty." ) ;
                        //StringTokenizer st2 = new StringTokenizer(line, "@");
                        //»
                       // System.out.println("\nline = "+line + " length = "+line.length()+" l[0] = "+(int)line.charAt(0)) ;
                        String[] st2 = null ;
                        if(outputLanguage == Language.JAPANESE){
                            st2 = line.split("(?i)仕様") ;
                        }else if(outputLanguage == Language.KOREAN){
                            
                            st2 = line.split("(?i)쿼티") ;
                         //   System.out.println("korean enter st2.l = "+st2.length) ;
                        }else if(outputLanguage == Language.SERBIAN){
                            st2 = line.split("(?i). Куерты.") ;
                        }else{
                            st2 = line.split("(?i). ?qwerty(键盘)?.") ;
                        }

                        if(st2!=null)count = st2.length ;
                       // System.out.println("length = " +line.length()) ;
                       // System.out.println("tokens = "+count) ;
                       // System.out.println("batch = "+line.toString()) ;
                        int x = 0 ;
                        //Language.PORTUGUESE
        
                        while(x<count ){
                            String line2 = st2[x] ;
                           
                            x++ ;
                         //   System.out.println("line2 = "+line2 + " length = "+line2.length()+" l[0] = "+(int)line2.charAt(0)) ;
                         //   for(int g = 0 ; g < line2.length() ; g++){
                          //      System.out.print(" "+line2.charAt(g)+" : "+(int)line2.charAt(g)) ;
//
                            //}
                         


                            line2 = line2.replace("&quot;","\"") ;
                            line2 = line2.replace("A\n","\n\n") ;
                            if(line2.equals("。")){
                                //System.out.println("spacja") ;
                                break ;
                            }
                            if(line2.equals("盤。")){
                                //System.out.println("spacja") ;
                                break ;
                            }
                           
                            //String line3 = null;
                            if(!line2.isEmpty()){
                                if(line2.charAt(0) == ' '){
                                   // System.out.println("pierwsza spacja") ;
                                    line2 = line2.substring(1) ;
                                   // System.out.println("sub  = "+line2) ;
                                }
                            }
                            if(line2.equals("A")){
                              
                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase(". kiểu")){
                                translatedLinesCout-- ;
                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase(". Kiểu")){
                                translatedLinesCout-- ;
                                line2 = "" ;
                            }//شسته شده است.
                            if(outputLanguage == Language.VIETNAMESE){
                                line2 = line2.replace(". kiểu", "") ;
                                line2 = line2.replace(". Kiểu", "") ;
                            }
                             if(outputLanguage == Language.PERSIAN){
                                 System.out.println("pers") ;
                                //line2 = line2.replace("شسته شده است.", "") ;
                                if(line2.equalsIgnoreCase("شسته شده است.")){
                                    line2 = "" ;
                                    add = false ;
                                }
                                if(line2.equalsIgnoreCase("")){
                                    line2 = "" ;//being washed
                                    add = false ;
                                }
                                if(line2.contains(" washed")){
                                     line2 = line2.replace(" washed", "") ;

                                     System.out.println("line2 = "+line2);
                                    add = false ;
                                }
                               
                                
                              //  line2 = line2.replace(". Kiểu", "") ;
                            }
                            if(outputLanguage == Language.CHINESE_TRADITIONAL){
                                line2 = line2.replace("盤。", "") ;
                              //  line2 = line2.replace(". Kiểu", "") ;
                            }
                            if(line2.equalsIgnoreCase("أ")){
                               
                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("Una")){
                                
                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("1 ")){
                                
                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("Een")){
                              
                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("Isang")){

                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("Unha")){

                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("Eine")){

                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("א")){

                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("एक")){

                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("Yang")){

                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("Uno")){

                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("ی")){

                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase(".. ب")){

                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("Uma")){

                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("O")){

                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("А")){

                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("a")){

                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("o")){

                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("uma")){

                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("Một. Kiểu ")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase("một. kiểu ")){

                                line2 = "" ;
                            }
                              if(line2.equalsIgnoreCase("bir")){

                                line2 = "" ;
                            }
                              if(line2.equalsIgnoreCase("tarafından. bir")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase("Bir")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase("ที่")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase("En")){

                                line2 = "" ;
                            }if(line2.equalsIgnoreCase("en")){

                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("una")){

                                line2 = "" ;
                            }
                            if(line2.equalsIgnoreCase("uno")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase("yang")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase("А. куерты. ")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase("Pliku")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase("eine")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase("Dunha")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase("isang")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase("файл")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase("een   ")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase("द्वारा. एक")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase("pliku")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase("로.. ")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase("では。")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase("ک")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase("a")){

                                line2 = "" ;
                            } if(line2.equalsIgnoreCase("Một. Kiểu")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase(".. ")){

                                line2 = "" ;
                            }
                             if(line2.equalsIgnoreCase("。 。")){

                                line2 = "" ;
                            } if(line2.equalsIgnoreCase("盤。 1 ")){

                                line2 = "" ;
                            }
                            if(line2.equals(".")){
                                break ;
                            }
                        /*    if(linesWithNumbers.contains(translatedLinesCout)){
                                String end = endingLines.get(translatedLinesCout) ;
                                line2 = new String(line2 + " " + end) ;
                            }*/
                           // translatedText.append(1) ;
                            if(add){
                                translatedText.append(line2);
                                translatedText.append(System.getProperty("line.separator"));
                                translatedLinesCout++ ;
                            }else{
                                add = true ;
                            }

                         //   translatedText.append(1) ;

                           
                        }

                        int prog = (i* 100 / lines);//*linesTime;
                       // if(prog>100) prog = 100 ;
                     //   System.out.println("prog = "+prog+" i = "+i+" lines = "+lines) ;
                        setProgress(prog) ;
                        i+=j;
                        err= 0 ;
                    } catch (Exception ex) {
                        Logger.getLogger(GoogleTranslateView.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("error batch = "+batch.toString()) ;
                        System.out.println("error length = " +batch.length()) ;
                       // err++;
                        System.out.println("err = "+err) ;
                    }
                    }
                }

                System.out.println("output text length = "+translatedText.length()) ;
                String[] outLines = translatedText.toString().split("\n") ;
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel() ;
                model.setRowCount(0);
                for(int k = 0 ; k < outLines.length ; k++){
                    model.addRow(new Object[]{outLines[k]});
                }
                theText = new String(translatedText.toString()) ;
            //    jTextAreaOutput.getDocument().remove(0, jTextAreaOutput.getDocument().getLength());
             //   jTextAreaOutput.getDocument().insertString(0, translatedText.toString(), null);


                jLabelLines.setText(jLabelLines.getText()+ " /  "+translatedLinesCout+" lines translated");
            } catch (BadLocationException ex) {
                Logger.getLogger(GoogleTranslateView.class.getName()).log(Level.SEVERE, null, ex);
            }
           return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
             jButtonTranslate.setText("Translate");
             progressBar.setValue(0);

            //Toolkit.getDefaultToolkit().beep();
//            setCursor(null); //turn off the wait cursor
//            taskOutput.append("Done!\n");
        }
    }

    public GoogleTranslateView(SingleFrameApplication app) {
        super(app);

        initComponents();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("");
        model.setNumRows(100) ;
       //model.
        jTable1.setModel(model);
        ReadConfig readConf = new ReadConfig() ;
        ///home/koper/NetBeansProjects/GoogleTranslate/src/googletranslate/
        readConf.readXml("config.xml");
        //Language.CHINESE_TRADITIONAL
        Config conf = readConf.getConfig() ;
        jComboBoxInput.setModel(new javax.swing.DefaultComboBoxModel(conf.languages.toArray()));
        jComboBoxOutput.setModel(new javax.swing.DefaultComboBoxModel(conf.languages.toArray()));



        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
//                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
       // statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(true);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                       // statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                   // statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                  //  statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
      
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = GoogleTranslateApp.getApplication().getMainFrame();
            aboutBox = new GoogleTranslateAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        GoogleTranslateApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        mainPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jComboBoxInput = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaInput = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jButtonSelectInput = new javax.swing.JButton();
        jButtonPaste = new javax.swing.JButton();
        jButtonImport = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxOutput = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        jButtonSelectOutput = new javax.swing.JButton();
        jButtonCopy = new javax.swing.JButton();
        jButtonExport = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButtonTranslate = new javax.swing.JButton();
        statusPanel = new javax.swing.JPanel();
        progressBar = new javax.swing.JProgressBar();
        jLabelLines = new javax.swing.JLabel();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setLayout(new javax.swing.BoxLayout(mainPanel, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jComboBoxInput.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxInputActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel4.add(jComboBoxInput, gridBagConstraints);

        jLabel1.setText("Input Language");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
        jPanel4.add(jLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel4, gridBagConstraints);

        jTextAreaInput.setColumns(20);
        jTextAreaInput.setFont(new java.awt.Font("Code2000", 0, 14));
        jTextAreaInput.setRows(23);
        jScrollPane1.setViewportView(jTextAreaInput);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 8.0;
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jButtonSelectInput.setText("Select All");
        jButtonSelectInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectInputActionPerformed(evt);
            }
        });
        jPanel5.add(jButtonSelectInput, new java.awt.GridBagConstraints());

        jButtonPaste.setText("Paste");
        jButtonPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPasteActionPerformed(evt);
            }
        });
        jPanel5.add(jButtonPaste, new java.awt.GridBagConstraints());

        jButtonImport.setText("Import");
        jButtonImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImportActionPerformed(evt);
            }
        });
        jPanel5.add(jButtonImport, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel5, gridBagConstraints);

        mainPanel.add(jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jPanel6.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Output language");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
        jPanel6.add(jLabel2, gridBagConstraints);

        jComboBoxOutput.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxOutputActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel6.add(jComboBoxOutput, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jPanel6, gridBagConstraints);

        jPanel7.setLayout(new java.awt.GridBagLayout());

        jButtonSelectOutput.setText("Select All");
        jButtonSelectOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectOutputActionPerformed(evt);
            }
        });
        jPanel7.add(jButtonSelectOutput, new java.awt.GridBagConstraints());

        jButtonCopy.setText("Copy");
        jButtonCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCopyActionPerformed(evt);
            }
        });
        jPanel7.add(jButtonCopy, new java.awt.GridBagConstraints());

        jButtonExport.setText("Export");
        jButtonExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExportActionPerformed(evt);
            }
        });
        jPanel7.add(jButtonExport, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jPanel7, gridBagConstraints);

        jScrollPane3.setPreferredSize(new java.awt.Dimension(218, 403));

        jTable1.setFont(new java.awt.Font("Code2000", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Title 1"
            }
        ));
        jTable1.setIntercellSpacing(new java.awt.Dimension(0, 0));
        jTable1.setOpaque(false);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane3.setViewportView(jTable1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 8.0;
        jPanel2.add(jScrollPane3, gridBagConstraints);

        mainPanel.add(jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButtonTranslate.setText("Translate");
        jButtonTranslate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTranslateActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonTranslate);

        mainPanel.add(jPanel3);

        statusPanel.setMinimumSize(new java.awt.Dimension(20, 50));
        statusPanel.setName("statusPanel"); // NOI18N
        statusPanel.setPreferredSize(new java.awt.Dimension(148, 20));

        progressBar.setMinimumSize(new java.awt.Dimension(20, 20));
        progressBar.setPreferredSize(new java.awt.Dimension(200, 20));

        org.jdesktop.layout.GroupLayout statusPanelLayout = new org.jdesktop.layout.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jLabelLines, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 271, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 164, Short.MAX_VALUE)
                .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelLayout.createSequentialGroup()
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jLabelLines, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        setComponent(mainPanel);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonTranslateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTranslateActionPerformed
        if(jButtonTranslate.getText() == "Cancel"){
            System.out.println("Cancel") ;
            task.cancel(true);
            task.removePropertyChangeListener(this);
            return ;
        }
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel() ;
        model.setRowCount(0);
        System.out.println("inputLanguage = "+inputLanguage+"  outputLanguage = "+outputLanguage) ;
        try {

            task = new Task();
            task.addPropertyChangeListener(this);
            task.execute();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButtonTranslateActionPerformed
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        }
    }
    private void jButtonImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImportActionPerformed

        final JFileChooser fc = new JFileChooser();
       ExampleFileFilter filter = new ExampleFileFilter("txt");
       filter.setDescription("text files");
       fc.addChoosableFileFilter(filter);
       fc.setAcceptAllFileFilterUsed(true) ;
       fc.setFileFilter(filter) ;

        int returnVal = fc.showOpenDialog(mainPanel);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                jTextAreaInput.getDocument().remove(0, jTextAreaInput.getDocument().getLength());
            } catch (BadLocationException ex) {
                Logger.getLogger(GoogleTranslateView.class.getName()).log(Level.SEVERE, null, ex);
            }
            File file = fc.getSelectedFile();
            try {
                Charset charset = getEncoding(file.getAbsolutePath()) ;
                System.out.println("charset = " + charset) ;
                BufferedReader inputFile = new BufferedReader(new InputStreamReader
                              (new FileInputStream(file),charset));
                String line ;
                StringBuilder contents = new StringBuilder();
                long i = 0 ;
                while (( line = inputFile.readLine()) != null){
                   // System.out.println(i+"  "+line) ;
                    //String newString = new String(line.getBytes("UTF-8"), "UTF-8");

                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                    i++ ;
                }
            //    jLabelLines.setText(""+i+" lines") ;
                inputFile.close() ;
              //  System.out.println("con: "+contents.toString())    ;
                Document doc = new DefaultStyledDocument();
//                doc.
               // doc.insertString( 0, contents.toString() , null);
               // jTextAreaInput.getDocument().remove(0, jTextAreaInput.getDocument().getLength());
                //jTextAreaInput.setDocument(doc);
           //     jTextAreaInput.
              //  jTextAreaInput.insert(contents.toString(), 0) ;
                jTextAreaInput.getDocument().insertString(0, contents.toString(), null);
                contents.delete(0, contents.length()) ;
          //      jTextAreaInput.read(inputFile, doc);

        } catch (BadLocationException ex) {
            Logger.getLogger(GoogleTranslateView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GoogleTranslateView.class.getName()).log(Level.SEVERE, null, ex);

        }catch (IOException ex) {
                Logger.getLogger(GoogleTranslateView.class.getName()).log(Level.SEVERE, null, ex);
        }

        } else {

        }


    }//GEN-LAST:event_jButtonImportActionPerformed

    private void jComboBoxInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxInputActionPerformed
        String selected = (String) jComboBoxInput.getSelectedItem() ;
        inputLanguage = Language.valueOf(selected.toUpperCase()) ;
      
    }//GEN-LAST:event_jComboBoxInputActionPerformed

    private void jComboBoxOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxOutputActionPerformed
        String selected = (String) jComboBoxOutput.getSelectedItem() ;
        outputLanguage = Language.valueOf(selected.toUpperCase()) ;
    }//GEN-LAST:event_jComboBoxOutputActionPerformed

    private void jButtonExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportActionPerformed
            final JFileChooser fc = new JFileChooser();
                   ExampleFileFilter filter = new ExampleFileFilter("txt");
       filter.setDescription("text files");
       fc.addChoosableFileFilter(filter);
       fc.setAcceptAllFileFilterUsed(true) ;
       fc.setFileFilter(filter) ;

//In response to a button click:
        int returnVal = fc.showSaveDialog(mainPanel);


        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if(!file.getName().contains(".txt")){
                file = new File(file.getPath()+".txt") ;
            }
            try {
                BufferedWriter outputFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
                String text = theText ;
                outputFile.write(text, 0, text.length());
                outputFile.close(); 
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(GoogleTranslateView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                    Logger.getLogger(GoogleTranslateView.class.getName()).log(Level.SEVERE, null, ex);
            }catch (IOException ex) {
                Logger.getLogger(GoogleTranslateView.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }


 
    }//GEN-LAST:event_jButtonExportActionPerformed

    private void jButtonPasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPasteActionPerformed
        jTextAreaInput.paste();
    }//GEN-LAST:event_jButtonPasteActionPerformed

    private void jButtonCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCopyActionPerformed
//       jTextAreaOutput.copy() ;
        java.awt.datatransfer.StringSelection tekst=new java.awt.datatransfer.StringSelection(theText);
        java.awt.datatransfer.Clipboard schowek=java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
        schowek.setContents(tekst, tekst);
    }//GEN-LAST:event_jButtonCopyActionPerformed

    private void jButtonSelectInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectInputActionPerformed
        jTextAreaInput.selectAll(); 
    }//GEN-LAST:event_jButtonSelectInputActionPerformed

    private void jButtonSelectOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectOutputActionPerformed
  //      jTextAreaOutput.selectAll();
        jTable1.selectAll(); ;
    }//GEN-LAST:event_jButtonSelectOutputActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCopy;
    private javax.swing.JButton jButtonExport;
    private javax.swing.JButton jButtonImport;
    private javax.swing.JButton jButtonPaste;
    private javax.swing.JButton jButtonSelectInput;
    private javax.swing.JButton jButtonSelectOutput;
    private javax.swing.JButton jButtonTranslate;
    private javax.swing.JComboBox jComboBoxInput;
    private javax.swing.JComboBox jComboBoxOutput;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelLines;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextAreaInput;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
