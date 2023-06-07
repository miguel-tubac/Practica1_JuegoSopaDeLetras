
package practica_1;
import java.util.*;
import javax.swing.JOptionPane;


public class Practica_1 {
    static String[] palabras;
    static int cantidadPalabras,n;
    static int  contador = 0;
    static String[] nombre = new String[10];
    static String[] seccion = new String[10];
    static String[] carnet = new String[10];
    static char[][] sopa;
    
    static int[] puntos = new int[10];
    static int[] intentos = new int[10];
    static int[] palabrasEncontradas = new int[10];

    public static void main(String[] args) {
       
       menuPrincipal();
    }
//---------------------------------------------------MENU PRINCIPAL-------------------------------------------------     
    public static void menuPrincipal(){
        int opcion =0;
        boolean salirPrincipal = true;
        Scanner entrada = new Scanner(System.in);
       do{
           System.out.print("\n--------------------BIENVENIDO AL JUEGO SOPA DE LETRAS--------------------\n"
                    + "1. Menu Palabras\n"
                    + "2. Jugar\n"
                    + "3. Historial de partidas\n"
                    + "4. Mostrar informacion de estudiante\n"
                    + "5. Salir\n"
                    + "Por favor Ingrese una opcion: ");
           opcion = entrada.nextInt();
           switch (opcion){
               case 1:
                   //datos MenuPalabras
                   menuPalabras();
                   break;
               case 2:
                   //datos jugar
                   comprovarPalabras();
                   ingresarEstudiante();
                   jugar();
                   break;
               case 3:
                   //datos Historial
                   System.out.printf("%n %-20S %-20S %-20S %-20S","NOMBRES","PUNTOS","FALLOS","CANTIDAD_PALABRAS");
                   for(int i=0;i<contador;i++){
                       System.out.printf("%n %-20S %-20d %-20d %-20d",nombre[i],puntos[i],intentos[i],palabrasEncontradas[i]);
                   }
                   System.out.println();
                   break;
               case 4:
                   //mostrar datos del estudiante
                   System.out.printf("%n %-20S %-20S %-20S","NOMBRES","CARNETS","SECCION");
                   for(int i=0;i<contador;i++){
                       System.out.printf("%n %-20S %-20S %-20S",nombre[i],carnet[i],seccion[i]);
                   }
                   System.out.println();
                   break;
               case 5:
                   salirPrincipal = false;
                   break;
               default:
                   System.out.println("EROR POR FAVOR INGRESE UNA OPCION CORRECTA\n");
                   break;
           }
       }while(salirPrincipal);
    }
    
//---------------------------------------------------jugar-------------------------------------------------     
    public static void jugar(){
        Scanner entrada = new Scanner(System.in);
        int[] indicex = new int[n];
        int[] indicey = new int[n];
        sopa = new char[n][n];
        
       
        
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                sopa[i][j]=(char)(int)Math.floor(Math.random()*(122-97)+97);
            }
        }
        for (int contPalabra = 0; contPalabra < cantidadPalabras; contPalabra++) {
            indicex[contPalabra] = (int) Math.floor(Math.random() * (n - 1));
            indicey[contPalabra] = (int) Math.floor(Math.random() * (n - 1));

            // validar que la palabra no salga del tablero
            while (indicex[contPalabra] > n - palabras[contPalabra].length() || indicey[contPalabra] > n - palabras[contPalabra].length() 
                    || indicex[contPalabra]==indicey[contPalabra]) {
                indicex[contPalabra] = (int) Math.floor(Math.random() * (n - 1));
                indicey[contPalabra] = (int) Math.floor(Math.random() * (n - 1));
            }
            
            // ingresar caracteres de la palabra al tablero 
            int inicialX = indicex[contPalabra];
            int inicialY = indicey[contPalabra];
            
            for (int letra1 = 0; letra1 < palabras[contPalabra].length(); letra1++) {
                if(contPalabra%2 !=0){//con esta condicion indico que no se repitan las palabras
                   //--vertical-- 
                   sopa[inicialX+letra1][inicialX] = palabras[contPalabra].charAt(letra1);
                }else{
                    //--horizontales--
                   sopa[inicialY][inicialY+letra1] = palabras[contPalabra].charAt(letra1); 
                }
                
            }        
        }
        boolean jugando = true;
        puntos[contador-1] = 20;
        intentos[contador-1] = 0;
        palabrasEncontradas[contador-1] = cantidadPalabras;
        
        while (jugando) {
            imprimirSopa();
            System.out.print("SI DESEA SALIR INGRESE `&` \nIngresa una palabra de la sopa de letras: ");
            String palabraIngresada = entrada.next();
            boolean encontrado = false;
            // buscamos si existe la palabra
            for (int i = 0; i < cantidadPalabras; i++) {
                if(i%2 != 0 && palabraIngresada.equals(palabras[i])){ 
                    // sume puntos
                    puntos[contador-1] += palabras[i].length();
                    encontrado = true;
                    // tachar palabra en el tablero
                    for (int letra = 0; letra < palabras[i].length(); letra++) {
                        //--vertical--
                        sopa[indicex[i] + letra][indicex[i]] = '#';
                    }   
                }else if(i%2 == 0 && palabraIngresada.equals(palabras[i])){
                    // sume puntos
                    puntos[contador-1] += palabras[i].length();
                    encontrado = true;
                    // tachar palabra en el tablero
                    for (int letra = 0; letra < palabras[i].length(); letra++) {
                        //--horizontal--
                        sopa[indicey[i]][indicey[i] + letra] = '#';
                    }
                }
            }
            
            if (encontrado) {
                //guardarResultado();
                palabrasEncontradas[contador-1]--;
                if(palabrasEncontradas[contador-1]==0){
                  //mensaje de gano 
                  JOptionPane.showMessageDialog(null, "FELICIDADES ENCONTRO TODAS LAS PALABRAS!", "GANADOR!!!!", JOptionPane.INFORMATION_MESSAGE);
                  palabrasEncontradas[contador-1] = cantidadPalabras;
                  jugando = false;  
                }
            } else {
                if(!palabraIngresada.equals("&") && intentos[contador-1] < 2){
                    // quita 5 puntos
                    puntos[contador-1] -= 5;
                    // resta un intento
                    intentos[contador-1]++;
                    JOptionPane.showMessageDialog(null, "LA PALABRA QUE INGRESO NO SE ENCUENTRA", "Error al ingresar", JOptionPane.ERROR_MESSAGE);
                }
                else if (intentos[contador-1] >= 2) {
                    jugando = false;
                    //guardarResultado();
                    puntos[contador-1] -= 5;
                    intentos[contador-1] = 3;
                    palabrasEncontradas[contador-1] = cantidadPalabras;
                    JOptionPane.showMessageDialog(null, "FIN DE LA PARTIDA", "SE TERMINARON LAS VIDAS", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
                else if(palabraIngresada.equals("&")){
                    jugando = false;
                    JOptionPane.showMessageDialog(null, "FIN DE LA PARTIDA", "SE TERMINARON LAS VIDAS", JOptionPane.INFORMATION_MESSAGE);
                }
            }

        }
        
        
 }
    
    
//---------------------------------------------------menu palabras-------------------------------------------------     
    public static void menuPalabras(){
        boolean salirPalabras = true;
        Scanner entrada = new Scanner(System.in);
        int opcion =0;
            
        do{
            System.out.print("\n--------------------Menu Palabras--------------------\n"
                    + "1. Insertar\n"
                    + "2. Modificar\n"
                    + "3. Eliminar\n"
                    + "4. Mostrar Palabras\n"
                    + "5. Volver al menu principal\n"
                    + "Por favor Ingrese una opcion: ");
            opcion = entrada.nextInt();
            switch(opcion){
               case 1:
                   //datos Insertar
                   insertarDatos();
                   break;
               case 2:
                   //datos Modificar
                   comprovarPalabras();
                   modificar();
                   break;
               case 3:
                   //datos Eliminar
                   comprovarPalabras();
                   eliminarPalabra();
                   break;
               case 4:
                   //datos mostrarPalabras
                   comprovarPalabras();
                   mostrarPalabras();
                   break;
               case 5:
                   //datos volver al menu peincipal
                   salirPalabras = false;
                   break;
               default:
                   System.out.println("EROR POR FAVOR INGRESE UNA OPCION CORRECTA\n");
                   break;
            }
        }while(salirPalabras);  
    }
    
 //---------------------------------------------------insertar datos-------------------------------------------------        
    public static void insertarDatos(){
        Scanner entrada=new Scanner(System.in);
        System.out.print("\nIngrese la cantidad de palabras que decee: ");
        cantidadPalabras = entrada.nextInt();
        palabras = new String[cantidadPalabras];
        System.out.println("Ingrese las palabras: ");
        
        for(int i=0;i<cantidadPalabras;i++){
            System.out.print((i+1)+". ");
            String nuevaPalabra = entrada.next();
            
            if(nuevaPalabra.length()<5 || nuevaPalabra.length()>10){
               JOptionPane.showMessageDialog(null, "La palabra deve de tener entre 5 y 10 caracteres", "Error al ingresar", JOptionPane.ERROR_MESSAGE);
               i--;
            }else { 
               palabras[i]= nuevaPalabra;
            }
        }
    }
//---------------------------------------------------modificar palabras-------------------------------------------------         
    public static void modificar(){
        Scanner entrada = new Scanner(System.in);
        System.out.print("\nPalabras Ingresadas: \n");
        mostrarPalabras();
        System.out.print("Ingrese el numero de la palabra que dece modificar: ");
        int numPalabra = entrada.nextInt();
        numPalabra--;

        System.out.print("Ingrese la nueva palabra: ");
        String nuevaPalabra = entrada.next();

        palabras[numPalabra] = nuevaPalabra;
    }
    
//---------------------------------------------------eliminar palabra-------------------------------------------------     
    public static void eliminarPalabra(){
        Scanner entrada = new Scanner(System.in);
        System.out.print("\nPalabras Ingresadas: \n");
        mostrarPalabras();
        System.out.print("Ingrese el numero de la palabra que dece eliminar: ");
        int numPalabra = entrada.nextInt();
        numPalabra--;

        for (int i = numPalabra; i < cantidadPalabras - 1; i++) {
            palabras[i] = palabras[i + 1];
        }
        cantidadPalabras--;
    }
    
//---------------------------------------------------mostrar palabras-------------------------------------------------         
    public static void mostrarPalabras() {
        System.out.println();
        for (int i = 0; i < cantidadPalabras; i++) {
            System.out.println((i + 1) + ". " + palabras[i]);
        }
        System.out.println();
    }
//-------------------------------------------comprovar que hay palabras-------------------------------------------------         
    public static void comprovarPalabras(){
        if(cantidadPalabras == 0){
            JOptionPane.showMessageDialog(null, "DEVE DE INGRESAR LAS PALABRAS", "Error al ingresar", JOptionPane.ERROR_MESSAGE);
            insertarDatos();
        }
    }
//-------------------------------------------ingresar estudiante-------------------------------------------------         
    public static void ingresarEstudiante(){
        
        boolean princi = true;
        if(contador<10){
            Scanner entrada = new Scanner(System.in);
            System.out.print("\nINGRESE SU NOMBRE: ");
            nombre[contador] = entrada.nextLine();
            System.out.print("INGRESE SU SECCION: ");
            seccion[contador] = entrada.next();
            System.out.print("INGRESE SU CARNET: ");
            carnet[contador] = entrada.next();
           do{
               System.out.print("INGRESE EL NUMERO DEL CUADRADO DEL TABLERO MAYOR A 10: ");
               n = entrada.nextInt();
               if(n>10){
                   princi = false;
                   contador++;
               }else{
                  System.out.println("INGRESE DATOS NUMERICOS MAYORES A 10"); 
               }
           }while(princi);
        }else{
          JOptionPane.showMessageDialog(null, "LLEGO AL LIMITE DE USUARIOS", "Error al ingresar", JOptionPane.ERROR_MESSAGE);  
        }
    }
    
//---------------------------------------------------imprimir-------------------------------------------------     
    public static void imprimirSopa(){
        System.out.println();
        //sopa = new char[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                System.out.print("|"+sopa[i][j]+"|");
            }
            System.out.println();
        }
    }
    
}
