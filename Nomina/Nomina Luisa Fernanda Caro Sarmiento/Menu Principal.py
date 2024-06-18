#hola soy la nomina
import os 
import Funcionesdenomina as n
from time import sleep

#menu de entrada 
Control = True
while Control: 
    n.Verificador('LaNomina.Txt')
    
    os.system('cls')
    
    print ("_______________________________𓆩*𓆪_________________________________")
    print ("\n--Transportes del Gran Caribe Colombiano S.C.A-TransCaribeCol--\n")
    print ("             -.M e n u   d e   e n t r a d a.-                 ")
    print ("\n___________________________________________________________________\n")
    
    print ("\tOPCIONES:  \n")
    print ("\t1- Ingresar datos del Empleado")
    print ("\t2- Abandonar➜\n")
    print ("___________________________________________________________________")
#seleccion de opciones
    opc = int(input('Escoja la opción: '))

    if opc==1:
        
        os.system('cls')
        print ("_______________________________𓆩*𓆪________________________________")
        print ("\n--Transportes del Gran Caribe Colombiano S.C.A-TransCaribeCol--\n")
        print ("\n_________________________________________________________________\n")
        print ("SELECIONE TIPO DE EMPLEADO: ")
        print ("1- ADMINISTRATIVO")
        print ("2- OPERATIVO")
        print ("3- REGRESAR AL MENÚ")
        print ("________________________________________________________________")
        opc2 = int(input('Escoja la opción: '))
            
        if opc2==1:
            Empleado= n.Administativo ()
            with open ('LaNomina.Txt','a') as M: 
                    M.write (Empleado)

        elif opc2==2:
            Empleado= n.Operativo()
            with open ('LaNomina.Txt','a') as M: 
                 M.write (Empleado)
                    
        elif opc2==3:
            sleep(0)
            
        else:
            os.system('cls')
            print ("\n¡HA OCURRIDO UN ERROR! POR FAVOR INTENTE DE NUEVO :(\n")
            input('Presione <ENTER> para continuar')
            
    elif opc ==2: 
        os.system('cls')
        print ("¡USTED HA ABANDONADO EL PROGRAMA!>>>")
        break 
    
    else:
        os.system('cls')
        print ("\n¡HA OCURRIDO UN ERROR! POR FAVOR INTENTE DE NUEVO :(\n")
        input('Presione <ENTER> para continuar')