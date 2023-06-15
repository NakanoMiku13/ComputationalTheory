class Metodo:
    def __init__(self, nombre):
        self.nombre = nombre
        self.abierto = False

    def abrir(self):
        self.abierto = True

    def cerrar(self):
        self.abierto = False

    def esta_abierto(self):
        return self.abierto

variables2 = dict()
def validar_entero(valor):
    if valor.startswith('-'):
        valor = valor.replace('-', '')
    if valor.startswith('0') and len(valor) >= 1:
        if all(digito in '01234567' for digito in valor[1:]):
            return True
    elif valor.isdigit():
        return True

    return False


def validar_short(valor):
    return validar_entero(valor)


def validar_hexadecimal(valor):
    if valor.startswith('0x') and len(valor) > 2:
        hex_part = valor[2:]
        if all(digito in '0123456789abcdefABCDEF' for digito in hex_part):
            return True

    return False


def validar_exponencial(valor):
    if 'E' in valor:
        base, exponente = valor.split('E')
        if base and exponente:
            if validar_entero(base) and validar_entero(exponente) and '.' not in exponente:
                return True

    return False


def validar_punto_decimal(valor):
    if('E' in valor):
        val, exponente = valor.split('E')
        return validar_punto_decimal(val) and validar_entero(exponente)
    elif '.' in valor and not 'E' in valor:
        partes = valor.split('.')
        if len(partes) == 2:
            parte_entera, parte_decimal = partes
            if validar_entero(parte_entera) and validar_entero(parte_decimal):
                return True
    return False

def validar_sobrecarga_operador(valor,tipo_dato):
    operador = ""
    i = 0
    while(valor[i] != ')' and i < len(valor)-1):
        if(valor[i] != '('):
            operador += valor[i]
        i+=1
    if(operador == tipo_dato):
        valor = valor.replace(str('('+operador+')'),"")
        valor = valor.replace(" ","")
        return validar_punto_decimal(valor) or validar_entero(valor) or validar_hexadecimal(valor)
    return False

def validar_nombre_variable(nombre_variable):
    if nombre_variable[0].isdigit():
        return False
    caracteres_especiales = ['!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+', '=', '[', ']', '{', '}', ';', ':', ',', '<', '>', '/', '?', '|']
    for caracter in caracteres_especiales:
        if caracter in nombre_variable:
            return False
    return True

def validar_system_out(linea, numero_linea):
    linea = linea.strip().replace("System.out.println(","")
    linea = linea.strip().replace(");","")
    elementos = linea.split("+")
    for i in elementos:
        it = i.replace(" ","")
        if(it.startswith("\"") or it.startswith("'")):
            continue
        else:
            if(it.startswith("(")):
                elementos2 = it.replace("(","")
                elementos2 = elementos2.replace(")","")
                elementos2 = elementos2.replace(" ","")
                separados = None
                if '*' in elementos2:
                    separados = elementos2.split('*')
                elif '/' in elementos2:
                    separados = elementos2.split('/')
                for j in separados:
                    if(not j.isdigit() and not validar_hexadecimal(j) and not validar_punto_decimal(j)):
                        if(not (j in variables2)):
                            print(f"Error en linea {numero_linea}, no existe la variable {j}")
