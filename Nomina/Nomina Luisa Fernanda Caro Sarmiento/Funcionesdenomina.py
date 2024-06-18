import os 

def Verificador (M):
    if os.path.exists (M):
        with open (M, 'a+') as M:
            M.seek (0)
            datito= M.read ()
            if not datito:
                cabeza= "_____________________________________________________________________________________________________________________________________________________________________________________________________\n{:<40}| {:<31}| {:<5}| {:<16}| {:<5}| {:<17}| {:<16}| {:<16}| {:<17}| {:<2}\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n".format("NOMBRE", "CARGO", "HT", "SALARIO", "HE","TPHE", "SALUD", "Pensión", "ARL", "TOTAL A PAGAR")
                M.write(cabeza)

#Operativo 
def Operativo ():
    os.system('cls')
    Nom=input("Ingrese el nombre del Empleado: ")
    Car=input("Ingrese el cargo: ")
    os.system('cls')
    
    salud=0.125
    pension=0.16
    horasextras= 0
    salarioporextra= 0
    Car= Car.lower().title() 
    
    if 'Conductor' in Car:
        horastrabajadas=160
        Salariobruto= (horastrabajadas*40000)
        ibc= Salariobruto*0.4
        arl= 0.01044
        sal= ibc*salud
        pen= ibc*pension
        ries=ibc*arl
        totaldedescuento=(sal+pen+ries)
        totalapagar=(Salariobruto-totaldedescuento)
        
    elif 'Oficios Generales' in Car: 
        horastrabajadas=100
        Salariobruto= (horastrabajadas*40000)
        ibc= Salariobruto*0.4
        arl= 0.00522
        sal= ibc*salud
        pen= ibc*pension
        ries=ibc*arl
        totaldedescuento=(sal+pen+ries)
        totalapagar=(Salariobruto-totaldedescuento)
        
        
    elif 'Vigilancia' in Car:
        horastrabajadas=336
        Salariobruto= (horastrabajadas*40000)
        ibc= Salariobruto*0.4
        arl= 0.0435
        sal= ibc*salud
        pen= ibc*pension
        ries=ibc*arl
        totaldedescuento=(sal+pen+ries)
        totalapagar=(Salariobruto-totaldedescuento)
        
    else:
        os.system('cls')
        print ("\tCargo no existente. Saliendo del programa...")
        
    Salariobruto= "${:,.0f}".format(Salariobruto)
    sal="${:,.0f}".format(sal)
    pen="${:,.0f}".format(pen)
    ries="${:,.0f}".format(ries)
    totaldedescuento="${:,.0f}".format(totaldedescuento)
    totalapagar="${:,.0f}".format(totalapagar)
    
    print ("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤")
    print ("¤¤¤¤¤¤¤¤¤¤¤¤ V O L A N T E  D E P A G O ¤¤¤¤¤¤¤¤¤¤¤")
    print ("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤\n")
    print ("Nombre: ",Nom)
    print ("Cargo: ",Car)
    print ("Horas Trabajadas: ",horastrabajadas)
    print ("Salario Bruto: ",Salariobruto)
    print ("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤")
    print ("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤ DESCUENTOS DE LEY ¤¤¤¤¤¤¤¤¤¤¤¤¤¤")
    print ("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤\n")
    print ("Salud (12.5%): ",sal)
    print ("Pensión (16%): ",pen)
    print ("ARL (",arl,"%): ",ries)
    print ("\nTotal de descuentos: ",totaldedescuento)
    print ("\nTotal a pagar: ",totalapagar)
    print ("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤")
    print ("¤¤¤¤¤¤¤¤¤¤¤¤ FIN  DEL VOLANTE DE PAGO ¤¤¤¤¤¤¤¤¤¤¤¤¤")
    print ("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤\n")
    print ()
    input ("¡Pulse <ENTER> para salir!")
    
    Impresora= "{:<40}| {:<31}| {:<5}| {:<16}| {:<5}| {:<17}| {:<16}| {:<16}| {:<17}| {:<2}\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n".format(
        Nom, Car, horastrabajadas, Salariobruto, horasextras, salarioporextra, sal, pen, ries, totalapagar
        )
    return Impresora
    
#Administrativo 
def Administativo ():
    os.system('cls')
    Nom=input("Ingrese el nombre del Empleado: ")
    Car=input("Ingrese el cargo: ")
    horastrabajadas= int(input("Ingrese las horas trabajadas (mes): "))
    horasextras= int(input("Ingrese las horas extras trabajadas: "))
    
    os.system('cls')
    Car= Car.lower().title() 
    
    if 'Administrativo' in Car:
        salariobruto=(horastrabajadas*20000)
        if (horasextras>0):
            salarioporextra= (horasextras*25000)
        else: 
            salarioporextra= 0
        
        totalinvisible=(salariobruto+salarioporextra)
            
        parasalud=(4/100)*totalinvisible
        parapension=(4/100)*totalinvisible
        parariesgo=(0.522/100)*totalinvisible
        
        totaldescuentos= (parapension+parasalud+parariesgo)
        
        totalapagar=(totalinvisible-totaldescuentos)
    else:
        os.system('cls')
        print ("")
        print ("\tCargo no existente.")
        input ("¡Pulse <ENTER> para intentar de nuevo!")  
    
    
    salariobruto= "${:,.0f}".format(salariobruto)
    salarioporextra="${:,.0f}".format(salarioporextra)
    parasalud="${:,.0f}".format(parasalud)
    parapension="${:,.0f}".format(parapension)
    parariesgo= "${:,.0f}".format(parariesgo)
    totaldescuentos= "${:,.0f}".format(totaldescuentos)
    totalapagar="${:,.0f}".format(totalapagar)
    
    print ("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤")
    print ("¤¤¤¤¤¤¤¤¤¤¤¤ V O L A N T E  D E P A G O ¤¤¤¤¤¤¤¤¤¤¤")
    print ("\n¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤")
    print ("Nombre: ",Nom)
    print ("Cargo: ",Car)
    print ("Horas Trabajadas: ",horastrabajadas)
    print ("Salario Bruto: ",salariobruto)
    print ("Horas extras: ",horasextras)
    print ("\nTotal pago por horas extras: ",salarioporextra)
    print ("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤")
    print ("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤ DESCUENTOS DE LEY ¤¤¤¤¤¤¤¤¤¤¤¤¤¤")
    print ("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤\n")
    print ("Salud (4%): ",parasalud)
    print ("Pensión (4%): ",parapension)
    print ("ARL (0.522%): ",parariesgo)
    print ("\nTotal de descuentos: ",totaldescuentos)
    print ("\nTotal a pagar: ",totalapagar)
    print ("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤")
    print ("¤¤¤¤¤¤¤¤¤¤¤¤ FIN  DEL VOLANTE DE PAGO ¤¤¤¤¤¤¤¤¤¤¤¤¤")
    print ("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤\n")
    print ()
    input ("¡Pulse <ENTER> para salir!")
    
    Impresora= "{:<40}| {:<31}| {:<5}| {:<16}| {:<5}| {:<17}| {:<16}| {:<16}| {:<17}| {:<2}\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n".format(
        Nom, Car, horastrabajadas, salariobruto, horasextras, salarioporextra, parasalud, parariesgo, parapension, totalapagar
        )
    return Impresora
    