def analizar_archivo_java(archivo):
    with open(archivo, 'r') as file:
        lineas = file.readlines()

    clase_abierta = False
    metodo_actual = None
    metodo_main_existente = False
    comentario_abierto = False
    metodo_abierto = False
    for numero_linea, linea in enumerate(lineas, start=1):
        if "/*" in linea.strip():
            comentario_abierto = True
        elif "*/" in linea.strip() and comentario_abierto:
            comentario_abierto = False
        if "/*" in linea and "*/" in linea:
            comentario_abierto = False
            continue
        if not comentario_abierto:
            if any(tipo in linea.split() for tipo in ['+=','-=', '/=', '*=', '%=', '++', '--']):
                items = linea.strip().replace(";","").split()
                if not items[0] in variables2:
                    print(f"Línea {numero_linea}: Error de sintaxis - Variable:  no declarada")
            elif not any(tipo in linea.split() for tipo in ['int', 'float', 'double', 'char', 'String', 'short']) and not any(keyword in linea.split() for keyword in ['if', 'else', 'for', 'while', 'do']) and not "System.out.println" in linea and "=" in linea:
                var, valor = linea.strip().split("=",1)
                if not var in variables2:
                    print(f"Línea {numero_linea}: Error de sintaxis - Variable:  no declarada")
                vari = False
                opera = False
                if(not linea.strip().endswith(";")):
                    print(f"Línea {numero_linea}: Error de sintaxis - No se encontró ';'")
                valor = valor.strip().replace(";","")
                for i in valor.split():
                    if i in variables2 and not i in ['*','+','-','/','%'] and not vari:
                        vari = True
                        opera = False
                    elif vari and i in variables2:
                        print(f"Línea {numero_linea}: Error de sintaxis - {linea.strip()}")
                    if not i in variables2 and not i in ['*','+','-','/','%']:
                        print(f"Línea {numero_linea}: Error de sintaxis - Variable: {i} no declarada")
                    if i in ['*','+','-','/','%'] and not opera:
                        opera = True
                        vari = False
                    elif opera and i in ['*','+','-','/','%']:
                        print(f"Línea {numero_linea}: Error de sintaxis - {linea.strip()}")
            # Ignorar líneas en blanco o comentarios
            if linea.strip() == '' or linea.strip().startswith('//'):
                continue
            if 'System.out.println' in linea:
                validar_system_out(linea, numero_linea)
            if "public static void main(String[] args)" in linea:
                metodo_main_existente = True
            # Verificar la integridad de la clase
            if 'class' in linea.split():
                if clase_abierta:
                    print(f"Línea {numero_linea}: Error de sintaxis - Clase anidada no permitida")
                clase_abierta = True

            # Verificar errores de sintaxis en las declaraciones de variables
            if any(tipo in linea.split() for tipo in ['int', 'float', 'double', 'char', 'String', 'short']):
                palabras = linea.split()
                tipo = next(
                    tipo for tipo in ['int', 'float', 'double', 'char', 'String', 'short'] if tipo in palabras
                )

                variables = linea[linea.index(tipo) + len(tipo) :].split(',')
                if linea.strip().endswith(';'):
                    for variable in variables:
                        variable = variable.strip()
                        if '=' in variable:
                            nombre_variable, valor_variable = variable.split('=')
                            nombre_variable = nombre_variable.strip()
                            valor_variable = valor_variable.strip()
                            if not validar_nombre_variable(nombre_variable):
                                print(f"Línea {numero_linea}: Error de sintaxis - Declaración de variable inválida")
                            variables2[nombre_variable]= True
                            if nombre_variable == '':
                                print(f"Línea {numero_linea}: Error de sintaxis - Declaración de variable inválida")
                                continue
                            valor_variable = valor_variable.replace(';', '')
                            if tipo == 'int':
                                if not validar_entero(valor_variable) or (
                                    (not valor_variable.startswith('-0') or not valor_variable.startswith('0'))
                                    and any(digito > '7' for digito in valor_variable)
                                ) and not validar_sobrecarga_operador(valor_variable,tipo):
                                    print(f"Línea {numero_linea}: Error de sintaxis - Variable '{tipo}' inválida: {variable}")
                            elif tipo == 'float' or tipo == 'double':
                                if not validar_entero(valor_variable) and not validar_hexadecimal(valor_variable) and not validar_exponencial(valor_variable) and not validar_punto_decimal(valor_variable) and not validar_sobrecarga_operador(valor_variable,tipo):
                                    print(f"Línea {numero_linea}: Error de sintaxis - Variable '{tipo}' inválida: {variable}")
                            elif tipo == 'char':
                                if len(valor_variable) != 1 and not validar_sobrecarga_operador(valor_variable,tipo):
                                    print(f"Línea {numero_linea}: Error de sintaxis - Variable '{tipo}' inválida: {variable}")
                            elif tipo == 'String':
                                if not (valor_variable.startswith('"') and valor_variable.endswith('"')):
                                    print(f"Línea {numero_linea}: Error de sintaxis - Variable '{tipo}' inválida: {variable}")
                            elif tipo == 'short':
                                if not validar_short(valor_variable) and not validar_hexadecimal(valor_variable) and not validar_exponencial(valor_variable):
                                    print(f"Línea {numero_linea}: Error de sintaxis - Variable '{tipo}' inválida: {variable}")
                        else:
                            if(' ' in variable):
                                sp = variable.split(" ")
                                if(len(sp) == 2):
                                    print(f"Línea {numero_linea}: Error de sintaxis - Declaración de un valor sin '=' en: {variable}")
                            variable = variable.replace(';','')
                            variables2[variable]= True
                            if(not validar_nombre_variable(variable)):
                                print(f"Línea {numero_linea}: Error de sintaxis - Declaración de nombre de variable inválida")
                            #if(not variable.endswith(';')):
                                #   print(f"Línea {numero_linea}: Error de sintaxis - Declaración de variable inválida, falta ';'")
                else:
                    print(f"Línea {numero_linea}: Error de sintaxis - No puede existir la instruccion sin ';'")
            # Verificar la sintaxis de las estructuras de control
            if any(keyword in linea.split()for keyword in ['if', 'else', 'for', 'while', 'do']):
                palabras = linea.split()
                if 'if' in palabras:
                    index_if = palabras.index('if')
                    if index_if + 1 < len(palabras):
                        condicion = ' '.join(palabras[index_if + 1 :])
                        if condicion.count('(') == condicion.count(')'):
                            continue
                elif 'else' in palabras:
                    index_else = palabras.index('else')
                    if index_else + 1 < len(palabras):
                        if palabras[index_else + 1] == 'if':
                            continue
                        else:
                            print(f"Línea {numero_linea}: Error de sintaxis - Declaración 'else' inválida")
                elif 'for' in palabras:
                    
                    index_for = palabras.index('for')
                    if index_for + 1 < len(palabras):
                        condicion = ' '.join(palabras[index_for + 1 :])
                        if condicion.count('(') == condicion.count(')'):
                            if ';' in condicion:
                                declaracion = condicion[: condicion.index(';')].strip()
                                if '=' in declaracion:
                                    continue
                            else:
                                print(f"Línea {numero_linea}: Error de sintaxis - Declaración 'for' inválida")
                elif 'while' in palabras:
                    index_while = palabras.index('while')
                    if index_while + 1 < len(palabras):
                        condicion = ' '.join(palabras[index_while + 1 :])
                        if condicion.count('(') == condicion.count(')'):
                            continue
                elif 'do' in palabras:
                    index_do = palabras.index('do')
                    if index_do + 1 < len(palabras):
                        condicion = ' '.join(palabras[index_do + 1 :])
                        if condicion == '{':
                            continue

                print(f"Línea {numero_linea}: Error de sintaxis - Estructura de control inválida")

            # Verificar la sintaxis de las instrucciones if o else de una sola línea de código
            if any(keyword in linea.split() for keyword in ['if', 'else']):
                if '(' in linea and ')' in linea:
                    parentesis_abiertos = linea.count('(')
                    parentesis_cerrados = linea.count(')')
                    if parentesis_abiertos == parentesis_cerrados:
                        continue

            # Verificar la sintaxis de las declaraciones de import y sus respectivas bibliotecas
            if 'import' in linea.split():
                palabras = linea.split()
                if 'import' in palabras:
                    index_import = palabras.index('import')
                    if index_import + 1 < len(palabras):
                        libreria = ' '.join(palabras[index_import + 1:])
                        if '.' in libreria:
                            continue

                print(f"Línea {numero_linea}: Error de sintaxis - Declaración de import inválida")

    # Verificar la existencia del método main
    if not metodo_main_existente:
        print("Error: No se encontró el método main")


# Ejemplo de uso
archivo_java = 'App.java'
analizar_archivo_java(archivo_java)